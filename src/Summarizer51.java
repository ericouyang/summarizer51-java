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
    public static void main(String[] args) {
	String filename = "";
	
	/*
	if (args.length == 0) {
	    Scanner s = new Scanner(System.in);
	    System.out.print("Please enter a filename: ");
	    filename = s.nextLine();
	}
	else
	{
	    filename = args[0];
	}
	*/
	
	filename = "texts/simple.txt";

	try {
	    Parser p = new Parser(filename);

	    System.out.println(Arrays.toString(p.parseSentences()));
	    System.out.println(Arrays.toString(p.parseWords()));
	    
	} catch (IOException e) {

	}

    }
}
