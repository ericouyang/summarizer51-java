Install the Java 8 JDK from http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

To run the class files, first make sure that you Environment Path variables point to the version of java you first downloaded.
Then, use the command:

	java Summarizer51

to run the application.

Alternatively, you can run the program directly using the provided .jar file using:

	java -classpath Summarizer51.jar Summarizer51

Additional arguments include

-f, --filename		Set the file name [filename]
-a, --algorithm		Specify algorithm [TextRank|LexRank]