import java.io.IOException;
import java.util.Arrays;

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
    public static final boolean DEBUG = true;

    public static void main(String[] args) throws IOException {
        String filename = "texts/gilbert.txt";

        /*
         * if (args.length == 0) { Scanner s = new Scanner(System.in);
         * System.out.print("Please enter a filename: "); filename = s.nextLine(); } else { filename
         * = args[0]; }
         */

        Parser p = new Parser(filename);

        if (DEBUG) {
            System.out.println("***SENTENCES***");
            for (Sentence s : p.parseSentences()) {
                System.out.println(s.getOriginal());
                System.out.println(s.getWords());
                System.out.println();
            }

            System.out.println("***WORDS***");
            System.out.println(Arrays.toString(p.parseWords()));
            System.out.println();
        }

        System.out.println("***SUMMARY***");
        for (Sentence s : TextRank.initSummaryExtractor().getSummary(p.parseSentences())) {
            System.out.println(s);
        }

    }
}
