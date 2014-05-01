import java.util.List;

public class TextRankSentenceNode extends Node<Sentence> {

    public TextRankSentenceNode(Sentence s) {
	super(s);
    }

    public double calculateSimilarity(TextRankSentenceNode other) {
	List<String> thisWords = getContent().getWords();
	List<String> otherWords = other.getContent().getWords();

	long numCommonWords = thisWords.stream()
		.filter(w -> otherWords.contains(w)).count();

	return numCommonWords
		/ (Math.log(thisWords.size()) + Math.log(otherWords.size()));
    }
}
