import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class represents a Thread safe dfs algorithm.
 * @param <T>
 */
public class ThreadLocalDFS<T> {
    protected final ThreadLocal<Stack<Node<T>>> stackThreadLocal =
            ThreadLocal.withInitial(Stack::new);
    protected final ThreadLocal<Set<Node<T>>> setThreadLocal =
            ThreadLocal.withInitial(()->new LinkedHashSet<>());
    public ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,
            10, 1000 , TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    protected ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * This method implements a push method to thread-local-stack
     * @param  node
     */
    protected void threadLocalPush(Node<T> node){
        stackThreadLocal.get().push(node);
    }

    /**
     * This method implements a pop method to thread-local-stack
     */
    protected Node<T> threadLocalPop(){
        return stackThreadLocal.get().pop();
    }

    /**
     * This method implements a DFS search in a parallel way.
     * Each thread searches a different branch at the same time.
     * Usage of write lock when popping the node from the stack to prevent from multiple threads
     * to access the same node, in order to scan the graph as fast as possible.
     * @param partOfGraph current graph
     * @param startNode current node
     */
    public void parallelTraverse(Traversable<T> partOfGraph , Node<T> startNode){
        while(!stackThreadLocal.get().isEmpty()){
            readWriteLock.writeLock().lock();
            Node<T> poppedNode = threadLocalPop();
            readWriteLock.writeLock().unlock();
            setThreadLocal.get().add(poppedNode);
            Collection<Node<T>> reachableNodes = partOfGraph.getReachableNodes(poppedNode);
            for (Node<T> singleReachableNode: reachableNodes){
                if (!setThreadLocal.get().contains(singleReachableNode) &&
                        !stackThreadLocal.get().contains(singleReachableNode)){
                    threadLocalPush(singleReachableNode);
                  Runnable r = () ->{
                        parallelTraverse(partOfGraph, singleReachableNode);
                    };
                    threadPoolExecutor.submit(r);
                }
            }
        }
    }

    /**
     * This method implements a DFS search.
     * Then return a strong connected component.
     * @param partOfGraph current graph.
     * @return HashSet<T> of strong connected component.
     */
    public HashSet<T> traverse(Traversable<T> partOfGraph){
        setThreadLocal.remove();
        stackThreadLocal.remove();
        threadLocalPush(partOfGraph.getOrigin());
        while(!stackThreadLocal.get().isEmpty()){
            Node<T> poppedNode = threadLocalPop();
            setThreadLocal.get().add(poppedNode);
            Collection<Node<T>> reachableNodes = partOfGraph.getReachableNodes(poppedNode);
            for (Node<T> singleReachableNode: reachableNodes){
                if (!setThreadLocal.get().contains(singleReachableNode) &&
                        !stackThreadLocal.get().contains(singleReachableNode)){
                    threadLocalPush(singleReachableNode);
                    parallelTraverse(partOfGraph, singleReachableNode);
                }
            }
        }
        HashSet<T> blackList = new LinkedHashSet<>();
        for (Node<T> node: setThreadLocal.get()){
            blackList.add(node.getData());
        }
        return blackList;
    }

}
