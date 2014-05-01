
public abstract class TextRankNode<T> extends Node<T> {
    
    public abstract float calculateSimilarity(Node<T> n);
    
}
