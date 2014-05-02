package algorithms;

import java.util.HashSet;
import java.util.Set;

import types.Graph;

public class TextRankKeywords extends TextRank implements KeywordExtractor {
    private static final int DEFAULT_NUM_KEYWORDS = 10;

    private final String[] words;
    private final Graph<String> wordGraph;
    private final int numKeywords;

    public TextRankKeywords(String[] w) {
	words = w;
	wordGraph = new Graph<String>();
	numKeywords = DEFAULT_NUM_KEYWORDS;
    }

    @Override
    public String[] getKeywords() {
	for (int i = 0; i < words.length; i++) {
	    String w = words[i];
	    WordNode n = (WordNode) wordGraph.get(w);
	    if (n == null)
		wordGraph.add(words[i], new WordNode(words[i], i));
	    else
		n.addIndex(i);
	}

	calculateRanks(wordGraph);

	return wordGraph.getRankedNodes().limit(numKeywords)
		.map(n -> n.getContent()).toArray(String[]::new);
    }

    private class WordNode extends TextRankNode<String> {
	private static final int COOCCURRENCE_THRESHOLD = 10;

	private final Set<Integer> positions;

	public WordNode(String s, int pos) {
	    super(wordGraph, s);

	    positions = new HashSet<>();
	    positions.add(pos);
	}

	public boolean addIndex(int i) {
	    return positions.add(i);
	}

	@Override
	public double calculateRelationScore(Graph<String>.Node other) {
	    Set<Integer> thisPositions = positions;
	    Set<Integer> otherPositions = ((WordNode) other).positions;

	    for (int i : thisPositions) {
		for (int j : otherPositions) {
		    if (Math.abs(i - j) <= COOCCURRENCE_THRESHOLD)
			return 1;
		}
	    }

	    return 0;
	}
    }
}
