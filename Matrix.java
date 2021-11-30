
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class represents a matrix build and functionality.
 */
public class Matrix implements Serializable {
    int[][] primitiveMatrix;

    /**
     * This constructor gets a 2D array and creates an instance of matrix.
     * @param oArray
     */

    public Matrix(int[][] oArray){
        List<int[]> list = new ArrayList<>();
        for (int[] row : oArray) {
            int[] clone = row.clone();
            list.add(clone);
        }
        primitiveMatrix = list.toArray(new int[0][]);
    }

    /**
     * This constructor gets the amount of rows and column and generates a random binary matrix.
     * @param row
     * @param column
     */

    public Matrix(int row, int column) {
        Random r = new Random();
        primitiveMatrix = new int[row][column];
        for (int i = 0; i < primitiveMatrix.length; i++) {
            for (int j = 0; j < primitiveMatrix[0].length; j++) {
                primitiveMatrix[i][j] = r.nextInt(2);
            }
        }
        for (int[] rows : primitiveMatrix) {
            String s = Arrays.toString(rows);
            System.out.println(s);
        }
        System.out.println("\n");
    }

    /**
     * This constructor gets the amount of rows, columns and bound and generates a random weighted matrix.
     * @param row
     * @param column
     * @param bound
     */

    public Matrix(int row, int column, int bound) {
        Random r = new Random();
        primitiveMatrix = new int[row][column];
        for (int i = 0; i < primitiveMatrix.length; i++) {
            for (int j = 0; j < primitiveMatrix[0].length; j++) {
                primitiveMatrix[i][j] = r.nextInt(bound) ;
            }
        }
        for (int[] rows : primitiveMatrix) {
            String s = Arrays.toString(rows);
            System.out.println(s);
        }
    }

    /**
     * This method prints the matrix.
     */
    public void printMatrix(){
        for (int[] row : primitiveMatrix) {
            String s = Arrays.toString(row);
            System.out.println(s);
        }
        System.out.println("\n");
    }

    /**
     * This method returns the 2D array
     * @return int[][] 2D array
     */
    public final int[][] getPrimitiveMatrix() {
        return primitiveMatrix;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int[] row : primitiveMatrix) {
            stringBuilder.append(Arrays.toString(row));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * This method returns the neighbors of the targeted index, including the diagonal indices.
     * @param index targeted index
     * @return Collection<Index> of neighbors
     */
    public Collection<Index> getNeighbors(final Index index){
        Collection<Index> list = new ArrayList<>();
        int extracted = -1;
        try{
            extracted = primitiveMatrix[index.row+1][index.column];
            list.add(new Index(index.row+1,index.column));
        }catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            extracted = primitiveMatrix[index.row][index.column+1];
            list.add(new Index(index.row,index.column+1));
        }catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            extracted = primitiveMatrix[index.row-1][index.column];
            list.add(new Index(index.row-1,index.column));
        }catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            extracted = primitiveMatrix[index.row][index.column-1];
            list.add(new Index(index.row,index.column-1));
        }catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            extracted = primitiveMatrix[index.row-1][index.column-1];
            list.add(new Index(index.row-1,index.column-1));
        }catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            extracted = primitiveMatrix[index.row+1][index.column-1];
            list.add(new Index(index.row+1,index.column-1));
        }catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            extracted = primitiveMatrix[index.row-1][index.column+1];
            list.add(new Index(index.row-1,index.column+1));
        }catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            extracted = primitiveMatrix[index.row+1][index.column+1];
            list.add(new Index(index.row+1,index.column+1));
        }catch (ArrayIndexOutOfBoundsException ignored){}
        return list;
    }

    /**
     * This method returns the neighbors of the targeted index, without the diagonal indices
     * @param index targeted index
     * @return Collection<Index> of neighbors
     */
    public Collection<Index> getNeighborsWithoutDiagonal(final Index index){
        Collection<Index> list = new ArrayList<>();
        int extracted = -1;
        try{
            extracted = primitiveMatrix[index.row+1][index.column];
            list.add(new Index(index.row+1,index.column));
        }catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            extracted = primitiveMatrix[index.row][index.column+1];
            list.add(new Index(index.row,index.column+1));
        }catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            extracted = primitiveMatrix[index.row-1][index.column];
            list.add(new Index(index.row-1,index.column));
        }catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            extracted = primitiveMatrix[index.row][index.column-1];
            list.add(new Index(index.row,index.column-1));
        }catch (ArrayIndexOutOfBoundsException ignored){}
        return list;
    }

    /**
     * This method returns the value of the targeted index
     * @param index targeted index
     * @return int
     */
    public int getValue(Index index) {
        return primitiveMatrix[index.row][index.column];
    }

    /**
     * This method returns the reachable nodes from the targeted index
     * @param index targeted index
     * @return Collection of reachable nodes.
     */
    public Collection<Index> getReachable(Index index) {
        ArrayList<Index> filteredIndices = new ArrayList<>();
        this.getNeighbors(index).stream().filter(i-> getValue(i)==1)
                .map(neighbor->filteredIndices.add(neighbor)).collect(Collectors.toList());
        return filteredIndices;
    }

    /**
     * This method returns the reachable nodes from the targeted index without the diagonal.
     * @param index
     * @return Collection of reachable nodes.
     */

    public Collection<Index> getReachableWithoutDiagonal(Index index){
    ArrayList<Index> filteredIndices = new ArrayList<>();
    filteredIndices.addAll(this.getNeighborsWithoutDiagonal(index));
    return filteredIndices;
    }
}