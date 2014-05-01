import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class Graph<T> {
    private Map<T, Node<T>> nodes;

    public Graph() {
	nodes = new HashMap<T, Node<T>>();
    }

    public Graph(int capacity) {
	nodes = new HashMap<T, Node<T>>(capacity);
    }

    public void add(T key, Node<T> value) {
	nodes.put(key, value);
    }

    public Node<T> get(String key) {
	return nodes.get(key);
    }

    public void link(T k1, T k2, float weight) {
	Node<T> n1 = nodes.get(k1);
	Node<T> n2 = nodes.get(k2);

	n1.addNeighbor(n2, weight);
	n2.addNeighbor(n1, weight);
    }

    public Stream<Node<T>> getNodeStream() {
	return nodes.values().stream();
    }

    public void forEach(BiConsumer<T, Node<T>> action) {
	nodes.forEach(action);
    }
}
