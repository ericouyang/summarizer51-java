import java.util.Arrays;
import java.util.List;

public class Sentence {
    private final String original;
    private final List<String> words;

    public Sentence(String s) {
	original = s;
	words = Arrays.asList(Parser.parseWords(s));
    }

    public String getOriginal() {
	return original;
    }

    public List<String> getWords() {
	return words;
    }
}
