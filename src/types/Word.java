package types;

public class Word {
    private final String word;
    private final String tag;

    public Word(String w, String t) {
	word = w;
	tag = t;
    }

    public String getWord() {
	return word;
    }

    public String getTag() {
	return tag;
    }

    public String toString() {
	return word + " (" + tag + ")";
    }
}
