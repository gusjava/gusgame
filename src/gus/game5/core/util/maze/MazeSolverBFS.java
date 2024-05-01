package gus.game5.core.util.maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MazeSolverBFS extends MazeSolver {

	private List<int[]> queue = new ArrayList<>();
	private Map<String, int[]> parents = new HashMap<>();
	
	public MazeSolverBFS(boolean[][] maze, int[] start, int[] end) throws Exception {
		super(maze, start, end);
		init();
	}
	
	public void init() {
		path = null;
		current = null;
		addToQueue(start);
	}

	public boolean handleStep() {
		if(queue.isEmpty()) return false;
		
		current = queue.remove(0);
		if (isEnd(current)) {
			resolve(current);
			return false;
		}

		for (int[] c : freeNeighbours(current)) {
			addToQueue(c);
			setParent(c, current);
		}
		return true;
	}


	private void addToQueue(int[] p) {
		queue.add(p);
		fill(p);
	}

	private void resolve(int[] p) {
		List<int[]> path_ = new ArrayList<>();
		while (p != null) {
			path_.add(p);
			p = getParent(p);
		}
		int length = path_.size();
		path = new int[length][2];
		for (int i = 0; i < length; i++)
			path[i] = path_.get(length - i - 1);
	}

	private void setParent(int[] c, int[] p) {
		parents.put(toString(c), p);
	}

	private int[] getParent(int[] p) {
		String p_ = toString(p);
		return parents.containsKey(p_) ? parents.get(p_) : null;
	}
}