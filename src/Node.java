import java.util.HashSet;
import java.util.Set;

public abstract class Node<T> implements Comparable<Node<T>> {
    protected Set<Edge<T>> neighbors;
    protected final T content;
    protected double rank = 1.0;

    public Node(T c) {
        neighbors = new HashSet<>();
        content = c;
    }

    public T getContent() {
        return content;
    }

    public void addNeighbor(Node<T> n, double weight) {
        neighbors.add(new Edge<T>(n, weight));
    }

    public boolean hasNeighbor(Node<T> n) {
        return neighbors.contains(n);
    }

    public Set<Edge<T>> getNeighbors() {
        return neighbors;
    }

    @Override
    public int compareTo(Node<T> n) {
        return Double.compare(n.rank, rank);
    }

    @Override
    public String toString() {
        return rank + ": " + content;
    }

    public abstract boolean calculateRank();

    public abstract double calculateRelationScore(Node<T> n);

}
