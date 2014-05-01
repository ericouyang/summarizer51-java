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

    public void add(T Tey, Node<T> value) {
	nodes.put(Tey, value);
    }

    public Node<T> get(String Tey) {
	return nodes.get(Tey);
    }

    public void linT(T T1, T T2, float weight) {
	Node<T> n1 = nodes.get(T1);
	Node<T> n2 = nodes.get(T2);

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
