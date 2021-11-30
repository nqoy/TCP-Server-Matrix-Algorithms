import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class represents the implementation of Bellman-Ford algorithm.
 * @param <T>
 */
public class ThreadLocalBellmanFord<T> {
    protected final ThreadLocal<Queue<Node<T>>>  queueThreadLocal =
            ThreadLocal.withInitial(LinkedList::new);
    protected final ThreadLocal<Set<Node<T>>> setThreadLocal =
            ThreadLocal.withInitial(()->new LinkedHashSet<>());
    public ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,
            10, 1000 , TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    protected ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * This method implements a push method to thread-local-queue
     * @param  node
     */
    protected void threadLocalAdd(Node<T> node){
        queueThreadLocal.get().add(node);
    }

    /**
     * This method implements a pop method to thread-local-queue
     */
    protected Node<T> threadLocalPoll(){
        return queueThreadLocal.get().poll();
    }

    /**
     * This method combines an implementation of BFS algorithm and Bellman-Ford algorithm:
     * 1. BFS for scanning the graph.
     * 2. Bellman-Ford for finding the simplest paths.
     * @param partOfGraph current graph.
     * @return Map of parents of each node.
     */

   public Map<Node<T>, ArrayList<Node<T>>> bellmanFord(Traversable<T> partOfGraph){
        Map<Node<T>, ArrayList<Node<T>>> parents = new HashMap<>();
        Map<Node<T>, Integer> weights = new HashMap<>();
        threadLocalAdd(partOfGraph.getOrigin());
        parents.put(partOfGraph.getOrigin(), new ArrayList<>());
        weights.put(partOfGraph.getOrigin(), partOfGraph.getValue(partOfGraph.getOrigin()));
        while (!queueThreadLocal.get().isEmpty()){
            Node<T> poppedNode = threadLocalPoll();
            Collection<Node<T>> reachableNode = partOfGraph.getReachableNodesWithoutDiagonal(poppedNode);
            for(Node<T> singleReachableNode : reachableNode){
                    if(!parents.containsKey(singleReachableNode))
                        parents.put(singleReachableNode, new ArrayList<>());

                    if(!weights.containsKey(singleReachableNode)){
                        weights.put(singleReachableNode, weights.get(poppedNode)+
                                partOfGraph.getValue(singleReachableNode));
                        threadLocalAdd(singleReachableNode);
                    }

                    if(weights.get(singleReachableNode)> weights.get(poppedNode) + partOfGraph.getValue(singleReachableNode)){
                        weights.put(singleReachableNode, weights.get(poppedNode)+ partOfGraph.getValue(singleReachableNode));
                        threadLocalAdd(singleReachableNode);
                        parents.get(singleReachableNode).clear();
                        parents.get(singleReachableNode).add(poppedNode);
                    }
                    else if(weights.get(singleReachableNode)== weights.get(poppedNode)+ partOfGraph.getValue(singleReachableNode))
                        parents.get(singleReachableNode).add(poppedNode);
                }
            }

        return parents;
    }


    /**
     * This method fills the list of paths from a source node to a destination node.
     * For each parents of a node, add the current path to the list and so on, starting from the destination node.
     * @param partOfGraph current graph
     * @param paths list of possible paths
     * @param path list of indices in a path
     * @param parents list of parents of nodes
     * @param destination destination node
     */
   public void findPaths(Traversable<T> partOfGraph, HashSet<ArrayList<Node<T>>> paths
           , ArrayList<Node<T>> path,Map<Node<T>, ArrayList<Node<T>>> parents, Node<T> destination){
       if(destination.equals(partOfGraph.getOrigin())){
           path.add(destination);
           paths.add((ArrayList<Node<T>>) path.clone());
           path.remove(path.get(path.size()-1));
       }
       for(Node parent : parents.get(destination)){
           path.add(destination);
           findPaths(partOfGraph,paths,path,parents,parent);
           path.remove(path.get(path.size()-1));
       }
   }
    /**
     * This method returns all the simplest paths from source node to destination node.
     * 1. call bellmanFordAlgorithm() method.
     * 2. call findPaths() method.
     * @param partOfGraph current graph
     * @return List of the simplest paths.
     */
    public HashSet<ArrayList<Node<T>>> getSimplePaths(Traversable<T> partOfGraph){
        HashSet<ArrayList<Node<T>>> paths = new HashSet<>();
        ArrayList<Node<T>> path = new ArrayList<>();
        Map<Node<T>, ArrayList<Node<T>>> parents = bellmanFord(partOfGraph);
        if (parents == null)
            return paths;
        findPaths(partOfGraph, paths, path, parents, partOfGraph.getDestination());
        for (ArrayList<Node<T>> route: paths ){
            Collections.reverse(route);
        }
        return paths;
    }
}
