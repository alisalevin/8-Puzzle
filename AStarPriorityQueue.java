import java.util.ArrayList;

public class AStarPriorityQueue {
	
	//Priority Queue
	ArrayList<Puzzle2> priorityQueue = new ArrayList<Puzzle2>(); 
	
	/*
	 * Checks if priority queue contains Puzzle2 object
	 * @param puzzle – a Puzzle2 object
	 * @return true if the priority queue contains puzzle, false otherwise 
	 */
	public Boolean contains(Puzzle2 puzzle) {
		if (priorityQueue.contains(puzzle)) return true; 
		else return false; 
	}
	
	/*
	 * Checks if priority queue is empty
	 * @return true if the priority queue is empty, false otherwise 
	 */
	public Boolean isEmpty() {
		if (priorityQueue.isEmpty()) return true; 
		else return false; 
	}
	
	/*
	 * Adds Puzzle2 object to priority queue 
	 * @param puzzle – a Puzzle2 object
	 */
	public void add(Puzzle2 puzzle) {
		priorityQueue.add(puzzle); 
	}

	/*
	 * Removes Puzzle2 object with minimum priority function value from priority queue 
	 * @return Puzzle2 object with minimum priority function value
	 */
	public Puzzle2 removeMin() {
	
		Puzzle2 minPuzzle = priorityQueue.get(0);
		int puzzleF = priorityQueue.get(0).getPriority(); 
		
		//Traverses priority queue for puzzle object with minimum priority function value
		for (int i = 1; i < priorityQueue.size(); i++) {
			int stateF  = priorityQueue.get(i).getPriority(); 
			if (stateF < puzzleF) {
				minPuzzle = priorityQueue.get(i); 
				puzzleF = stateF; 
			}
		}
		//Gets index of puzzle with minimum priority function and returns that puzzle
		int index = priorityQueue.indexOf(minPuzzle);
		return priorityQueue.remove(index); 
	}
}
