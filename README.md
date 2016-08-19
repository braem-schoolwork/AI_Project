# AI Student Project
CPSC 371: AI Project.
January 2016 Semester @ UNBC

##Main Components
* Rubik's Cube
* A\* and Breadth-First Search Algorithms
* Multilayered Neural Network (input-hiddenlayers-output)
* Stochastic Back Propagation Algorithm
* Genetic Algorithm

The idea is to have the A\* search algorithm search for moves that solve any sized Rubik's Cube at a specific scramble depth, generating training data of which is used to train a multilayered neural network to solve that cube. That is, perturb an *n* sided Rubik's Cube *k* moves, then have A\* search the solution space, log the moves A\* takes, then train a neural network on the move data. Genetic algorithms were implemented, but did not preform as well as neural networks. 


##Usage
With the editor, you can

* Create an *n* sized Rubik's Cube (nxnxn)
* Generate a list of suggested moves with A\* or Breadth-First Search
* Generate Training Data
* Train a neural network on set of training data using a stochastic back propagation algorithm
* Include any amount of hidden layers with any number of neurons
* Use the best found neural network to generate suggested moves for the current state of a Rubik's Cube
* Run a genetic algorithm to find the best neural network configuration
* Run various experiments, outputting .csv files. I use these files to graphically display results.

Note that the fastest way to solve a Rubik's Cube in this program will be with the A\* search algorithm

#Algorithm/Data Structure Specifics
##Rubik's Cube
##A\* Search
##Neural Network
##Stochastic Back Propagation
##Genetic Algorithm


#Outputs; Research
