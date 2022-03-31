# TCP-Server-Matrix-Algorithms

## summary:
 A TCP server that handles algorithmic operations on matrices of data, depending on the type of task he was given.<br />
 The various operations and client handling is executed Asynchronously via multithreading.<br />

# Server Tasks:

## 1.Find strong connected components, including diagonal connections:<br />
 input: `Binary matrix` <br />
 output: `List of SCC, sort by the size of single SCC`.<br />
 example: <br />
 ````
 input-<br />
 > [1, 0, 0]<br />
 > [1, 0, 1]<br />
 > [0, 1, 1]<br />
 
 output- [(0,0), (1,0), (1,2), (2,1), (2,2)]<br />
````
## 2.Find shortest paths between two nodes:<br />
 input: `Binary matrix` (50* 50 is the limit)<br />
 output- `List of the shortest paths from source node and destination node`.<br />
 example: <br />
 ````
 input-<br />
 > [1, 1, 0]<br />
 > [1, 0, 1]<br />
 > [0, 1, 1]<br />
 
 output- [[(0, 0), (1, 0), (2,1), (2,2)], [(0, 0), (0, 1), (1, 2), (2, 2)]]<br />
````
## 3.Submarine game:<br />
 input: `Binary matrix` <br />
 output- `Number of proper submarines`<br />
 **rules:<br />**
   1.At least two "1" in vertical.<br />
   2.At least two "1" in horizontal.<br />
   3.Cannot be two "1" in diagonal unless for both sections 1 and 2 are met.<br />
   4.The minimum distance between two submarines is one slot.<br />
 example:<br />
 input- <br />
 > [1, 1, 0, 1, 1]<br />
 > [1, 0, 0, 1, 1]<br />
 > [1, 0, 0, 1, 1]<br />
 
 output- 1<br />
 input- <br />
 > [1, 1, 0, 1, 1]<br />
 > [0, 0, 0, 1, 1]<br />
 > [1, 1, 0, 1, 1]<br />
 
 output- 3<br />

## 4.Find the simplest path between two nodes:<br />
 input- 2D array source node and destination node.<br />
 output- list of the simplest paths.<br />
 example:<br />
 input- <br />
 > [100, 100, 100] <br />
 > [500, 900, 300]<br />
 > source node - (1,0)<br />
 > destination node - (1, 2)<br />
     
 output- [(1, 0), (0, 0), (0, 1), (0, 2), (1, 2)]<br />
  
 # Instructions for running the code:
 1.download the code.<br />
 2.Run the TCPServer first.<br />
 3.Enter any key.<br />
 4.Run the client (can be multiple).<br />
 5.Follow the menu at the program.<br />
 6.Enter "stop" at the server to stop the TCP server.<br />
 
 # Thank you!
