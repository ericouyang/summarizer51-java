
public class Edge {
    private float weight;
    private Node target;
    
    public Edge (Node t, float w) {
	target = t;
	weight = w;
    }
    
    public float getWeight() {
	return weight;
    }
    
    public Node getTarget() {
	return target;
    }
}
