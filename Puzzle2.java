import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Puzzle2 {
	
	//Puzzle2 Variables
	int[] puzzleState;
	int depth;
	ArrayList<String> solutionPath; 
	int priority; 
	
	//Default Constructor
	public Puzzle2() {
		this.puzzleState = new int[9];
		this.depth = 0; 
		this.solutionPath = new ArrayList<String>();
		this.priority = 0; 
	}

	//Constructor with specified values 
	public Puzzle2(int[] puzzle, int depth, ArrayList<String> path, int f) {
		this.puzzleState = puzzle;
		this.depth = depth; 
		this.solutionPath = path;
		this.priority = f; 
	}
	
	//Frontier
	AStarPriorityQueue frontier = new AStarPriorityQueue(); 
	
	//Explored
	ArrayList<Puzzle2> explored = new ArrayList<Puzzle2>(); 
	
	//User's Heuristic Choice
	int heuristicChoice; 
	
	//Goal States for Both Parities 
	int[] goal0 = {1, 2, 3, 4, 5, 6, 7, 8, 0};
	int[] goal1 = {1, 2, 3, 8, 0, 4, 7, 6, 5}; 
	
	//Main Method
	public static void main(String argv[]) {
		
		//Initializes puzzle to user input or random puzzle state
		Puzzle2 puzzle = new Puzzle2();
		puzzle.heuristicChoice = puzzle.initPuzzle();
		int[] initialState = puzzle.getState(); 
		
		
		//Initial Output
		System.out.print("Initial State: ");
		puzzle.printState(initialState);
		System.out.print("Goal State: ");
		puzzle.printGoalState(initialState); 
		System.out.println("Searching...");
		
		long startTime = System.nanoTime();

		//Runs A* Search Search
		ArrayList<String> solution = (puzzle.AStarSearch(initialState)); 
		
		long totalTime = System.nanoTime() - startTime;
		
		//Final Output
		System.out.println("First 3 Moves of A* Search Strategy: ");
		puzzle.printSolutionPath(solution, initialState); 
		System.out.println("Number of Moves Required: " + solution.size());
		System.out.println("Number of States Explored: " + puzzle.explored.size()); 
		System.out.println("Search Time: " + (totalTime / 1000000) + " milliseconds");
	}
	
	/*
	 * Performs A* Search Search to find solution to goal state
	 * @param initial – initial state, the state for which a solution is to be found
	 * @return – ArrayList of strings, the solution path to the goal state 
	 */
	public ArrayList<String> AStarSearch(int[] initial) {
		
		//Initializes the frontier with the initial state
		ArrayList<String> path = new ArrayList<String>(); 
		Puzzle2 puzzle = new Puzzle2(initial, 0, path, 0); 
		frontier.add(puzzle); 
		
		while (!frontier.isEmpty()) { 
			//Removes leaf state from frontier (based priority queue function)
			puzzle = frontier.removeMin(); 
			//Adds state to explored set
			explored.add(puzzle); 
			//Checks if current state is goal state
			if (isGoal(puzzle.getState())) return puzzle.getPath(); 
			else {
				expandState(puzzle.getState(), puzzle.getCost() + 1, puzzle.getPath());
			}
		}
		return puzzle.getPath(); 
	}
	
	/*
	 * Gets state of a Puzzle2 object
	 * @return – integer array of puzzle state 
	 */
	public int[] getState() { 
		return puzzleState; 
	} 
	
	/*
	 * Gets path cost (depth) of a Puzzle2 object's state
	 * @return – integer path cost 
	 */
	public int getCost() { 
		return depth; 
	}
	
	/*
	 * Gets priority of Puzzle2 object's state
	 * @return – integer path cost 
	 */
	public int getPriority() { 
		return priority; 
	}
	
	/*
	 * Gets solution path of a Puzzle2 object's state to the goal state
	 * @return – ArrayList of strings that represent moves to the goal state
	 */
	public ArrayList<String> getPath() { 
		return solutionPath; 
	} 
	
	/*
	 * Adds a move to the solution path of a Puzzle2 object's state to the goal state
	 */
	public void addToPath(String move) {
		solutionPath.add(move); 
	}
	
	/*
	 * Expands a state and adds all new states that are not already in the explored or frontier lists to the frontier 
	 * @param state – state, the state to be expanded
	 * @return – the number of expanded nodes that are added to the frontier 
	 */
	public void expandState(int[] state, int depth, ArrayList<String> path) {
		
		int spaceIndex = blankSpace(state); //gets index of blank space in puzzle 
		
		//For all indices in which a blank space cannot move in all 4 directions
		if (spaceIndex != 4) {
			
			if (spaceIndex == 0) {
				//Right state values 
				ArrayList<String> rightPath = new ArrayList<String>(path);
				int[] rightState = rightState(state, spaceIndex); 
				int rightF = priorityFunction(rightState, depth); 
				
				//Down state values 
				ArrayList<String> downPath = new ArrayList<String>(path);
				int[] downState = downState(state, spaceIndex); 
				int downF = priorityFunction(downState, depth); 
				
				//Creates new Puzzle2 objects 
				Puzzle2 right = new Puzzle2(rightState, depth, rightPath, rightF); 
				Puzzle2 down = new Puzzle2(downState, depth, downPath, downF); 
				
				//Adds new states to frontier if they are not already explored or in the frontier 
				if ((!exploredContains(right)) && (!frontier.contains(right))) {
					right.addToPath("Right"); 
					frontier.add(right);
				}
				if ((!exploredContains(down)) && (!frontier.contains(down))) {
					down.addToPath("Down"); 
					frontier.add(down);
				}
			}
			else if (spaceIndex == 2) {
				//Left state values
				ArrayList<String> leftPath = new ArrayList<String>(path);
				int[] leftState = leftState(state, spaceIndex); 
				int leftF = priorityFunction(leftState, depth); 
				
				//Down state values
				ArrayList<String> downPath = new ArrayList<String>(path);
				int[] downState = downState(state, spaceIndex); 
				int downF = priorityFunction(downState, depth); 
				
				
				//Creates new Puzzle2 objects 
				Puzzle2 left = new Puzzle2(leftState, depth, leftPath, leftF); 
				Puzzle2 down = new Puzzle2(downState, depth, downPath, downF); 
				
				//Adds new states to frontier if they are not already explored or in the frontier 
				if ((!exploredContains(left)) && (!frontier.contains(left))) {
					left.addToPath("Left"); 
					frontier.add(left);
				}
				if ((!exploredContains(down)) && (!frontier.contains(down))) {
					down.addToPath("Down"); 
					frontier.add(down);
				}
			}
			else if (spaceIndex == 6) {
				//Right state values
				ArrayList<String> rightPath = new ArrayList<String>(path); 
				int[] rightState = rightState(state, spaceIndex); 
				int rightF = priorityFunction(rightState, depth); 
				
				//Up state values
				ArrayList<String> upPath = new ArrayList<String>(path);
				int[] upState = upState(state, spaceIndex); 
				int upF = priorityFunction(upState, depth); 
				
				
				//Creates new Puzzle2 objects 
				Puzzle2 right = new Puzzle2(rightState, depth, rightPath, rightF); 
				Puzzle2 up = new Puzzle2(upState, depth, upPath, upF); 
				
				//Adds new states to frontier if they are not already explored or in the frontier 
				if ((!exploredContains(right)) && (!frontier.contains(right))) {
					right.addToPath("Right"); 
					frontier.add(right);
				}
				if ((!exploredContains(up)) && (!frontier.contains(up))) {
					up.addToPath("Up"); 
					frontier.add(up);
				}
			}
			else if (spaceIndex == 8) {
				//Left state values
				ArrayList<String> leftPath = new ArrayList<String>(path);
				int[] leftState = leftState(state, spaceIndex); 
				int leftF = priorityFunction(leftState, depth); 

				//Up state values
				ArrayList<String> upPath = new ArrayList<String>(path);
				int[] upState = upState(state, spaceIndex); 
				int upF = priorityFunction(upState, depth); 
				
				//Creates Puzzle2 objects
				Puzzle2 left = new Puzzle2(leftState, depth, leftPath, leftF); 
				Puzzle2 up = new Puzzle2(upState, depth, upPath, upF); 
				
				//Adds new states to frontier if they are not already explored or in the frontier 
				if ((!exploredContains(left)) && (!frontier.contains(left))) {
					left.addToPath("Left"); 
					frontier.add(left);
				}
				if ((!exploredContains(up)) && (!frontier.contains(up))) {
					up.addToPath("Up"); 
					frontier.add(up);
				}
			}
			else if (spaceIndex == 1) {
				//Right state values 
				ArrayList<String> rightPath = new ArrayList<String>(path);
				int[] rightState = rightState(state, spaceIndex); 
				int rightF = priorityFunction(rightState, depth); 
				
				//Left state values 
				ArrayList<String> leftPath = new ArrayList<String>(path);
				int[] leftState = leftState(state, spaceIndex); 
				int leftF = priorityFunction(leftState, depth); 
				
				//Down state values
				ArrayList<String> downPath = new ArrayList<String>(path);
				int[] downState = downState(state, spaceIndex); 
				int downF = priorityFunction(downState, depth); 
				
				//Creates Puzzle2 objects
				Puzzle2 right = new Puzzle2(rightState, depth, rightPath, rightF); 
				Puzzle2 left = new Puzzle2(leftState, depth, leftPath, leftF); 
				Puzzle2 down = new Puzzle2(downState, depth, downPath, downF);  
				
				//Adds new states to frontier if they are not already explored or in the frontier 
				if ((!exploredContains(right)) && (!frontier.contains(right))) {
					right.addToPath("Right");
					frontier.add(right);
				}
				if ((!exploredContains(left)) && (!frontier.contains(left))) {
					left.addToPath("Left"); 
					frontier.add(left);
				}
				if ((!exploredContains(down)) && (!frontier.contains(down))) {
					down.addToPath("Down"); 
					frontier.add(down);
				}
			}
			else if (spaceIndex == 3) {
				//Right state values 
				ArrayList<String> rightPath = new ArrayList<String>(path);
				int[] rightState = rightState(state, spaceIndex); 
				int rightF = priorityFunction(rightState, depth); 
				
				//Up state values
				ArrayList<String> upPath = new ArrayList<String>(path);
				int[] upState = upState(state, spaceIndex); 
				int upF = priorityFunction(upState, depth); 
				
				//Down state values
				ArrayList<String> downPath = new ArrayList<String>(path);
				int[] downState = downState(state, spaceIndex); 
				int downF = priorityFunction(downState, depth); 
			
				//Creates Puzzle2 objects
				Puzzle2 right = new Puzzle2(rightState, depth, rightPath, rightF); 
				Puzzle2 up = new Puzzle2(upState, depth, upPath, upF); 
				Puzzle2 down = new Puzzle2(downState, depth, downPath, downF);  
				
				//Adds new states to frontier if they are not already explored or in the frontier 
				if ((!exploredContains(right)) && (!frontier.contains(right))) {
					right.addToPath("Right"); 
					frontier.add(right);
				}
				if ((!exploredContains(up)) && (!frontier.contains(up))) {
					up.addToPath("Up"); 
					frontier.add(up);
				}
				if ((!exploredContains(down)) && (!frontier.contains(down))) {
					down.addToPath("Down"); 
					frontier.add(down);
				}
			}
			else if (spaceIndex == 5) {
				//Left state values 
				ArrayList<String> leftPath = new ArrayList<String>(path);
				int[] leftState = leftState(state, spaceIndex); 
				int leftF = priorityFunction(leftState, depth); 
				
				//Up state values
				ArrayList<String> upPath = new ArrayList<String>(path);
				int[] upState = upState(state, spaceIndex); 
				int upF = priorityFunction(upState, depth); 
				
				//Down state values
				ArrayList<String> downPath = new ArrayList<String>(path);
				int[] downState = downState(state, spaceIndex); 
				int downF = priorityFunction(downState, depth); 
				
				//Creates Puzzle2 objects
				Puzzle2 left = new Puzzle2(leftState, depth, leftPath, leftF); 
				Puzzle2 up = new Puzzle2(upState, depth, upPath, upF); 
				Puzzle2 down = new Puzzle2(downState, depth, downPath, downF);  
				
				//Adds new states to frontier if they are not already explored or in the frontier 
				if ((!exploredContains(left)) && (!frontier.contains(left))) {
					left.addToPath("Left"); 
					frontier.add(left);
				}
				if ((!exploredContains(up)) && (!frontier.contains(up))) {
					up.addToPath("Up"); 
					frontier.add(up);
				}
				if ((!exploredContains(down)) && (!frontier.contains(down))) {
					down.addToPath("Down"); 
					frontier.add(down);
				}
			}
			else if (spaceIndex == 7) {
				//Right state values 
				ArrayList<String> rightPath = new ArrayList<String>(path);
				int[] rightState = rightState(state, spaceIndex); 
				int rightF = priorityFunction(rightState, depth); 
				
				//Left state values 
				ArrayList<String> leftPath = new ArrayList<String>(path);
				int[] leftState = leftState(state, spaceIndex); 
				int leftF = priorityFunction(leftState, depth); 
				
				//Up state values
				ArrayList<String> upPath = new ArrayList<String>(path);
				int[] upState = upState(state, spaceIndex); 
				int upF = priorityFunction(upState, depth); 
				
				//Creates Puzzle2 objects
				Puzzle2 right = new Puzzle2(rightState, depth, rightPath, rightF); 
				Puzzle2 left = new Puzzle2(leftState, depth, leftPath, leftF); 
				Puzzle2 up = new Puzzle2(upState, depth, upPath, upF); 
				
				//Adds new states to frontier if they are not already explored or in the frontier 
				if ((!exploredContains(right)) && (!frontier.contains(right))) {
					right.addToPath("Right"); 
					frontier.add(right);
				}
				if ((!exploredContains(left)) && (!frontier.contains(left))) {
					left.addToPath("Left"); 
					frontier.add(left);
				}
				if ((!exploredContains(up)) && (!frontier.contains(up))) {
					up.addToPath("Up"); 
					frontier.add(up);
				}
			}
		}
		//For index = 4 (moving the blank space in all directions is allowed) 
		else {
			//Right state values 
			ArrayList<String> rightPath = new ArrayList<String>(path);
			int[] rightState = rightState(state, spaceIndex); 
			int rightF = priorityFunction(rightState, depth); 
			
			//Left state values 
			ArrayList<String> leftPath = new ArrayList<String>(path);
			int[] leftState = leftState(state, spaceIndex); 
			int leftF = priorityFunction(leftState, depth); 
			
			//Up state values
			ArrayList<String> upPath = new ArrayList<String>(path);
			int[] upState = upState(state, spaceIndex); 
			int upF = priorityFunction(upState, depth); 
			
			//Down state values
			ArrayList<String> downPath = new ArrayList<String>(path);
			int[] downState = downState(state, spaceIndex); 
			int downF = priorityFunction(downState, depth); 
			
			//Creates Puzzle2 objects
			Puzzle2 right = new Puzzle2(rightState, depth, rightPath, rightF); 
			Puzzle2 left = new Puzzle2(leftState, depth, leftPath, leftF); 
			Puzzle2 up = new Puzzle2(upState, depth, upPath, upF); 
			Puzzle2 down = new Puzzle2(downState, depth, downPath, downF); 
			
			//Adds new states to frontier if they are not already explored or in the frontier 
			if ((!exploredContains(right)) && (!frontier.contains(right))) {
				right.addToPath("Right"); 
				frontier.add(right);
			}
			if ((!exploredContains(left)) && (!frontier.contains(left))) {
				left.addToPath("Left"); 
				frontier.add(left);
			}
			if ((!exploredContains(up)) && (!frontier.contains(up))) {
				up.addToPath("Up"); 
				frontier.add(up);
			}
			if ((!exploredContains(down)) && (!frontier.contains(down))) {
				down.addToPath("Down"); 
				frontier.add(down);
			}
		}
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
	 * Initializes puzzle, either with given input numbers or through a random initial puzzle state generator 
	 */
	public int initPuzzle() {
		//Initializes scanner
		Scanner s = new Scanner(System.in); 
		
		//Determines if user wants to add their own input
		System.out.println("If you'd like to enter your own puzzle, type 'YES'. If you'd like a puzzle to be randomly generate, type 'NO'");
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
		//Takes user's input for heuristic type
		System.out.println("Enter which heuristic you would like to use. Type '1' for heuristic 1 and 2 for heuristic '2': ");
		int heuristic = s.nextInt();
		s.close(); 
		return heuristic; 
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
		//If state is parity 0, compares to goal 1
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
	
	/*
	 * Determines priority function value for a given Puzzle2 object using the user's earlier choice of heuristic 
	 * @param puzzle – a Puzzle2 object
	 * @return – integer priority function value for given puzzle
	 */
	public int priorityFunction(int[] state, int cost) {
		int stateh; 
		if (heuristicChoice == 1) stateh = heuristic1(state); 
		else stateh = heuristic2(state); 
		int stateCost = cost; 
		int f = stateh + stateCost; //priority function value = state heuristic + cost of getting to state
		return f; 
	}
	
	/*
	 * Determines heuristic 1 value, the sum of displaced tiles, for a given state
	 * @param state – a given state for which the heuristic 1 value should be calculated
	 * @return – integer heuristic 1 value for given state
	 */
	public int heuristic1(int[] state) {
		int h1 = 0;  
		//If state is parity 0, compare to goal state 0 
		if(isParity0(state)) {
			for(int i = 0; i < 9; i++) {
				//If number at current index is not zero and is in the incorrect location, add to number of displaced tiles
				if ((state[i] != 0) && (state[i] != goal0[i])) h1++;
			}
			return h1; 
		}
		//State is parity 1, so compares to goal state 1 
		else {
			for(int i = 0; i < 9; i++) {
				//If number at current index is not zero and is in the incorrect location, add to number of displaced tiles
				if ((state[i] != 0) && (state[i] != goal1[i])) h1++;
			}
			return h1; 
		}
	}
	
	/*
	 * Determines heuristic 2 value, the sum of Manhattan Distances, for a given state
	 * @param state – a given state for which the heuristic 2 value should be calculated
	 * @return – integer heuristic 2 value for given state
	 */
	public int heuristic2(int[] state) {
		int h2 = 0;
		//If state is parity 0, compare to goal state 0 
		if(isParity0(state)) {
			for(int i = 0; i < 9; i++) {
				if ((state[i] != 0) && (state[i] != goal0[i])) {
					//Determines correct index that number at incorrect index should be at
					int goalIndex = indexOfGoal(state[i], 0); 
					//Adds to Manhattan Distance depending on relationship between current index and goal index 
					if ((goalIndex == i + 2) || (goalIndex == i - 2)) h2 += 2; //"L" shape of 2 blocks
					else if ((goalIndex == i + 3) || (goalIndex == i - 3)) h2 += 1; //straight vertical line of 1 block
					else if ((goalIndex == i + 5) || (goalIndex == i - 5)) h2 += 3; //"L" shape of 3 blocks
					else if ((goalIndex == i + 6) || (goalIndex == i - 6)) h2 += 2; //straight vertical line of 2 blocks
					else if ((goalIndex == i + 7) || (goalIndex == i - 7)) h2 += 3; //"L" shape of 3 blocks
					else if ((goalIndex == i + 8) || (goalIndex == i - 8)) h2 += 4; //"L" shape of 4 blocks
					else if ((goalIndex == i + 4) || (goalIndex == i - 4)) {
						if ((goalIndex == 6) || (goalIndex == 2)) h2 += 4; //odd case of moving numbers from index 2 to 6 or vice versa, "L" shape of 4 blocks
						else h2 += 2; //normal case of "L" shape of 2 blocks
					}
					else if ((goalIndex == i + 1) || (goalIndex == i - 1)) {
						if (((goalIndex == 2) && (i == 3)) || ((goalIndex == 3) && (i == 2)) 
								|| ((goalIndex == 5) && (i == 6)) || ((goalIndex == 6) && (i == 5))) { 
							h2 += 3; //odd case of moving numbers from edge of one row to opposite edge of another row, "L" shape of 3 blocks 
						}
						else h2 += 1; //normal case of straight horizontal line of 1 block
					}
				}
			}
			return h2; 
		}
		//State is parity 1, so compares to goal state 1 
		else {
			for(int i = 0; i < 9; i++) {
				if ((state[i] != 0) && (state[i] != goal1[i])) {
					//Determines correct index that number at incorrect index should be at
					int goalIndex = indexOfGoal(state[i], 1); 
					//Adds to Manhattan Distance depending on relationship between current index and goal index 
					if ((goalIndex == i + 2) || (goalIndex == i - 2)) h2 += 2; //"L" shape of 2 blocks
					else if ((goalIndex == i + 3) || (goalIndex == i - 3)) h2 += 1; //straight vertical line of 1 block
					else if ((goalIndex == i + 5) || (goalIndex == i - 5)) h2 += 3; //"L" shape of 3 blocks
					else if ((goalIndex == i + 6) || (goalIndex == i - 6)) h2 += 2; //straight vertical line of 2 blocks
					else if ((goalIndex == i + 7) || (goalIndex == i - 7)) h2 += 3; //"L" shape of 3 blocks
					else if ((goalIndex == i + 8) || (goalIndex == i - 8)) h2 += 4; //"L" shape of 4 blocks
					else if ((goalIndex == i + 4) || (goalIndex == i - 4)) {
						if ((goalIndex == 6) || (goalIndex == 2)) h2 += 4; //odd case of moving numbers from index 2 to 6 or vice versa, "L" shape of 4 blocks
						else h2 += 2; //normal case of "L" shape of 2 blocks
					}
					else if ((goalIndex == i + 1) || (goalIndex == i - 1)) {
						if (((goalIndex == 2) && (i == 3)) || ((goalIndex == 3) && (i == 2)) 
								|| ((goalIndex == 5) && (i == 6)) || ((goalIndex == 6) && (i == 5))) {
							h2 += 3; //odd case of moving numbers from edge of one row to opposite edge of another row, "L" shape of 3 blocks 
						}
						else h2 += 1; //normal case of straight horizontal line of 1 block
					}
				}
			}
			return h2; 
		}
	}
	
	/*
	 * Determines index of given number within goal state
	 * @param stateNum – a given integer whose index location within the goal state should be found
	 * @param goalType – an integer (0 or 1) that indicates if the number should be found in the goal0 or goal1 state
	 * @return – integer index of the given number in the given goal state
	 */
	public int indexOfGoal(int stateNum, int goalType) {
		if (goalType == 0) {
			for (int i = 0; i < goal0.length; i++) {
				if (stateNum == goal0[i]) {
					return i; 
				}
			}
		}
		else {
			for (int i = 0; i < goal1.length; i++) {
				if (stateNum == goal1[i]) {
					return i; 
				}
			}
		}
		return -1; 
	}
	
	/*
	 * Converts integer array state to string state
	 * @param state – state to be converted to string
	 * @return – string version of given state
	 */
	public String stateToString(int[] state) {
	        String newState = ""; 
		for (int i = 0; i < 9; i++) {
			newState += state[i];
		}
		return newState; 
	}
	
	/*
	 * Traverses explored set and determines if it contains a given Puzzle2 object with the same state
	 * @param puzzle – a Puzzle2 object with the same state
	 * @return – true if the explored set contains a Puzzle2 object with the same state, false otherwise
	 */
	public Boolean exploredContains(Puzzle2 puzzle) {
		for (int i = 0; i < explored.size(); i++) {
			//Checks if two Puzzle2 objects have the same states (by converting those states to comparable strings)
			if (stateToString(explored.get(i).getState()).equals(stateToString(puzzle.getState()))) return true; 
		}
		return false; 
	}
}
