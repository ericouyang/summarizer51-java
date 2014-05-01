import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Graph {
    private Map<String, Node> nodes;
    
    public Graph() {
	nodes = new HashMap<String, Node>();
    }
    
    public Graph(int capacity) {
	nodes = new HashMap<String, Node>(capacity);
    }
    
    public void add(String key, Node value) {
	nodes.put(key, value);
    }
    
    public Node get(String key) {
	return nodes.get(key);
    }
    
    public void link(String k1, String k2, float weight) {
	Node n1 = nodes.get(k1);
	Node n2 = nodes.get(k2);
	
	n1.addNeighbor(n2, weight);
	n2.addNeighbor(n1, weight);
    }
    
    public Iterator<Entry<String, Node>> getIterator() {
	return nodes.entrySet().iterator();
    }
}
