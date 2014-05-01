public class Edge<T> {
    private double weight;
    private Node<T> target;

    public Edge(Node<T> t, double w) {
        target = t;
        weight = w;
    }

    public double getWeight() {
        return weight;
    }

    public Node<T> getTarget() {
        return target;
    }
}
