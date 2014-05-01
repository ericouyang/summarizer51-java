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
}
