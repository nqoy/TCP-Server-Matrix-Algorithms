# TCP-Server-Matrix-Algorithms

## summary:
 A TCP server that handles algorithmic operations on matrices of data, depending on the type of task he was given.<br />
 The various operations and client handling is executed Asynchronously via multithreading.<br />

# Server Tasks:

## 1.Find strong connected components, including diagonal connections:<br />
 input: `Binary matrix` <br />
 output: `List of SCC, sort by the size of single SCC`.<br />
 Example: <br />
 ````
 input:
[1, 0, 0]
[1, 0, 1]
[0, 1, 1]
 
 output: [(0,0), (1,0), (1,2), (2,1), (2,2)]
````
## 2.Find shortest paths between two nodes:<br />
 input: `Binary matrix` (50* 50 is the limit)<br />
 output- `List of the shortest paths from source node and destination node`.<br />
 Example: <br />
 ````
 input:
 [1, 1, 0]
 [1, 0, 1]
 [0, 1, 1]
 
 output: [[(0, 0), (1, 0), (2,1), (2,2)], [(0, 0), (0, 1), (1, 2), (2, 2)]]
````
## 3.Submarine game:<br />
 input: `Binary matrix` <br />
 output- `Number of proper submarines`<br />
 ### Rules:
   1.At least two "1" in vertical.<br />
   2.At least two "1" in horizontal.<br />
   3.Cannot be two "1" in diagonal unless for both sections 1 and 2 are met.<br />
   4.The minimum distance between two submarines is one slot.<br />
 Example:<br />
 ````
 input:
 [1, 1, 0, 1, 1]
 [1, 0, 0, 1, 1]
 [1, 0, 0, 1, 1]
 
 output: 1
 input:
 [1, 1, 0, 1, 1]
 [0, 0, 0, 1, 1]
 [1, 1, 0, 1, 1]
 
 output: 3
````
## 4.Find the lightest path between two nodes:<br />
 input- `Metrix of integers, source & destination nodes` .<br />
 output- `list of the lightest paths`.<br />
 Example:<br />
 ````
 input:
 [100, 100, 100]
 [500, 900, 300]
 source node - (1,0)
 destination node - (1, 2)
     
 output: [(1, 0), (0, 0), (0, 1), (0, 2), (1, 2)]
 ```` 
 ## Instructions for running the code:
 1.download the code.<br />
 2.Run the TCPServer.<br />
 3.Enter any key.<br />
 4.Run the client (can execute multiple clients).<br />
 5.Follow the menu of the program.<br />
 6.Enter "stop" in the server console to stop the TCP server.<br />
