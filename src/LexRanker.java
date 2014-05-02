
/*
 * public class LexRanker implements SummaryExtractor {
 * 
 * private static final int MAX_ITERATIONS = 30; private static final double
 * DAMPING_FACTOR = 0.85; private static final double ERROR_THRESHOLD = 0.0001;
 * private static final int SUMMARY_LENGTH = 5;
 * 
 * Map<String, Double> idf = new HashMap<>(); Map<Sentence, Map<String,
 * Integer>> tfs = new HashMap<>();
 * 
 * @Override public Sentence[] getSummary(Sentence[] sentences) {
 * init(sentences); return null; }
 * 
 * private void init(Sentence[] sentences) { Map<String, Integer> df = new
 * HashMap<>(); for (Sentence sentence : sentences) { Map<String, Integer> tf =
 * new HashMap<>(); for (String word : sentence.getWords()) { Integer frequency
 * = tf.get(word); tf.put(word, frequency == null ? 1 : frequency + 1); }
 * tfs.put(sentence, tf);
 * 
 * for (String word : tf.keySet()) { Integer frequency = df.get(word);
 * df.put(word, frequency == null ? 1 : frequency + 1); } } for (Entry<String,
 * Integer> entry : df.entrySet()) { idf.put(entry.getKey(), Math.log((double)
 * sentences.length / (double) entry.getValue())); } }
 * 
 * private class SentenceNode extends Node<Sentence> {
 * 
 * public SentenceNode(Sentence s) { super(s); }
 * 
 * @Override public boolean calculateRank() { double rank =
 * neighbors.stream().reduce( 0.0, (aj, j) -> aj + j.getWeight()
 * j.getTarget().rank / j.getTarget().neighbors.stream().reduce(0.0, (ak, k) ->
 * ak + k.getWeight(), Double::sum), Double::sum); rank *= DAMPING_FACTOR; rank
 * += 1 - DAMPING_FACTOR;
 * 
 * boolean converged = Math.abs(this.rank - rank) < ERROR_THRESHOLD;
 * 
 * this.rank = rank;
 * 
 * return converged; }
 * 
 * @Override public double calculateRelationScore(Node<Sentence> other) {
 * Map<String, Integer> thisTf = tfs.get(this); Map<String, Integer> otherTf =
 * tfs.get(other);
 * 
 * double n = thisTf.keySet().stream().filter(w -> otherTf.containsKey(w))
 * .reduce((Double) 0.0, (a e) -> a + thisTf.get(e) * otherTf.get(e), combiner);
 * 
 * return numCommonWords / Math.log(thisWords.size() * otherWords.size()); } } }
 */