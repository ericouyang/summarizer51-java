import java.util.List;

public abstract class Node implements Comparable<Node> {
    private List<Edge> neighbors;
    
    public void addNeighbor(Node n, float weight)
    {
	neighbors.add(new Edge(n, weight));
    }
    
    public List<Edge> getNeighbors()
    {
	return neighbors;
    }
    
    public float getRank()
    {
	float rank = 0;
	for(Edge e : neighbors)
	{
	    rank += e.getWeight();
	}
	return rank;
    }
    
    public abstract int compareTo(Node n);
}
