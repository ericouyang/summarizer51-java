package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import types.Sentence;

public class Parser {
    private final static String SENTENCE_BOUNDARY = "(?<=(?<!\\..)[\\?\\!\\.]+)\\s(?!.\\.)|[\r\n]+";
    private final static String WORD_BOUNDARY = "('s|(?<=s)')?(,\\W|[^\\w-',\\?\\!\\.]+|(?<!\\..)([\\?\\!\\.]+)(\\s(?!.\\.)|$)|[\r\n]+)";

    private final String fullText;
    private final boolean debugMode;

    public Parser(String filename, boolean debug) throws IOException {
	debugMode = debug;

	try (BufferedReader r = new BufferedReader(new FileReader(filename))) {
	    StringBuffer strBuff = new StringBuffer();

	    char[] buf = new char[1024];
	    int read;
	    while ((read = r.read(buf, 0, buf.length)) >= 0) {
		strBuff.append(buf, 0, read);
	    }

	    fullText = strBuff.toString();

	    if (debugMode) {
		System.out.println("***TEXT***");
		System.out.println(fullText);
		System.out.println();
	    }
	}
    }

    public Parser(String filename) throws IOException {
	this(filename, false);
    }

    public Sentence[] parseSentences() {
	return Parser.parseSentences(fullText);
    }

    public String[] parseWords() {
	return Parser.parseWords(fullText);
    }

    public static Sentence[] parseSentences(String s) {
	String[] parsed = s.split(SENTENCE_BOUNDARY);
	List<Sentence> sentences = new LinkedList<>();
	for (int i = 0; i < parsed.length; i++) {
	    String sentence = parsed[i].trim();
	    if (!sentence.isEmpty()) {
		sentences.add(new Sentence(sentence));
	    }
	}
	return sentences.toArray(new Sentence[sentences.size()]);
    }

    public static String[] parseWords(String s) {
	String[] parsed = s.split(WORD_BOUNDARY);
	List<String> words = new LinkedList<>();
	for (int i = 0; i < parsed.length; i++) {
	    String word = parsed[i].trim().toLowerCase();
	    if (!word.isEmpty()) {
		words.add(word);
	    }
	}

	return words.toArray(new String[words.size()]);
    }
}
