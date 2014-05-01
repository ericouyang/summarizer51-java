import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class Graph<K, V> {
    private Map<K, Node<V>> nodes;
    
    public Graph() {
	nodes = new HashMap<K, Node<V>>();
    }
    
    public Graph(int capacity) {
	nodes = new HashMap<K, Node<V>>(capacity);
    }
    
    public void add(K key, Node<V> value) {
	nodes.put(key, value);
    }
    
    public Node<V> get(String key) {
	return nodes.get(key);
    }
    
    public void link(K k1, K k2, float weight) {
	Node<V> n1 = nodes.get(k1);
	Node<V> n2 = nodes.get(k2);
	
	n1.addNeighbor(n2, weight);
	n2.addNeighbor(n1, weight);
    }
    
    public Stream<Node<V>> getNodeStream() {
	return nodes.values().stream();
    }
    
    public void forEach(BiConsumer<K, Node<V>> action) {
	nodes.forEach(action);
    }
}
