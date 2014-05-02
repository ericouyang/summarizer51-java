package algorithms.textrank;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import types.Graph;
import types.NGram;
import types.Word;
import algorithms.KeywordExtractor;

public class TextRankKeywords extends TextRank implements KeywordExtractor {

    private final int MAX_NGRAM_LENGTH = 3;

    private final Word[] words;
    private final Graph<Word> wordGraph;
    private final Graph<NGram> nGramGraph;
    private final int numKeywords;

    public TextRankKeywords(Word[] w, int n) {
	words = w;
	wordGraph = new Graph<Word>();
	nGramGraph = new Graph<NGram>();
	numKeywords = n;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String[] getKeywords() {
	for (int i = 0; i < words.length; i++) {
	    Word w = words[i];
	    if (isCandidate(w)) {
		PositionNode<Word> n = (PositionNode<Word>) wordGraph.get(w);
		if (n == null)
		    wordGraph.add(w, new PositionNode<Word>(wordGraph, w, i));
		else
		    n.addIndex(i);
	    }
	}

	calculateRanks(wordGraph);

	System.out.println(Arrays.toString(wordGraph.getRankedNodes()
		.limit(numKeywords).map(n -> n.getContent().getWord())
		.toArray(String[]::new)));

	calculateNGramGraph(wordGraph.getThreshold(numKeywords * 5));

	calculateRanks(nGramGraph);

	return nGramGraph.getRankedNodes().limit(numKeywords)
		.map(n -> n.getContent().toString()).toArray(String[]::new);
    }

    private void calculateNGramGraph(double threshold) {
	int pos = 0;
	List<Word> nGramWords = new LinkedList<>();
	for (Word word : words) {
	    Graph<Word>.Node n = wordGraph.get(word);
	    if (n != null && nGramWords.size() < MAX_NGRAM_LENGTH
		    && n.getRank() >= threshold) {
		nGramWords.add(word);
	    } else {
		if (!nGramWords.isEmpty()) {
		    Word[] words = nGramWords.toArray(new Word[nGramWords
			    .size()]);
		    addNGram(pos++, words);
		    nGramWords.clear();
		}
		if (isCandidate(word))
		    addNGram(pos++, word);
	    }
	}
	if (!nGramWords.isEmpty()) {
	    Word[] words = nGramWords.toArray(new Word[nGramWords.size()]);
	    addNGram(pos++, words);
	    nGramWords.clear();
	}
    }

    @SuppressWarnings("unchecked")
    private void addNGram(int pos, Word... words) {
	NGram nGram = new NGram(words);
	System.out.println("NGram: " + nGram);
	PositionNode<NGram> nGramNode = (PositionNode<NGram>) nGramGraph
		.get(nGram);
	if (nGramNode == null) {
	    nGramGraph.add(nGram, new PositionNode<NGram>(nGramGraph,
		    new NGram(words), pos++));
	} else {
	    nGramNode.addIndex(pos++);
	}
    }

    private boolean isCandidate(Word w) {
	return w.getTag().equals("JJ") || w.getTag().charAt(0) == 'N';
    }
    /*
     * private String[] combineNodes(Stream<Graph<Word>.Node> words) {
     * PositionNode<Word>[] wordNodes = words.map(n -> (PositionNode<Word>) n)
     * .toArray(PositionNode<Word>[]::new);
     * 
     * List<String> combinedWordNodes = new ArrayList<>();
     * 
     * for (int i = 0; i < wordNodes.length; i++) { WordNode n1 = wordNodes[i];
     * if (n1 != null) { for (int j = i + 1; j < wordNodes.length; j++) {
     * WordNode n2 = wordNodes[j]; if (n2 != null) { int adjacency =
     * n1.adjacency(n2);
     * 
     * if (adjacency != 0) { String combined = (adjacency < 0) ? n1.getContent()
     * .getWord() + " " + n2.getContent().getWord() : n2 .getContent().getWord()
     * + " " + n1.getContent().getWord();
     * 
     * wordNodes[i] = null; wordNodes[j] = null;
     * 
     * combinedWordNodes.add(combined); }
     * 
     * } } if (wordNodes[i] != null)
     * combinedWordNodes.add(wordNodes[i].getContent().getWord()); } }
     * 
     * return combinedWordNodes.toArray(new String[combinedWordNodes.size()]); }
     */
}
