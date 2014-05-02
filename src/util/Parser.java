package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import types.Sentence;
import types.Word;

public class Parser {

    private final static int WRAP_LENGTH = 50;

    private final static String SENTENCE_BOUNDARY = "(?<=(?<!\\..)[\\?\\!\\.]+)\\s(?!.\\.)|[\r\n]+";
    private final static String WORD_BOUNDARY = "('s|(?<=s)')?(,\\W|[^\\w-',\\?\\!\\.]+|(?<!\\..)([\\?\\!\\.]+)(\\s(?!.\\.)|$)|[\r\n]+)";

    private final String fullText;
    private final boolean debugMode;
    private Sentence[] sentences = null;
    private String[] words = null;
    private Word[] taggedWords = null;

    public Parser(String filename, boolean debug) throws IOException {
        debugMode = debug;

        try (BufferedReader r = new BufferedReader(new FileReader(filename))) {
            StringBuffer strBuff = new StringBuffer();

            char[] buf = new char[1024];
            int read;
            int counter = 0;
            while ((read = r.read(buf, 0, buf.length)) > 0) {
                for (int i = 0; i < read; i++) {
                    char c = buf[i];
                    switch (c) {
                    case '\r':
                    case '\n':
                        if (counter != 0) {
                            if (counter > WRAP_LENGTH) {
                                strBuff.append(' ');
                            } else {
                                strBuff.append('\n');

                            }
                            counter = 0;
                        }
                        break;
                    default:
                        strBuff.append(c);
                        counter++;
                        break;
                    }
                }
            }

            fullText = strBuff.toString();

            if (debugMode) {
                System.out.println("***TEXT***");
                System.out.println(fullText);
                System.out.println();
            }

            r.close();
        }
    }

    public Parser(String filename) throws IOException {
        this(filename, false);
    }

    public Sentence[] getParsedSentences() {
        if (sentences == null) {
            sentences = Parser.parseSentences(fullText);
        }
        return sentences;
    }

    public String[] getParsedWords() {
        if (words == null) {
            words = Parser.parseWords(fullText);
        }
        return words;
    }

    public Word[] getTaggedWords() {
        if (taggedWords == null) {
            taggedWords = Parser.tagWords(getParsedWords());
        }
        return taggedWords;
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

    public static Word[] tagWords(String[] words) {
        Tagger t = new Tagger();
        return t.tag(words);
    }
}
