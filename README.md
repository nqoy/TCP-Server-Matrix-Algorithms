# TCP-Server-Matrix-Algorithms

## summary:
TCP server, preforming various algorithmic operations on data matrices.<br />
Wrapping data as nodes and using graph algorithms for calculations.<br />
The algorithmic operations and the handling of server clients are performed asynchronously via multithreading.<br />
At the end of each operation the client receives the output of the relevant task.<br />

## Server Tasks:

### 1.Find strong connected components, including diagonal connections:<br />
 Input: `Binary matrix` <br />
 Output: `List of SCC, sort by the size of single SCC`.<br />
 Example: <br />
 ````
 Input:
[1, 0, 0]
[1, 0, 1]
[0, 1, 1]
 
 Output: [(0,0), (1,0), (1,2), (2,1), (2,2)]
````
### 2.Find shortest paths between two nodes:<br />
 Input: `Binary matrix` (50* 50 is the limit)<br />
 Output: `List of the shortest paths from source node and destination node`.<br />
 Example: <br />
 ````
 Input:
 [1, 1, 0]
 [1, 0, 1]
 [0, 1, 1]
 
 Output: [[(0, 0), (1, 0), (2,1), (2,2)], [(0, 0), (0, 1), (1, 2), (2, 2)]]
````
### 3.Submarine game:<br />
 Input: `Binary matrix` <br />
 Output- `Number of proper submarines`<br />
 #### Rules:
   1.At least two "1" in vertical.<br />
   2.At least two "1" in horizontal.<br />
   3.Cannot be two "1" in diagonal unless for both sections 1 and 2 are met.<br />
   4.The minimum distance between two submarines is one slot.<br />
 Example:<br />
 ````
 Input:
 [1, 1, 0, 1, 1]
 [1, 0, 0, 1, 1]
 [1, 0, 0, 1, 1]
 
 Output: 1
 Input:
 [1, 1, 0, 1, 1]
 [0, 0, 0, 1, 1]
 [1, 1, 0, 1, 1]
 
 Output: 3
````
### 4.Find the lightest path between two nodes:<br />
 Input- `Metrix of integers, source & destination nodes`.<br />
 Output- `list of the lightest paths`.<br />
 Example:<br />
 ````
 Input:
 [100, 100, 100]
 [500, 900, 300]
 Source node - (1,0)
 Destination node - (1, 2)
     
 Output: [(1, 0), (0, 0), (0, 1), (0, 2), (1, 2)]
 ```` 
 ### Instructions:
 1.Download the code.<br />
 2.Run the TCP Server.<br />
 3.Enter any key.<br />
 4.Run the client (can execute multiple clients).<br />
 5.Follow the menu of the program.<br />
 6.Enter "stop" in the server console to stop the TCP server.<br />
