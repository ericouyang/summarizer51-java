import java.util.List;

public class TextRank implements KeywordExtractor, SummaryExtractor {

    private static final int MAX_ITERATIONS = 30;
    private static final double DAMPING_FACTOR = 0.85;
    private static final double ERROR_THRESHOLD = 0.0001;
    private static final int SUMMARY_LENGTH = 5;

    private TextRank() {
    }

    public static KeywordExtractor initKeywordExtractor() {
        return new TextRank();
    }

    public static SummaryExtractor initSummaryExtractor() {
        return new TextRank();
    }

    @Override
    public String[] getKeywords(String[] words) {
        return null;
    }

    @Override
    public Sentence[] getSummary(Sentence[] sentences) {
        Graph<Sentence> graph = new Graph<>();

        for (Sentence sentence : sentences) {
            graph.add(sentence, new SentenceNode(sentence));
        }

        calculateRanks(graph);

        return graph.getRankedNodes().limit(SUMMARY_LENGTH).map(n -> n.getContent())
                .toArray(Sentence[]::new);
    }

    private <T> void calculateRanks(Graph<T> graph) {
        graph.forEach((k1, v1) -> graph.forEach((k2, v2) -> {
            if (v1 != v2 && !v1.getNeighbors().contains(v2)) {
                graph.link(v1, v2);
            }
        }));

        for (int i = 0; i < MAX_ITERATIONS; i++) {
            // check if rank calculation has converged
            if (graph.getNodeStream().reduce(false, (a, n) -> a || n.calculateRank(),
                    Boolean::logicalOr)) {
                break;
            }
        }
    }

    private class SentenceNode extends Node<Sentence> {

        public SentenceNode(Sentence s) {
            super(s);
        }

        @Override
        public boolean calculateRank() {
            double rank = neighbors.stream().reduce(
                    0.0,
                    (aj, j) -> aj
                            + j.getWeight()
                            * j.getTarget().rank
                            / j.getTarget().neighbors.stream().reduce(0.0,
                                    (ak, k) -> ak + k.getWeight(), Double::sum), Double::sum);
            rank *= DAMPING_FACTOR;
            rank += 1 - DAMPING_FACTOR;

            boolean converged = Math.abs(this.rank - rank) < ERROR_THRESHOLD;

            this.rank = rank;

            return converged;
        }

        @Override
        public double calculateRelationScore(Node<Sentence> other) {
            List<String> thisWords = getContent().getWords();
            List<String> otherWords = other.getContent().getWords();

            long numCommonWords = thisWords.stream().filter(w -> otherWords.contains(w)).count();

            return numCommonWords / Math.log(thisWords.size() * otherWords.size());
        }
    }

}
