import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
    private String fullText;
    private final static String sentenceRegex = "[\\.!?] *";
    private final static String wordRegex = "[ \\.,!?]+";

    public Parser(String filename) throws IOException {
	BufferedReader r = null;

	r = new BufferedReader(new FileReader(filename));

	StringBuffer strBuff = new StringBuffer();

	char[] buff = new char[1024];
	
	while (r.read(buff, 0, buff.length) >= 0)
	{
	    strBuff.append(new String(buff).replaceAll("\\r?\\n", " "));
	}
	
	fullText = strBuff.toString();

	r.close();
    }

    public String[] parseSentences() {
	return Parser.parseSentences(fullText);
    }

    public String[] parseWords() {
	return Parser.parseWords(fullText);
    }

    public static String[] parseSentences(String s) {
	return s.split(sentenceRegex);
    }

    public static String[] parseWords(String s) {
	String[] words = s.split(wordRegex);
	
	for(int i = 0; i < words.length; i++)
	{
	    words[i] = words[i].toLowerCase();
	}
	
	return words;
    }
}
