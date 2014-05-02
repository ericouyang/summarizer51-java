package algorithms.textrank;

import types.Graph;

public abstract class TextRank {

    protected static final int MAX_ITERATIONS = 30;
    protected static final double DAMPING_FACTOR = 0.85;
    protected static final double ERROR_THRESHOLD = 0.0001;

    /**
     * Performs the ranking calculations in place for the given graph
     * 
     * @param graph
     *            the graph for ranking calculations
     */
    protected <T> void calculateRanks(Graph<T> graph) {
	graph.forEach((k1, v1) -> graph.forEach((k2, v2) -> {
	    if (v1 != v2 && !v1.getNeighbors().contains(v2)) {
		graph.link(v1, v2);
	    }
	}));

	for (int i = 0; i < MAX_ITERATIONS; i++) {
	    // check if rank calculation has converged
	    if (graph.getNodeStream().reduce(false,
		    (a, n) -> a || n.calculateRank(), Boolean::logicalOr)) {
		break;
	    }
	}
    }

    protected abstract class TextRankNode<T> extends Graph<T>.Node {
	public TextRankNode(Graph<T> g, T c) {
	    g.super(c);
	}

	/**
	 * Iteratively calculates the ranking of this node
	 * 
	 * @return true if the ranking has converged
	 */
	public boolean calculateRank() {
	    double rank = neighbors.stream().reduce(
		    0.0,
		    (aj, j) -> aj
			    + j.getWeight()
			    * j.getTarget().getRank()
			    / j.getTarget()
				    .getNeighbors()
				    .stream()
				    .reduce(0.0, (ak, k) -> ak + k.getWeight(),
					    Double::sum), Double::sum);
	    rank *= DAMPING_FACTOR;
	    rank += 1 - DAMPING_FACTOR;

	    boolean converged = Math.abs(this.rank - rank) < ERROR_THRESHOLD;

	    this.rank = rank;

	    return converged;
	}
    }
}
