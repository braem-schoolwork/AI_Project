# AI Student Project
CPSC 371: AI Project.
January 2016 Semester @ UNBC

This program is only to be used by whom have knowledge, or at least partial knowledge, of the Main Components involved.

##Main Components
* Rubik's Cube
* A\* and Breadth-First Search Algorithms
* Multilayered Neural Network (input-hiddenlayers-output)
* Stochastic Back Propagation Algorithm
* Genetic Algorithm

The idea is to have the A\* search algorithm search for moves that solve the cube at a specific scramble depth, generating training data for a neural network. That is, perturb a Rubik's Cube *n* moves, then have A\* search the solution space, log the moves A\* takes, then train a neural network on the move data. Genetic algorithms were implemented, but did not preform as well as neural networks. 


##Usage
With the editor, you can

* Create an *N* sized Rubik's Cube (*N*x*N*x*N*)
* Generate a list of suggested moves with A\* or Breadth-First Search
* Generate Training Data
* Train a neural network on set of training data using a stochastic back propagation algorithm
* Include any amount of hidden layers with any number of neurons
* Use the best found neural network to generate suggested moves for the current state of a Rubik's Cube
* Run a genetic algorithm to find the best neural network configuration
* Run various experiments, outputting .csv files. I use these files to graphically display results.

Note that the fastest way to solve a Rubik's Cube in this program will be with the A\* search algorithm

#Algorithm/Data Structure Specifics

These algorithms are not Rubik's Cube specific, but are used in the solving of a Rubik's Cube.

##Rubik's Cube
Internally, the Rubik's Cube is structured as a 3-dimensional array. cube[5][1][2] would return the color of row 1 column 2 of the 5th face, of which is notated by a byte. All face's row/column configuration is relative to the top left corner of a Rubik's Cube, if you were to hold it in your hand. The face numbering is arbitrary.

A move is defined by the rotation (CW or CCW), the axis of rotation (X, Y, or Z) and the slice number (in the case of a 3x3x3 Rubik's Cube - 0, 1, or 2). A slice is just a row or column of a particular face that is rotatable, in the real world sense. Outer slice numbers will also rotate an entire face. 

##Breadth-First Search
The breadth-first search algorithm uses a LinkedList to keep track of pathways to explore (the open list), a HashSet to keep track of pathways already explored (the closed list), and a simple array to keep track of the children of a chosen node. The chosen node is popped off the open list and then searched in a Breadth-First pattern. The algorithm ends when the chosen node is equal to the goal state.

This algorithm returns the path taken.

##A\* Search
Similar to the Breadth-First Search algorithm, except that, for the open list, A\* uses a modified Priority Queue with an internal HashSet based on [Java's PriorityQueue](https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html) to make removal of nodes by reference fast. This is a sacrifice of memory for speed.

\*This A\* search algorithm requires the structure to have an h (heuristic) value. In the case of the Rubik's Cube, a 3D Manhattan Distance heuristic of the Rubik's Cube. This heuristic needs to be admissible, or undefined behaviour may occur.

##Neural Network


##Stochastic Back Propagation
##Genetic Algorithm


#Outputs; Research
