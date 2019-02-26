import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;

public class Puzzle1 {

	//Puzzle1 Variables
	int[] puzzleState;
	int depth; 
	ArrayList<String> solutionPath; 
	
	//Default Constructor
	public Puzzle1() {
		this.puzzleState = new int[9];
		this.depth = 0; 
		this.solutionPath = new ArrayList<String>();
	}
	
	//Constructor with specified values 
	public Puzzle1(int[] puzzle, int depth, ArrayList<String> path) {
		this.puzzleState = puzzle;
		this.depth = depth; 
		this.solutionPath = path;
	}
	
	//Frontier
	Queue<Puzzle1> frontier = new LinkedList<Puzzle1>();
	
	//Explored
	ArrayList<Puzzle1> explored = new ArrayList<Puzzle1>(); 	
	
	//Goal States for Both Parities 
	int[] goal0 = {1, 2, 3, 4, 5, 6, 7, 8, 0};
	int[] goal1 = {1, 2, 3, 8, 0, 4, 7, 6, 5}; 
	

	//Main Method
	public static void main(String argv[]) {
		
		//Initializes puzzle to user input or random puzzle state
		Puzzle1 puzzle = new Puzzle1();
		puzzle.initPuzzle();
		int[] initialState = puzzle.getState(); 

		//Initial Output
		System.out.print("Initial State: ");
		puzzle.printState(initialState);
		System.out.print("Goal State: ");
		puzzle.printGoalState(initialState);
		System.out.println("Searching...");
		
		long startTime = System.nanoTime();
		
		//Runs BFS Search
		ArrayList<String> solution = (puzzle.BFS(initialState)); 
		
		long totalTime = System.nanoTime() - startTime;
		
		//Final Output 
		System.out.println("First 3 Moves of BFS Strategy: ");
		puzzle.printSolutionPath(solution, initialState); 
		System.out.println("Number of Moves Required " + solution.size());
		System.out.println("Number of States Explored: " + puzzle.explored.size());
		System.out.println("Search Time: " + (totalTime / 1000000) + " milliseconds");
		
	}
	
	/*
	 * Performs Breadth-First Search to find solution to goal state
	 * @param initial – initial state, the state for which a solution is to be found
	 * @return – ArrayList of strings, the solution path to the goal state 
	 */
	public ArrayList<String> BFS(int[] initial) {
		
		//Initializes the frontier with the initial state
		ArrayList<String> path = new ArrayList<String>(); 
		Puzzle1 puzzle = new Puzzle1(initial, 0, path); 
		frontier.add(puzzle); 
	
		
		while (!frontier.isEmpty()) {
			//Removes leaf state from frontier (based on LIFO queue)
			puzzle = frontier.remove(); 
			
			//Adds state to explored set
			explored.add(puzzle); 
			//Checks if current state is goal state
			if (isGoal(puzzle.getState())) return puzzle.getPath();
			else {
				//Expands state 
				ArrayList<String> state = expandState(puzzle.getState(), puzzle.getCost() + 1, puzzle.getPath());
				//If goal state has been returned (i.e. solution path is not zero), return the path to that solution
				if (state.size() != 0) return state; 
			}
		}
		return puzzle.getPath(); 
	}
	
	
	/*
	 * Gets state of a Puzzle1 object
	 * @return – integer array of puzzle state 
	 */
	public int[] getState() { 
	      return puzzleState; 
	} 
	
	/*
	 * Gets path cost (depth) of a Puzzle1 object's state
	 * @return – integer path cost 
	 */
	public int getCost() { 
		return depth; 
	}
	
	/*
	 * Gets solution path of a Puzzle1 object's state to the goal state
	 * @return – ArrayList of strings that represent moves to the goal state
	 */
	public ArrayList<String> getPath() { 
		return solutionPath; 
	} 
	
	/*
	 * Adds a move to the solution path of a Puzzle1 object's state to the goal state
	 */
	public void addToPath(String move) {
		solutionPath.add(move);  
	}
	
	/*
	 * Expands a state and adds all new states that are not already in the explored or frontier lists to the frontier 
	 * @param state – state, the state to be expanded
	 * @return – the path to the goal state if the goal state is found upon expansion, or an empty path otherwise 
	 */
	public ArrayList<String> expandState(int[] state, int depth, ArrayList<String> path) {
		
		ArrayList<String> notGoal = new ArrayList<String>(); 
		int spaceIndex = blankSpace(state); //gets index of blank space in puzzle 
		
		//For all indices in which a blank space cannot move in all 4 directions
		if (spaceIndex != 4) {
			
			if (spaceIndex == 0) {
				//Creates new path arrays
				ArrayList<String> rightPath = new ArrayList<String>(path); 
				ArrayList<String> downPath = new ArrayList<String>(path);
				
				//Creates new states
				Puzzle1 right = new Puzzle1(rightState(state, spaceIndex), depth, rightPath); 
				Puzzle1 down = new Puzzle1(downState(state, spaceIndex), depth, downPath); 
				
				//Adds new states to frontier if they are not already explored or in the frontier 
				if ((!explored.contains(right)) && (!frontier.contains(right))) {
					right.addToPath("Right"); 
					//Returns solution if state is goal state
					if (isGoal(right.getState())) {
						explored.add(right); 
						return right.getPath();
					} 
					//Otherwise, adds state to frontier
					frontier.add(right);
				}
				if ((!explored.contains(down)) && (!frontier.contains(down))) {
					down.addToPath("Down"); 
					//Returns solution if state is goal state
					if (isGoal(down.getState())) {
						explored.add(down);
						return down.getPath(); 
					}
					//Otherwise, adds state to frontier
					frontier.add(down);
				}
			}
			else if (spaceIndex == 2) {
				//Creates new path arrays
				ArrayList<String> leftPath = new ArrayList<String>(path);
				ArrayList<String> downPath = new ArrayList<String>(path);
				
				//Creates new states
				Puzzle1 left = new Puzzle1(leftState(state, spaceIndex), depth, leftPath); 
				Puzzle1 down = new Puzzle1(downState(state, spaceIndex), depth, downPath); 
				
				//Adds new states to frontier if they are not already explored or in the frontier 
				if ((!explored.contains(left)) && (!frontier.contains(left))) {
					left.addToPath("Left"); 
					//Returns solution if state is goal state
					if (isGoal(left.getState())) {
						explored.add(left); 
						return left.getPath();
					} 
					//Otherwise, adds state to frontier
					frontier.add(left);
				}
				if ((!explored.contains(down)) && (!frontier.contains(down))) {
					down.addToPath("Down"); 
					//Returns solution if state is goal state
					if (isGoal(down.getState())) return down.getPath(); 
					//Otherwise, adds state to frontier
					frontier.add(down);
				}
			}
			else if (spaceIndex == 6) {
				//Creates new path arrays
				ArrayList<String> rightPath = new ArrayList<String>(path); 
				ArrayList<String> upPath = new ArrayList<String>(path);
				
				//Creates new states
				Puzzle1 right = new Puzzle1(rightState(state, spaceIndex), depth, rightPath); 
				Puzzle1 up = new Puzzle1(upState(state, spaceIndex), depth, upPath); 
				
				//Adds new states to frontier if they are not already explored or in the frontier 
				if ((!explored.contains(right)) && (!frontier.contains(right))) {
					right.addToPath("Right"); 
					//Returns solution if state is goal state
					if (isGoal(right.getState())) {
						explored.add(right);
						return right.getPath(); 
					}
					//Otherwise, adds state to frontier
					frontier.add(right); 
				}
				if ((!explored.contains(up)) && (!frontier.contains(up))) {
					up.addToPath("Up"); 
					//Returns solution if state is goal state
					if (isGoal(up.getState())) {
						explored.add(up);
						return up.getPath(); 
					}
					//Otherwise, adds state to frontier
					frontier.add(up);
				}
			}
			else if (spaceIndex == 8) {
				//Creates new path arrays
				ArrayList<String> leftPath = new ArrayList<String>(path); 
				ArrayList<String> upPath = new ArrayList<String>(path);
				
				//Creates new states
				Puzzle1 left = new Puzzle1(leftState(state, spaceIndex), depth, leftPath); 
				Puzzle1 up = new Puzzle1(upState(state, spaceIndex), depth, upPath); 
				
				//Adds new states to frontier if they are not already explored or in the frontier 
				if ((!explored.contains(left)) && (!frontier.contains(left))) {
					left.addToPath("Left");
					//Returns solution if state is goal state
					if (isGoal(left.getState())) {
						explored.add(left);
						return left.getPath();
					} 
					//Otherwise, adds state to frontier
					frontier.add(left);
				}
				if ((!explored.contains(up)) && (!frontier.contains(up))) {
					up.addToPath("Up");
					//Returns solution if state is goal state
					if (isGoal(up.getState())) {
						explored.add(up);
						return up.getPath(); 
					}
					//Otherwise, adds state to frontier
					frontier.add(up);
				}
			}
			
			else if (spaceIndex == 1) {
				//Creates new path arrays 
				ArrayList<String> rightPath = new ArrayList<String>(path); 
				ArrayList<String> leftPath = new ArrayList<String>(path);
				ArrayList<String> downPath = new ArrayList<String>(path);
				
				//Creates new states
				Puzzle1 right = new Puzzle1(rightState(state, spaceIndex), depth, rightPath); 
				Puzzle1 left = new Puzzle1(leftState(state, spaceIndex), depth, leftPath); 
				Puzzle1 down = new Puzzle1(downState(state, spaceIndex), depth, downPath);  
				
				//Adds new states to frontier if they are not already explored or in the frontier 
				if ((!explored.contains(right)) && (!frontier.contains(right))) {
					right.addToPath("Right");
					//Returns solution if state is goal state
					if (isGoal(right.getState())) {
						explored.add(right);
						return right.getPath();
					} 
					//Otherwise, adds state to frontier
					frontier.add(right);
				}
				if ((!explored.contains(left)) && (!frontier.contains(left))) {
					left.addToPath("Left"); 
					//Returns solution if state is goal state
					if (isGoal(left.getState())) {
						explored.add(left); 
						return left.getPath(); 
					}
					//Otherwise, adds state to frontier
					frontier.add(left);
				}
				if ((!explored.contains(down)) && (!frontier.contains(down))) {
					down.addToPath("Down"); 
					//Returns solution if state is goal state
					if (isGoal(down.getState())) {
						explored.add(down);
						return down.getPath(); 
					}
					//Otherwise, adds state to frontier
					frontier.add(down);
				}
			}
			else if (spaceIndex == 3) {
				//Creates new path arrays
				ArrayList<String> rightPath = new ArrayList<String>(path); 
				ArrayList<String> upPath = new ArrayList<String>(path);
				ArrayList<String> downPath = new ArrayList<String>(path);
				
				//Creates new states
				Puzzle1 right = new Puzzle1(rightState(state, spaceIndex), depth, rightPath); 
				Puzzle1 up = new Puzzle1(upState(state, spaceIndex), depth, upPath); 
				Puzzle1 down = new Puzzle1(downState(state, spaceIndex), depth, downPath);  
				
				//Adds new states to frontier if they are not already explored or in the frontier 
				if ((!explored.contains(right)) && (!frontier.contains(right))) {
					right.addToPath("Right"); 
					//Returns solution if state is goal state
					if (isGoal(right.getState())) {
						explored.add(right);
						return right.getPath(); 
					}
					//Otherwise, adds state to frontier
					frontier.add(right);
				}
				if ((!explored.contains(up)) && (!frontier.contains(up))) {
					up.addToPath("Up");
					//Returns solution if state is goal state
					if (isGoal(up.getState())) {
						explored.add(up);
						return up.getPath(); 
					}
					//Otherwise, adds state to frontier
					frontier.add(up);
				}
				if (!explored.contains(down) && (!frontier.contains(down))) {
					down.addToPath("Down"); 
					//Returns solution if state is goal state
					if (isGoal(down.getState())) {
						explored.add(down); 
						return down.getPath(); 
					}
					//Otherwise, adds state to frontier
					frontier.add(down);
				}
			}
			else if (spaceIndex == 5) {
				//Creates new path arrays
				ArrayList<String> leftPath = new ArrayList<String>(path);
				ArrayList<String> upPath = new ArrayList<String>(path); 
				ArrayList<String> downPath = new ArrayList<String>(path);
				
				//Creates new states
				Puzzle1 left = new Puzzle1(leftState(state, spaceIndex), depth, leftPath); 
				Puzzle1 up = new Puzzle1(upState(state, spaceIndex), depth, upPath); 
				Puzzle1 down = new Puzzle1(downState(state, spaceIndex), depth, downPath);  
				
				//Adds new states to frontier if they are not already explored or in the frontier 
				if ((!explored.contains(left)) && (!frontier.contains(left))) {
					left.addToPath("Left"); 
					//Returns solution if state is goal state
					if (isGoal(left.getState())) {
						explored.add(left); 
						return left.getPath(); 
					}
					//Otherwise, adds state to frontier
					frontier.add(left);
				}
				if ((!explored.contains(up)) && (!frontier.contains(up))) {
					up.addToPath("Up"); 
					//Returns solution if state is goal state
					if (isGoal(up.getState())) {
						explored.add(up); 
						return up.getPath();
					} 
					//Otherwise, adds state to frontier
					frontier.add(up);
				}
				if ((!explored.contains(down)) && (!frontier.contains(down))) {
					down.addToPath("Down"); 
					//Returns solution if state is goal state
					if (isGoal(down.getState())) {
						explored.add(down); 
						return down.getPath(); 
					}
					//Otherwise, adds state to frontier
					frontier.add(down);
				}
			}
			
			else if (spaceIndex == 7) {
				//Creates new path arrays
				ArrayList<String> rightPath = new ArrayList<String>(path);
				ArrayList<String> leftPath = new ArrayList<String>(path);
				ArrayList<String> upPath = new ArrayList<String>(path); 
				
				//Creates new states
				Puzzle1 right = new Puzzle1(rightState(state, spaceIndex), depth, rightPath); 
				Puzzle1 left = new Puzzle1(leftState(state, spaceIndex), depth, leftPath); 
				Puzzle1 up = new Puzzle1(upState(state, spaceIndex), depth, upPath); 
				
				//Adds new states to frontier if they are not already explored or in the frontier 
				if ((!explored.contains(right)) && (!frontier.contains(right))) {
					right.addToPath("Right"); 
					//Returns solution if state is goal state
					if (isGoal(right.getState())) {
						explored.add(right); 
						return right.getPath(); 
					}
					//Otherwise, adds state to frontier
					frontier.add(right);
				}
				if ((!explored.contains(left)) && (!frontier.contains(left))) {
					left.addToPath("Left"); 
					//Returns solution if state is goal state
					if (isGoal(left.getState())) {
						explored.add(left);
						return left.getPath(); 
					}
					//Otherwise, adds state to frontier
					frontier.add(left);
				}
				if ((!explored.contains(up)) && (!frontier.contains(up))) {
					up.addToPath("Up");
					//Returns solution if state is goal state
					if (isGoal(up.getState())) {
						explored.add(up);
						return up.getPath(); 
					}
					//Otherwise, adds state to frontier
					frontier.add(up);
				}
			}
		}
		//For index = 4 (moving the blank space in all directions is allowed) 
		else {
			//Creates new path arrays 
			ArrayList<String> rightPath = new ArrayList<String>(path);
			ArrayList<String> leftPath = new ArrayList<String>(path);
			ArrayList<String> upPath = new ArrayList<String>(path); 
			ArrayList<String> downPath = new ArrayList<String>(path);
			
			//Creates new states
			Puzzle1 right = new Puzzle1(rightState(state, spaceIndex), depth, rightPath); 
			Puzzle1 left = new Puzzle1(leftState(state, spaceIndex), depth, leftPath); 
			Puzzle1 up = new Puzzle1(upState(state, spaceIndex), depth, upPath); 
			Puzzle1 down = new Puzzle1(downState(state, spaceIndex), depth, downPath); 
			
			//Adds new states to frontier if they are not already explored or in the frontier 
			if ((!explored.contains(right)) && (!frontier.contains(right))) {
				right.addToPath("Right"); 
				//Returns solution if state is goal state
				if (isGoal(right.getState())) {
					explored.add(right);
					return right.getPath(); 
				}
				//Otherwise, adds state to frontier
				frontier.add(right);
			}
			if ((!explored.contains(left)) && (!frontier.contains(left))) {
				left.addToPath("Left"); 
				//Returns solution if state is goal state
				if (isGoal(left.getState())) {
					explored.add(left);
					return left.getPath(); 
				}
				//Otherwise, adds state to frontier
				frontier.add(left);
			}
			if ((!explored.contains(up)) && (!frontier.contains(up))) {
				up.addToPath("Up");
				//Returns solution if state is goal state
				if (isGoal(up.getState())) {
					explored.add(up);
					return up.getPath(); 
				}
				//Otherwise, adds state to frontier
				frontier.add(up);
			}
			if ((!explored.contains(down)) && (!frontier.contains(down))) {
				down.addToPath("Down"); 
				//Returns solution if state is goal state
				if (isGoal(down.getState())) {
					explored.add(down); 
					return down.getPath(); 
				}
				//Otherwise, adds state to frontier
				frontier.add(down);
			}
		}
		return notGoal; 
	}
	
	
	/*
	 * Creates the state that is produced when the blank space in the given state is shifted to the left
	 * @param state – state to be altered
	 * @param index – the index of the blank space
	 * @return – the new state
	 */
	public int[] leftState(int[] state, int index) {
		int[] newState = duplicateState(state); 
		newState[index] = newState[index - 1]; 
		newState[index - 1] = 0; 
		return newState; 
	}
	
	/*
	 * Creates the state that is produced when the blank space in the given state is shifted to the right
	 * @param state – state to be altered
	 * @param index – the index of the blank space
	 * @return – the new state
	 */
	public int[] rightState(int[] state, int index) {
		int[] newState = duplicateState(state); 
		newState[index] = newState[index + 1]; 
		newState[index + 1] = 0; 
		return newState; 
	}
	
	/*
	 * Creates the state that is produced when the blank space space in the given state is shifted up
	 * @param state – state to be altered
	 * @param index – the index of the blank space
	 * @return – the new state
	 */
	public int[] upState(int[] state, int index) {
		int[] newState = duplicateState(state); 
		newState[index] = newState[index - 3]; 
		newState[index - 3] = 0; 
		return newState; 
	}
	
	/*
	 * Creates the state that is produced when the blank space space in the given state is shifted down
	 * @param state – state to be altered
	 * @param index – the index of the blank space
	 * @return – the new state
	 */
	public int[] downState(int[] state, int index) {
		int[] newState = duplicateState(state); 
		newState[index] = newState[index + 3]; 
		newState[index + 3] = 0; 
		return newState; 
	}
	
	/*
	 * Finds the index of the blank space 
	 * @param state – state in which the blank space index is to be found
	 * @return – the index of the blank space 
	 */
	public int blankSpace(int[] state) {
		for (int i = 0; i < 9; i++) {
			if (state[i] == 0) return i; 
		}
		return -1; 
	}
	
	/*
	 * Initializes Puzzle, either with given input numbers or through random generator 
	 */
	public void initPuzzle() {
		//Initializes scanner
		Scanner s = new Scanner(System.in); 
		
		//Determines if user wants to add their own input
		System.out.println("If you'd like to enter your own puzzle, type 'YES'. If you'd like a puzzle to be randomly generated, type 'NO'");
		String answer = s.nextLine();
		
		if (answer.equals("YES")) {
			//Takes user's input of initial puzzle state 
			System.out.println("Enter puzzle numbers in row-major order:");
			String initial = s.nextLine();
			int numIndex = 0; 
			for (int i = 0; i < initial.length(); i++) {
				if(initial.charAt(i) != ' ') {
					int num  = Character.getNumericValue(initial.charAt(i)); 
					puzzleState[numIndex] = num; 
					numIndex++; 
				}
			}
		}
		
		else {
			//Generates random initial puzzle state 
			ArrayList<Integer> random = new ArrayList<Integer>(); 
			for (int i = 0; i < 9; i++) {
				random.add(i); 
			}
			Collections.shuffle(random);
			for (int i = 0; i < random.size(); i++) {
				puzzleState[i] = random.get(i); 
			}
		}
		s.close(); 
	}
	
	/*
	 * Duplicates a state 
	 * @param state – state to be duplicated
	 * @return – new, duplicate state 
	 */
	public int[] duplicateState(int[] state) {
		int[] newState = new int[9];
		for (int i = 0; i < 9; i++) {
			newState[i] = state[i];
		}
		return newState; 
	}
	
	/*
	 * Checks if goal state has been reached by comparing a given state to the goal state with the same parity (even or odd)
	 * @param state – state to be checked
	 * @return true if it is the goal state, false otherwise  
	 */
	public Boolean isGoal(int[] state) {
		//If state is parity 0, compares to goal 0 
		if(isParity0(state)) {
			for(int i = 0; i < 9; i++) {
				if(state[i] != goal0[i]) return false; 
			}
			return true; 
		}
		//If state is parity 1, compares to goal 1 
		else {
			for(int i = 0; i < 9; i++) {
				if(state[i] != goal1[i]) return false; 
			}
			return true; 
		}
	}
	
	/*
	 * Checks if a given state is parity 0 
	 * @param state – state to be checked
	 * @return true if the state is parity 0 (even), false if the state is parity 1 (odd)
	 */
	public Boolean isParity0(int[] state) {
		int inversions = 0; 
		//Traverses state to add up inversions
		for (int i = 0; i < 9; i++) {
			for (int j = i + 1; j < 9; j++) {
				if ((state[j] != 0) && (state[i] > state[j])) {
					inversions++;
				}
			}
		}
		if (inversions % 2 == 0) return true; 
		else return false; 
	}
	
	/*
	 * Outputs a state in a row-major order line
	 * @param state – state to be output
	 */
	public void printState(int[] state) {
		for (int i = 0; i < 9; i++) {
			System.out.print(state[i] + " ");
		}
		System.out.println();
	}
	
	/*
	 * Outputs goal state for a given initial puzzle state
	 * @param state – initial puzzle state
	 */
	public void printGoalState(int[] state) {
		if(isParity0(state)) printState(goal0); 
		else printState(goal1); 
	}
	
	/*
	 * Outputs first 3 moves to solution 
	 * @param path – ArrayList of moves from initial state to solution
	 * @param initialState – initial state whose solution path has been found
	 */
	public void printSolutionPath(ArrayList<String> path, int[] initialState) {
		int[] state = initialState; 
		int index = 0; 
		int moves = 3; 
		//Recreates first 3 moves to solution by iterating through path array and adjusting the state accordingly
		if (path.size() == 0) printState(state); //prints initial state if it is the goal state
		else {
			if (path.size() < 3) moves = path.size(); 
			for (int i = 0; i < moves; i++) {
				index = blankSpace(state); 
				if (path.get(i) == "Right") {
					state = rightState(state, index); 
					printState(state);
				}
				else if (path.get(i) == "Left") {
					state = leftState(state, index); 
					printState(state);
				}
				else if (path.get(i) == "Up") {
					state = upState(state, index); 
					printState(state);
				}
				else {
					state = downState(state, index); 
					printState(state);
				}
			}
		}
	}
}
