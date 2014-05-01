import java.util.List;

public abstract class Node<T> {
    private List<Edge<T>> neighbors;
    private T content;
    
    public T getContent() {
	return content;
    }
    
    public void setContent(T c) {
	content = c;
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
