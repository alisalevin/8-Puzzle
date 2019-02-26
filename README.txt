Artificial Intelligence Project 1
February 25, 2019


Files
Puzzle1.java – has the Breadth-First Search algorithm. 
Puzzle2.java – has the A* Search algorithm with both heuristic 1 and heuristic 2. 
AStarPriorityQueue.java – is a priority queue I created for the frontier in A* Search because it is extremely challenging to override the priority function for the official Java priority queue datatype. 


Note: If you want to run these programs through the terminal and wish to compile all 3 java files at once, make sure all 3 files are in the same directory, get into that directory, and type "javac *.java"
Otherwise, you can compile each file separately as described in step 1 for each algorithm below. 


Breadth-First Search
1. To run the Breath-First Search algorithm through the terminal, type "javac Puzzle1.java" to compile the program. 
2. To run the program, type "java Puzzle1" 
3. The program will then ask you if you'd like to type in your own initial puzzle state or not (and have one randomly generated for you.) Answers should be either YES or NO in capital letters. I did not include extensive error handling for input, but the default in case of invalid input is a randomly generated initial puzzle state. 
4. If you choose to enter your own initial puzzle state, just type in a list of numbers separated by spaces (although numbers not separated by spaces will also work) and hit enter to begin the search. 


A* Search
1. To run the A* Search algorithm through the terminal, type "javac Puzzle2.java" and "javac AStarPriorityQueue.java" to compile the two programs. 
2. To run the program, type "java Puzzle2" 
3. The program will then ask you if you'd like to type in your own initial puzzle state or not (and have one randomly generated for you.) Answers should be either YES or NO in capital letters. I did not include extensive error handling for input, but the default in case of invalid input is a randomly generated initial puzzle state. 
4. If you choose to enter your own initial puzzle state, just type in a list of numbers separated by spaces (although numbers not separated by spaces will also work) and hit enter.
5. The program will then ask you which heuristic you would like to use for the search. Type in the number 1 for heuristic 1 or the number 2 for heuristic 2 and hit enter to begin the search. 



