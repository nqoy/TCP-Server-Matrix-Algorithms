# TCP_Server_Matrix_algorithms

Development of a TCP server preforming various algorithmic operations on data matrices.<br />
Wrapping data as nodes and using graph algorithms for calculations.<br />
The algorithmic operations and the handling of server clients are performed in parallel via multithreading.<br />
At the end of each operation the client recives the output of the relevant task.<br />

Tasks:
Finding SCC
Shortest paths from one index to another
Lightest paths from one index to another
Number of submarines:
Ruls:
 * 1. Minimum of two "1" vertically.
 * 2. Minimum of two "1" horizontally.
 * 3. There cannot be "1" diagonally unless arguments 1 and 2 are implied.
 * 4. The minimal distance between two submarines is at least one index("0").
