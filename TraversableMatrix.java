import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * This class implements adapter/wrapper/decorator design pattern
 */
public class TraversableMatrix implements Traversable<Index> {
    protected final Matrix matrix;
    protected Index startIndex,endIndex;

    public TraversableMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public Index getEndIndex() {
        return endIndex;
    }

    public Index getStartIndex() {
        return startIndex;
    }

    public void setEndIndex(Index endIndex) {
        this.endIndex = endIndex;
    }

    public void setStartIndex(Index startIndex) {
        this.startIndex = startIndex;
    }

    @Override
    public Node<Index> getDestination() throws NullPointerException{
        if (this.endIndex == null) throw new NullPointerException("end index is not initialized");
        return new Node<>(this.endIndex);
    }

    @Override
    public Node<Index> getOrigin() throws NullPointerException{
        if (this.startIndex == null) throw new NullPointerException("start index is not initialized");
        return new Node<>(this.startIndex);
    }

    @Override
    public Collection<Node<Index>> getReachableNodes(Node<Index> someNode) {
        List<Node<Index>> reachableIndex = new ArrayList<>();
        for (Index index : this.matrix.getNeighbors(someNode.getData())) {
            if (matrix.getValue(index) == 1) {
                Node<Index> indexNode = new Node<>(index, someNode);
                reachableIndex.add(indexNode);
            }
        }
        return reachableIndex;
    }

    @Override
    public int getValue(Node<Index> someNode) {
        return matrix.getValue(new Index(someNode.getData().row,someNode.getData().column));
    }

    @Override
    public Collection<Node<Index>> getReachableNodesWithoutDiagonal(Node<Index> someNode) {
        List<Node<Index>> reachableIndex = new ArrayList<>();
        for (Index index : this.matrix.getNeighborsWithoutDiagonal(someNode.getData())) {
            Node<Index> indexNode = new Node<>(index, someNode);
            reachableIndex.add(indexNode);
        }
        return reachableIndex;
    }
    @Override
    public Collection<Node<Index>> getReachableWeight(Node<Index> someNode){
        List<Node<Index>> reachableIndex = new ArrayList<>();
        for(Index index : this.matrix.getReachableWithoutDiagonal(someNode.getData())){
            Node<Index> indexNode = new Node<>(index, someNode);
            reachableIndex.add(indexNode);
        }
        return reachableIndex;
    }
    @Override
    public String toString() {
        return matrix.toString();
    }
}
