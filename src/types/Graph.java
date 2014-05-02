package types;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class Graph<T> {
    private Map<T, Node> nodes;

    public Graph() {
	nodes = new HashMap<T, Node>();
    }

    public Graph(int capacity) {
	nodes = new HashMap<T, Node>(capacity);
    }

    public Node add(T key, Node value) {
	return nodes.put(key, value);
    }

    public Node get(String key) {
	return nodes.get(key);
    }

    @SuppressWarnings("unchecked")
    public void linkNodes() {
	Node[] n = (Node[]) nodes.values().toArray();
	for (int i = 0; i < n.length; i++) {
	    Node n1 = n[i];
	    for (int j = i + 1; j < n.length; j++) {
		Node n2 = n[j];
		link(n1, n2);
	    }
	}
    }

    public void link(Node n1, Node n2) {
	double relation = n1.calculateRelationScore(n2);
	if (relation > 0.0) {
	    n1.addNeighbor(n2, relation);
	    n2.addNeighbor(n1, relation);
	}
    }

    public Stream<Node> getNodeStream() {
	return nodes.values().stream();
    }

    public void forEach(BiConsumer<T, Node> action) {
	nodes.forEach(action);
    }

    public Stream<Node> getRankedNodes() {
	return getNodeStream().sorted();
    }

    public class Edge {
	private double weight;
	private Node target;

	public Edge(Node t, double w) {
	    target = t;
	    weight = w;
	}

	public double getWeight() {
	    return weight;
	}

	public Node getTarget() {
	    return target;
	}
    }

    public abstract class Node implements Comparable<Node> {
	protected Set<Edge> neighbors;
	protected final T content;
	protected double rank = 1.0;

	public Node(T c) {
	    neighbors = new HashSet<>();
	    content = c;
	}

	public T getContent() {
	    return content;
	}

	public void addNeighbor(Node n, double weight) {
	    neighbors.add(new Edge(n, weight));
	}

	public boolean hasNeighbor(Node n) {
	    return neighbors.contains(n);
	}

	public double getRank() {
	    return rank;
	}

	public Set<Edge> getNeighbors() {
	    return neighbors;
	}

	@Override
	public int compareTo(Node n) {
	    return Double.compare(n.rank, rank);
	}

	@Override
	public String toString() {
	    return rank + ": " + content;
	}

	public abstract boolean calculateRank();

	public abstract double calculateRelationScore(Node n);

    }
}
