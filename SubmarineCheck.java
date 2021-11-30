import java.util.HashSet;
import java.util.Iterator;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class represents a method to check if a matrix contains a submarines according to the arguments.
 * 1. Minimum of two "1" vertically.
 * 2. Minimum of two "1" horizontally.
 * 3. There cannot be "1" diagonally unless arguments 1 and 2 are implied.
 * 4. The minimal distance between two submarines is at least one index("0").
 */
public class SubmarineCheck {
    public ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,
            10, 1000 , TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    protected ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    public SubmarineCheck() { }

    /**
     * This method returns the number of valid submarines in the matrix.
     * We take a piece of a rectangle in the matrix (by a list of SCC in a parallel way),
     * and check the four arguments.
     * @param submarines list of suspicious submarine
     * @param primitiveMatrix current 2D array
     * @return int
     */
    public int getNumberOfSubmarine(HashSet<HashSet<Index>> submarines, int[][] primitiveMatrix) {
        Integer count=0;
        for(HashSet<Index> submarine : submarines){
            Callable<Integer> callable = ()->{
                Iterator<Index> index = submarine.iterator();
                int maxRow =0, maxColumn = 0 ,minRow = Integer.MAX_VALUE, minColumn = Integer.MAX_VALUE;
                while (index.hasNext()){
                    Index ind = index.next();
                    maxRow = Math.max(maxRow, ind.row);
                    maxColumn = Math.max(maxColumn, ind.column);
                    minRow = Math.min(minRow, ind.row);
                    minColumn = Math.min(minColumn, ind.column);
                }
                if((maxRow == minRow) && (maxColumn == minColumn))
                    return 0;
                else{
                    for(int i = minRow ; i<= maxRow ; i++)
                        for (int j = minColumn; j <= maxColumn; j++)
                            if (primitiveMatrix[i][j] == 0) {
                                return 0;
                            }
                }
                return 1;
            };
            Future<Integer> integerFuture = threadPoolExecutor.submit(callable);
            try {
                count += integerFuture.get();
            }catch (ExecutionException | InterruptedException e){
                e.printStackTrace();
            }
        }
        return count;
    }
}
