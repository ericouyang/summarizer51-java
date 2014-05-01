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

    @SuppressWarnings("unchecked")
    public void linkNodes() {
        Node<T>[] n = nodes.values().toArray(new Node[nodes.size()]);
        for (int i = 0; i < n.length; i++) {
            Node<T> n1 = n[i];
            for (int j = i + 1; j < n.length; j++) {
                Node<T> n2 = n[j];
                link(n1, n2);
            }
        }
    }

    public void link(Node<T> n1, Node<T> n2) {
        double relation = n1.calculateRelationScore(n2);
        if (relation > 0.0) {
            n1.addNeighbor(n2, relation);
            n2.addNeighbor(n1, relation);
        }
    }

    public Stream<Node<T>> getNodeStream() {
        return nodes.values().stream();
    }

    public void forEach(BiConsumer<T, Node<T>> action) {
        nodes.forEach(action);
    }

    public Stream<Node<T>> getRankedNodes() {
        return getNodeStream().sorted();
    }
}
