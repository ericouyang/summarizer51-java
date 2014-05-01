import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
    private String fullText;

    // private final static String sentenceRegex = "[\\.!?] *";

    // http://en.wikipedia.org/wiki/Sentence_boundary_disambiguation
    private final static String sentenceRegex = "(?<!\\..)([\\?\\!\\.]+)\\s(?!.\\.)";

    private final static String wordRegex = "[^\\w-]+";

    public Parser(String filename) throws IOException {
        BufferedReader r = null;

        r = new BufferedReader(new FileReader(filename));

        StringBuffer strBuff = new StringBuffer();

        char[] buff = new char[1024];

        while (r.read(buff, 0, buff.length) >= 0) {
            strBuff.append(new String(buff).replaceAll("\\r?\\n", " "));
        }

        fullText = strBuff.toString();

        r.close();
    }

    public Sentence[] parseSentences() {
        return Parser.parseSentences(fullText);
    }

    public String[] parseWords() {
        return Parser.parseWords(fullText);
    }

    public static Sentence[] parseSentences(String s) {
        /*
         * List<String> matches = new ArrayList<String>(); Matcher m =
         * Pattern.compile(sentenceRegex).matcher(s); while (m.find()) { matches.add(m.group()); }
         * 
         * return matches.stream().map(match -> new Sentence(match)).toArray(Sentence[]::new);
         */

        String[] parsed = s.split(sentenceRegex);
        Sentence[] sentences = new Sentence[parsed.length];
        for (int i = 0; i < parsed.length; i++) {
            sentences[i] = new Sentence(parsed[i]);
        }
        return sentences;

    }

    public static String[] parseWords(String s) {
        String[] words = s.split(wordRegex);

        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].toLowerCase();
        }

        return words;
    }
}
