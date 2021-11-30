import java.util.*;

/**
 * This class represents the implementation of BFS algorithm.
 * @param <T>
 */
public class BFSVisit<T> {
    /**
     * This method implements BFS algorithm.
     * @param partOfGraph current graph
     * @param parents list of parents of nodes
     */
    public void BFS (Traversable<T> partOfGraph, Map<Node<T>, ArrayList<Node<T>>> parents){
        Map<Node<T>, Integer> distances = new HashMap<>();
        Queue<Node<T>> nodeQueue = new LinkedList<>();
        nodeQueue.add(partOfGraph.getOrigin());
        distances.put(partOfGraph.getOrigin(),0);
        Node poppedNode;
        while (!nodeQueue.isEmpty()){
            poppedNode= nodeQueue.peek();
            nodeQueue.remove();
            Collection<Node<T>> reachableNodes = partOfGraph.getReachableNodes(poppedNode);
            for(Node<T> singleReachableNode : reachableNodes){
                if(!distances.containsKey(singleReachableNode))
                    distances.put(singleReachableNode, Integer.MAX_VALUE);

                if(!parents.containsKey(singleReachableNode))
                    parents.put(singleReachableNode,new ArrayList<>());

                if(distances.get(singleReachableNode) > distances.get(poppedNode) + 1){
                    distances.put(singleReachableNode, distances.get(poppedNode) + 1);
                    nodeQueue.add(singleReachableNode);
                    parents.get(singleReachableNode).clear();
                    parents.get(singleReachableNode).add(poppedNode);
                }
                else if(distances.get(singleReachableNode) == distances.get(poppedNode) + 1)
                    parents.get(singleReachableNode).add(poppedNode);
            }
        }
    }

    /**
     * This method fills the list of paths from a source node to a destination node.
     * For each parents of an node, add the current path to the list and so on,
     * starting from the destination node.
     * @param partOfGraph current graph
     * @param paths list of possible paths
     * @param path list of indices in a path
     * @param parents list of parents of nodes
     * @param destination destination node
     */
    public void findPaths(Traversable<T> partOfGraph, List<ArrayList<Node<T>>> paths
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
     * This method returns all the paths from a source node to a destination node.
     * 1.Implement BFS
     * 2.Call findPath() method to fill the list of paths.
     * @param partOfGraph current graph
     * @return list of paths.
     * @throws NullPointerException
     */
    public List<ArrayList<Node<T>>> getPaths(Traversable<T> partOfGraph) throws NullPointerException{
        List<ArrayList<Node<T>>> paths = new ArrayList<>();
        ArrayList<Node<T>> path = new ArrayList<>();
        Map<Node<T> , ArrayList<Node<T>>> parents = new HashMap<>();
        BFS(partOfGraph, parents);
        if(partOfGraph.getValue(partOfGraph.getOrigin()) == 0 || partOfGraph.getValue(partOfGraph.getDestination())==0)
            return paths;
        findPaths(partOfGraph, paths, path, parents, partOfGraph.getDestination());
        for (ArrayList<Node<T>> route: paths ){
            Collections.reverse(route);
        }
        for(int i=0; i<paths.size();i++){
            System.out.println("Path No. " + (i+1));
            System.out.println(paths.get(i));
        }
        return paths;
    }
}
