package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import types.Graph;
import types.Word;

public class TextRankKeywords extends TextRank implements KeywordExtractor {

    private final Word[] words;
    private final Graph<Word> wordGraph;
    private final int numKeywords;

    public TextRankKeywords(Word[] w, int n) {
	words = w;
	wordGraph = new Graph<Word>();
	numKeywords = n;
    }

    @Override
    public String[] getKeywords() {
	for (int i = 0; i < words.length; i++) {
	    Word w = words[i];
	    if (w.getTag().equals("JJ") || w.getTag().charAt(0) == 'N') {
		WordNode n = (WordNode) wordGraph.get(w);
		if (n == null)
		    wordGraph.add(w, new WordNode(w, i));
		else
		    n.addIndex(i);
	    }
	}

	calculateRanks(wordGraph);

	return Arrays.copyOfRange(combineNodes(wordGraph.getRankedNodes()
		.limit(numKeywords * 2)), 0, numKeywords);
    }

    private String[] combineNodes(Stream<Graph<Word>.Node> words) {
	WordNode[] wordNodes = words.map(n -> (WordNode) n).toArray(
		WordNode[]::new);

	List<String> combinedWordNodes = new ArrayList<>();

	for (int i = 0; i < wordNodes.length; i++) {
	    WordNode n1 = wordNodes[i];
	    if (n1 != null) {
		for (int j = i + 1; j < wordNodes.length; j++) {
		    WordNode n2 = wordNodes[j];
		    if (n2 != null) {
			int adjacency = n1.adjacency(n2);

			if (adjacency != 0) {
			    String combined = (adjacency < 0) ? n1.getContent()
				    .getWord()
				    + " "
				    + n2.getContent().getWord() : n2
				    .getContent().getWord()
				    + " "
				    + n1.getContent().getWord();

			    wordNodes[i] = null;
			    wordNodes[j] = null;

			    combinedWordNodes.add(combined);
			}

		    }
		}
		if (wordNodes[i] != null)
		    combinedWordNodes.add(wordNodes[i].getContent().getWord());
	    }
	}

	return combinedWordNodes.toArray(new String[combinedWordNodes.size()]);
    }

    private class WordNode extends TextRankNode<Word> {
	private static final int COOCCURRENCE_THRESHOLD = 2;

	private final Set<Integer> positions;

	public WordNode(Word s, int pos) {
	    super(wordGraph, s);

	    positions = new HashSet<>();
	    positions.add(pos);
	}

	public boolean addIndex(int i) {
	    return positions.add(i);
	}

	public int adjacency(WordNode other) {
	    return withinThreshold(other, 1);
	}

	@Override
	public double calculateRelationScore(Graph<Word>.Node other) {
	    return (withinThreshold((WordNode) other, COOCCURRENCE_THRESHOLD) != 0) ? 1
		    : 0;
	}

	private int withinThreshold(WordNode other, int threshold) {
	    Set<Integer> thisPositions = positions;
	    Set<Integer> otherPositions = other.positions;

	    for (int i : thisPositions) {
		for (int j : otherPositions) {
		    if (Math.abs(i - j) <= threshold)
			return i - j;
		}
	    }

	    return 0;
	}
    }
}
