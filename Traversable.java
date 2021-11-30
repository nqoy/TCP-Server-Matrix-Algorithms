import java.util.Collection;

/**
 * This interface defines the functionality required for a traversable graph
 */
public interface Traversable<T> {
    /**
     * This method returns the start node.
     * @return Node<T>
     */
    public Node<T> getOrigin();

    /**
     * This method returns the destination node.
      * @return Node<T>
     */
    public Node<T> getDestination();

    /**
     * This method returns the reachable nodes of a current node (diagonal included).
     * @param someNode current node
     * @return Collection<Node<T>>
     */
    public Collection<Node<T>>  getReachableNodes(Node<T> someNode);

    /**
     * This method returns a value of current node.
     * @param someNode current node
     * @return int
     */
    public int getValue(Node<T> someNode);

    /**
     * This method returns the reachable nodes of a current node
     * @param someNode current node
     * @return Collection<Node<T>>
     */
    public Collection<Node<T>> getReachableNodesWithoutDiagonal(Node<T> someNode);

    public Collection<Node<Index>> getReachableWeight(Node<T> someNode);

}
