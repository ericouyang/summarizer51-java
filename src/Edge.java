
public class Edge<T> {
    private float weight;
    private Node<T> target;
    
    public Edge (Node<T> t, float w) {
	target = t;
	weight = w;
    }
    
    public float getWeight() {
	return weight;
    }
    
    public Node<T> getTarget() {
	return target;
    }
}
