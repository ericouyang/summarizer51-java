
public class TextRankSentenceNode extends Node<Sentence> {
    
    public TextRankSentenceNode(Sentence s) {
	super(s);
    }
    
    public float calculateSimilarity(TextRankSentenceNode n) {
	return 0;
    }
    
}
