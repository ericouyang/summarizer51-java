import java.util.List;

public abstract class Node<T> {
    protected List<Edge<T>> neighbors;
    protected final T content;
    
    public Node(T c) {
	content = c;
    }
    
    public T getContent() {
	return content;
    }
    
    public void addNeighbor(Node<T> n, float weight) {
	neighbors.add(new Edge<T>(n, weight));
    }
    
    public List<Edge<T>> getNeighbors() {
	return neighbors;
    }
    
    public float getRank() {
	float rank = 0;
	for(Edge<T> e : neighbors) {
	    rank += e.getWeight();
	}
	return rank;
    }

}
