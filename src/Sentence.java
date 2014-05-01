
public class Sentence {
    private final String original;
    private final String[] words;
    
    public Sentence(String s) {
	original = s;
	words = Parser.parseWords(s);
    }
    
    public String getOriginal() {
	return original;
    }
    
    public String[] getWords() {
	return words;
    }
}
