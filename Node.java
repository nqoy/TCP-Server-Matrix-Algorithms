import java.io.Serializable;
import java.util.Objects;

/**
 * This class wraps a concrete object and supplies getters and setter
 * @param <T>
 */
public class Node<T> implements Serializable {
    private T data;
    private Node<T> parent;


    public Node(T someObject, final Node<T> discoveredBy){
        this.data = someObject;
        this.parent = discoveredBy;
    }

    public Node(T someObject){
        this(someObject,null);
    }

    public T getData() {
        return data;
    }

    public Node(){
        this(null);
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode():0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node<?> state1 = (Node<?>) o;

        return Objects.equals(data, state1.data);
    }

    @Override
    public String toString() {
        return data.toString();
    }

}
