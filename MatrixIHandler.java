import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class handles the input matrix from the user and operate the calculations that are needed
 * and returns them.
 */
public class MatrixIHandler implements IHandler {
    private Matrix matrix;
    private Index start,end;

    /**
     * This method clears data members between clients
     * (if the same instance is shared among clients/tasks)
     */
    private void resetParams(){
        this.matrix = null;
        this.start = null;
        this.end = null;
    }

    /**
     * This method returns a list of all the indices with value of 1.
     * @param primitiveMatrix current 2D array
     * @return List of indices.
     */
    private List<Index> getIndices(int[][] primitiveMatrix) {
        List<Index> indexList = new ArrayList<>();
        for(int i = 0; i< primitiveMatrix.length ; i++)
            for(int j = 0; j< primitiveMatrix[0].length; j++)
                if(primitiveMatrix[i][j] == 1)
                    indexList.add(new Index(i,j));
        return indexList;
    }

    /**
     * This method finds strong connected components and adds them to a hashset.
     * @param myTraversable instance of TraversableMatrix.
     * @param algorithm instance of ThreadLocalSearch.
     * @param sccSet hashSet of strong connected components.
     * @param indexList list of "1".
     */
    protected void getSCC(TraversableMatrix myTraversable, ThreadLocalDFS<Index> algorithm,
                          HashSet<HashSet<Index>> sccSet, List<Index> indexList) {
        while (!indexList.isEmpty()) {
            myTraversable.setStartIndex(indexList.get(0));
            HashSet<Index> singleSCC = algorithm.traverse(myTraversable);
            for (Index index : singleSCC)
                indexList.remove(index);
            sccSet.add(singleSCC);
        }
        algorithm.threadPoolExecutor.shutdown();
    }

    @Override
    public void handle(InputStream fromClient, OutputStream toClient)
            throws IOException, ClassNotFoundException {

        // In order to read either objects or primitive types we can use ObjectInputStream
        ObjectInputStream objectInputStream = new ObjectInputStream(fromClient);
        // In order to write either objects or primitive types we can use ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(toClient);
        this.resetParams(); // in order to use same handler between tasks/clients

        boolean doWork = true;
        while(doWork){
            // client send a verbal command
            switch(objectInputStream.readObject().toString()){
                   case "matrix":{
                       int[][] primitiveMatrix = (int[][])objectInputStream.readObject();
                       System.out.println("Server: Got 2d array from client");
                       this.matrix = new Matrix(primitiveMatrix);
                       this.matrix.printMatrix();
                       break;
                   }
                   case "firstTask":{
                       this.matrix = (Matrix) objectInputStream.readObject();
                       TraversableMatrix myTraversable = new TraversableMatrix(matrix);
                       ThreadLocalDFS<Index> algorithm = new ThreadLocalDFS<>();
                       HashSet<HashSet<Index>> sccSet = new HashSet<>();
                       List<Index> indexList = getIndices(myTraversable.matrix.getPrimitiveMatrix());
                       getSCC(myTraversable, algorithm, sccSet, indexList);
                       List<HashSet<Index>> sortedSCCList = sccSet.stream().sorted(Comparator.comparingInt(HashSet::size))
                           .collect(Collectors.toList());
                       System.out.println("strong connected component : " + sortedSCCList);
                       objectOutputStream.writeObject(sortedSCCList);
                       break;
                   }
                   case "secondTask":{
                       this.matrix= (Matrix) objectInputStream.readObject();
                       TraversableMatrix myTraversable = new TraversableMatrix(this.matrix);
                       myTraversable.startIndex = (Index) objectInputStream.readObject();
                       myTraversable.endIndex = (Index) objectInputStream.readObject();
                       BFSVisit<Index> algorithm = new BFSVisit<>();
                       List<ArrayList<Node<Index>>> shortestPaths = algorithm.getPaths(myTraversable);
                       objectOutputStream.writeObject(shortestPaths);
                       break;
                   }
                   case  "thirdTask":{
                       this.matrix= (Matrix) objectInputStream.readObject();
                       TraversableMatrix myTraversable = new TraversableMatrix(this.matrix);
                       ThreadLocalDFS<Index> algorithm = new ThreadLocalDFS<>();
                       HashSet<HashSet<Index>> submarines = new HashSet<>();
                       List<Index> indexList = getIndices(myTraversable.matrix.getPrimitiveMatrix());
                       getSCC(myTraversable, algorithm, submarines, indexList);
                       SubmarineCheck submarineCheck = new SubmarineCheck();
                       int numberOfSubmarine = submarineCheck.getNumberOfSubmarine(submarines,myTraversable.matrix.getPrimitiveMatrix());
                       objectOutputStream.writeObject(numberOfSubmarine);
                       break;
                   }
                case "fourthTask":{
                        /*int[][] primitiveMatrix = (int[][]) objectInputStream.readObject();
                        this.matrix =new Matrix(primitiveMatrix);*/
                        this.matrix = (Matrix) objectInputStream.readObject();
                        TraversableMatrix myTraversable = new TraversableMatrix(this.matrix);
                        myTraversable.startIndex = (Index) objectInputStream.readObject();
                        myTraversable.endIndex = (Index) objectInputStream.readObject();
                        ThreadLocalBellmanFord<Index> algorithm = new ThreadLocalBellmanFord<>();
                        HashSet<ArrayList<Node<Index>>> simplePaths = algorithm.getSimplePaths(myTraversable);
                        objectOutputStream.writeObject(simplePaths);
                    break;
                }
                case "start index":{
                    this.start = (Index)objectInputStream.readObject();
                    break;
                }
                case "end index":{
                    this.end = (Index)objectInputStream.readObject();
                    break;
                }
                case "stop":{
                    doWork = false;
                    break;
                }
            }
        }
    }

}