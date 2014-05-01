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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof Sentence) {
            Sentence s = (Sentence) o;
            return original.equals(s.original);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return original.hashCode();
    }

    @Override
    public String toString() {
        return original;
    }
}
