import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import types.Sentence;
import types.Word;
import util.Parser;
import algorithms.lexrank.LexRank;
import algorithms.textrank.TextRankKeywords;
import algorithms.textrank.TextRankSummary;

/**
 * Summarizer51
 *
 * Final project for Computer Science 51
 *
 * Spring 2014
 *
 * @author Eric Ouyang <eouyang@college.harvard.edu>
 * @author Frederick Widjaja <fwidjaja@college.harvard.edu>
 *
 * @version 0.1
 *
 */
public class Summarizer51 {
    private static boolean debugMode = false;
    private static String filename = null;
    private static Algorithm algorithm = Algorithm.TEXTRANK;
    private static Sentence[] parsedSentences;
    private static Word[] taggedWords;

    private static Parser p;
    private static Sentence[] summary;
    private static String[] keywords;

    private static int numKeywords = 5;
    private static int summaryLength = 3;

    private enum Algorithm {
	TEXTRANK, LEXRANK
    }

    public static void main(String[] args) throws IOException {
	System.out.println("Welcome to Summarizer51!");

	for (int i = 0; i < args.length; i++) {
	    String arg = args[i];

	    if (arg.charAt(0) == '-') {
		switch (arg.substring(1)) {
		case "f":
		case "-file":
		    if (i + 1 < args.length) {
			filename = args[i + 1];
			i++;
		    } else {
			System.err.println("\nYou must provide a filename");
			System.exit(1);
		    }
		    break;
		case "a":
		case "-algorithm":
		    if (i + 1 < args.length) {
			if (args[i + 1].equalsIgnoreCase("textrank"))
			    algorithm = Algorithm.TEXTRANK;
			else if (args[i + 1].equalsIgnoreCase("lexrank"))
			    algorithm = Algorithm.LEXRANK;
			else {
			    System.err
				    .println("\nThat is not an implemented algorithm.\nPlease choose from \"textrank\" and \"lexrank\"");
			    System.exit(2);
			}
			i++;
		    } else {
			System.err
				.println("\nYou must provide the name of an algorithm.\nPlease choose from \"textrank\" and \"lexrank\"");
			System.exit(3);
		    }
		    break;
		case "d":
		case "-debug":
		    debugMode = true;
		}
	    }

	}
	if (filename == null) {
	    Scanner s = new Scanner(System.in);
	    System.out.print("\nPlease enter a filename: ");
	    filename = s.nextLine();

	    s.close();
	}

	p = new Parser(filename);

	System.out.println("\nParsing Sentences...");
	parsedSentences = p.getParsedSentences();

	System.out.println("\nTagging parts of speech...");
	taggedWords = p.getTaggedWords();

	System.out.println("\nFinding keywords...");
	keywords = new TextRankKeywords(taggedWords, numKeywords).getKeywords();

	switch (algorithm) {
	case TEXTRANK:
	    System.out.println("\nCalculating summary using TextRank...");
	    summary = new TextRankSummary(parsedSentences, summaryLength)
		    .getSummary();
	    break;
	case LEXRANK:
	    System.out.println("\nCalculating summary using LexRank...");
	    summary = new LexRank(parsedSentences).getSummary();
	    break;
	}

	if (debugMode) {
	    System.out.println("\n*** SENTENCES ***");
	    for (Sentence s : parsedSentences) {
		System.out.println(s.getOriginal());
		System.out.println(s.getWords());
		System.out.println();
	    }

	    System.out.println("\n*** WORDS ***");
	    System.out.println(Arrays.toString(taggedWords));
	    System.out.println();
	}

	System.out.printf("\n*** KEYWORDS (%d) ***\n", numKeywords);
	for (String s : keywords) {
	    System.out.println("  - " + s);
	}

	System.out.printf("\n*** SUMMARY (%d) ***\n", summaryLength);
	for (Sentence s : summary) {
	    System.out.println("  - " + s);
	}

    }
}
