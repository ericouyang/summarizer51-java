package algorithms;

import types.Sentence;

public interface SummaryExtractor {

    /**
     * @return Sentence[] the summary
     * @see Sentence
     */
    public Sentence[] getSummary();
}
