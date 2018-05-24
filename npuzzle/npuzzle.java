import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.Comparator;
import java.util.PriorityQueue;

class Node {

	private Queue<Node> children = null;
	private int[] value;
	private int hvalue;
	private int dept;

	public Node (int[] value, int hvalue, int dept) {

		this.children = new LinkedList<Node>();
		this.value = value;
		this.hvalue = hvalue;
		this.dept = dept;
	}

	public void addChild(Node child) {
		children.add(child);
	}

	public int[] getValue() {
		return value;
	}

	public int getdept () {
		return dept;
	}

	public Queue<Node> getChildren() {
		return children;
	}

	public int getHeuristicValue() {
		return hvalue;
	}
}

class puzzleComparator implements Comparator <Node> {

	public int compare(Node n1, Node n2) {
		if (n1.getHeuristicValue() > n2.getHeuristicValue()) {
			return 1;
		} else if (n1.getHeuristicValue() < n2.getHeuristicValue()) {
			return -1;
		}
		return 0;
	}
}

class	algo {

	public List<String> BFS(Node root, int col, int hueri) {
		
		Queue<Node> CloseList = new LinkedList<Node>();
		List<String> PathToSolution = new LinkedList<String>();
		PriorityQueue<Node> OpenList = new PriorityQueue<Node>(new puzzleComparator());
		Queue<Node> TempList = new LinkedList<Node>();
		HashMap<String, String> hmap = new HashMap<String, String>();
		functions f = new functions();
		Node currentNode = null;
		OpenList.add(root);
		int moves = 0;

		boolean goalFound = false;

		if (!f.isSolution(root.getValue(), col)) {
			while (OpenList.size() > 0) {

				currentNode = OpenList.remove();
				f.possibleMoves(currentNode, col, hueri, CloseList);
				CloseList.add(currentNode);

				TempList = currentNode.getChildren();
				
				for (Node tmp : TempList) {
					
					if (f.isSolution(tmp.getValue(), col)) {

						moves = tmp.getdept();
						String var = makeString(tmp.getValue()); 
						PathToSolution.add(var);
						var = makeString(currentNode.getValue());
						PathToSolution.add(0, var);
						while (var != null) {
							
							var = hmap.get(var);
							if (var != null) {
								PathToSolution.add(0, var);
							}
						}
						TempList.clear();
						OpenList.clear();

					} else {
						
						String key = makeString(tmp.getValue());
						String value = makeString(currentNode.getValue());
						hmap.put(key, value);
						OpenList.add(tmp);
					}

					if (TempList.size() == 0)
						break ;
				}
		}
	} else {
		PathToSolution.add(makeString(root.getValue()));
	}
		System.out.println("Number of Moves: " + moves);
		return PathToSolution;
	}

	public String makeString (int[] puz){

		String var = null;
		for (int i = 0; i < puz.length; i++) {
			if (var != null)
				var += " " + String.valueOf(puz[i]);
			else
				var = String.valueOf(puz[i]);
		}

		return var;
	}

	public void printNode (Node puzzleNode, int col) {

		int[] puzzle = new int[col * col];
		puzzle = puzzleNode.getValue();
		for (int i = 0; i < puzzle.length; i++) {
			System.out.print(puzzle[i]);
			if ((i + 1) % col == 0)
				System.out.println();
			else
				System.out.print(" ");
		}
		System.out.println();
	}

}

class functions {

	List<Node> mytmp = new LinkedList<Node>();
	Node newNode;

	public int[] generateGoal(int col) {

 		int[] goal = new int[col * col];
 		for (int i = 0; i < col * col; i++) {
 			if (i == (col * col) - 1)
 				goal[i] = 0;
 			else
 				goal[i] = i + 1;
 		}

 		return goal;
 	}

	public boolean IsSamePuzzle(int[] p, int[] c) {

		boolean samePuzzle = true;

		for (int i = 0; i < p.length; i++) {

			if (p[i] != c[i])
				samePuzzle = false;
		}
		return samePuzzle;
	}

	public boolean Contains (Queue<Node> closed, Node node) {

		boolean contains = false;

		for (Node checklist : closed) {
			if (IsSamePuzzle(checklist.getValue(), node.getValue()))
				contains = true;
		}
		return contains;
	}

	public void ft_swap(int[] puzzle, int i, int j) {

		int tmp = puzzle[i];
		puzzle[i] = puzzle[j];
		puzzle[j] = tmp;
	}

	public int FindIndex(int[] puzzle) {

		int position = 0;

		for (int i = 0; i < puzzle.length; i++) {
			if (puzzle[i] == 0)
				position = i;
		}

		return position;
	}

	public int[] CopyPuzzle(int[] newPuzzle, int[] puzzle) {

		for (int i = 0; i < puzzle.length; i++) {
			newPuzzle[i] = puzzle[i];
		}

		return newPuzzle;
	}

	public void Right (Node newNode, int col, int heuri, Queue<Node> closed) {

		this.newNode = newNode;
		int heuriValue = 0;

		int[] puzzle = newNode.getValue();
		int dept = newNode.getdept();
		dept++;

		int i = FindIndex(puzzle);
		
		if (i % col < col - 1) {

			int[] newPuzzle = new int[col * col];
			CopyPuzzle(newPuzzle, puzzle);
			ft_swap(newPuzzle, i, i + 1);

			if (heuri == 1)
				heuriValue = Man(newPuzzle, col);
			else if (heuri == 2)
				heuriValue = Hamming(newPuzzle, col);
			else
				heuriValue = linerConflict(newPuzzle, col);

			Node addNode = new Node(newPuzzle, heuriValue, dept);
			if (!Contains(closed, addNode)) {
				mytmp.add(addNode);
			}
		}
	}

	public void Left (Node newNode, int col, int heuri, Queue<Node> closed) {

		this.newNode = newNode;
		int heuriValue = 0;

		int[] puzzle = newNode.getValue();
		int dept = newNode.getdept();
		dept++;
		int i = FindIndex(puzzle);
		
		if (i % col > 0) {

			int[] newPuzzle = new int[col * col];
			CopyPuzzle(newPuzzle, puzzle);
			ft_swap(newPuzzle, i, i - 1);

			if (heuri == 1)
				heuriValue = Man(newPuzzle, col);
			else if (heuri == 2)
				heuriValue = Hamming(newPuzzle, col);
			else
				heuriValue = linerConflict(newPuzzle, col);

			Node addNode = new Node(newPuzzle, Man(newPuzzle, col), dept);
			if (!Contains(closed, addNode)) {
				mytmp.add(addNode);
			}
		}
	}

	public void Down (Node newNode, int col, int heuri, Queue<Node> closed) {

		this.newNode = newNode;
		int heuriValue = 0;

		int[] puzzle = newNode.getValue();
		int dept = newNode.getdept();
		dept++;
		int i = FindIndex(puzzle);
		
		if (i < (col * col) - col) {

			int[] newPuzzle = new int[col * col];
			CopyPuzzle(newPuzzle, puzzle);
			ft_swap(newPuzzle, i, i + col);

			if (heuri == 1)
				heuriValue = Man(newPuzzle, col);
			else if (heuri == 2)
				heuriValue = Hamming(newPuzzle, col);
			else
				heuriValue = linerConflict(newPuzzle, col);

			Node addNode = new Node(newPuzzle, Man(newPuzzle, col), dept);
			if (!Contains(closed, addNode)) {
				mytmp.add(addNode);
			}
		}
	}

	public void Up (Node newNode, int col, int heuri, Queue<Node> closed) {

		this.newNode = newNode;
		int heuriValue = 0;

		int[] puzzle = newNode.getValue();
		int dept = newNode.getdept();
		dept++;
		int i = FindIndex(puzzle);
		
		if (i > col - 1) {

			int[] newPuzzle = new int[col * col];
			CopyPuzzle(newPuzzle, puzzle);
			ft_swap(newPuzzle, i, i - col);

			if (heuri == 1)
				heuriValue = Man(newPuzzle, col);
			else if (heuri == 2)
				heuriValue = Hamming(newPuzzle, col);
			else
				heuriValue = linerConflict(newPuzzle, col);
			
			Node addNode = new Node(newPuzzle, Man(newPuzzle, col), dept);
			if (!Contains(closed, addNode)) {
				mytmp.add(addNode);
			}
		}
	}

	public void possibleMoves(Node p, int col, int heuri, Queue<Node> closed) {

		Left(p, col, heuri, closed);
		Right(p, col, heuri, closed);
		Down(p, col, heuri, closed);
		Up(p, col, heuri, closed);

		for (Node checkNode : mytmp) {
			if (!Contains(closed, checkNode))
				newNode.addChild(checkNode);
		}
		mytmp.clear();
	}

	public int Man(int[] puzzle, int col) {

		int ans = 0;
		int[] goal = generateGoal(col);

		for (int i = 0; i < goal.length - 1; i++) {
 			
 			int j = 0;
 			while (goal[i] != puzzle[j]) {
 				j++;
 			}
 			ans += Math.abs(i / col - j / col) + Math.abs(i % col - j % col);
 		}
 		return ans;
 	}

 	private int invasion(int[] puzzle) {

		int count = 0;

		for (int i = 0; i < puzzle.length - 1; i++) {

			for (int j = i + 1; j < puzzle.length; j++) {
				if (puzzle[i] > puzzle[j] && puzzle[i] != 0 && puzzle[j] != 0)
					count++;
			}
		}
		return count;
	}

	private int rowZero (int[] puzzle, int col) {

		int row = 0;
		for (int i = 0; i < puzzle.length; i++) {

			if (puzzle[i] == 0) {
				row = i / col;
				break ;
			}
		}
		return row;
	}

	public boolean isSolvable(int[] puzzle, int col) {

		boolean Solvable = false;

		if (col % 2 != 0) {

			if (invasion(puzzle) % 2 == 0)
				Solvable = true;
		} else {

			if ((invasion(puzzle) + rowZero(puzzle, col)) % 2 != 0)
				Solvable = true;
		}

		return Solvable;
	}

	public boolean isDouble (int[] puzzle) {

 		boolean foundDouble = false;

 		for (int i = 0; i < puzzle.length - 1; i++) {

 			for (int j = i + 1; j < puzzle.length; j++) {
 				if (puzzle[i] == puzzle[j]) {
 					foundDouble = true;
 					break ;
 				}
 			}
 		}
 		return foundDouble;
 	}

	public int[] readFile(Scanner input, int col) {

		int[] puzzle = new int[col * col];
		String tmp = null;
		while (input.hasNext()) {

			String mystring = input.next();
			if (tmp != null) {
				tmp += " ";
				tmp += mystring;
			} else {
				tmp = mystring;
			}
		}

		String[] tmp2 = tmp.split(" ");

		if (checkparams(tmp2, col)) {
			for (int i = 0; i < tmp2.length; i++) {
				puzzle[i] = Integer.parseInt(tmp2[i]);
			}

			if (isDouble(puzzle))
				return null;
		} else {
			return null;
		}

		return puzzle;
	}

	public boolean checkparams(String[] str, int col) {

		for (int i = 0; i < str.length; i++) {

			if(!isdigit(str[i])) {
				return false;
			}
		}

		if (col * col != str.length)
			return false;
		return true;
	}

	private boolean isdigit(String str) {

		if (str == null || str.trim().isEmpty()) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public int Hamming (int[] puzzle, int col) {

		int[] goal = generateGoal(col);
		int ham = 0;
		for (int i = 0; i < puzzle.length; i++) {
			if (puzzle[i] != goal[i] && puzzle[i] != 0)
				ham++;
		}

		return ham;
	}

	public boolean isSolution(int[] puzzle, int col) {

		boolean isgoal = false;
		int[] solution = generateGoal(col);
		int i = 0;

		while (i < solution.length) {

			if (puzzle[i] == solution[i]) {
				i++;
				if (i == (col * col))
					isgoal = true;
			} else {
				break ;
			}
		}
		return isgoal;
	}

	private int checkpos (int i, int j, int[] puzzle) {

		int[] goal = {1, 2, 3, 4, 5, 6, 7, 8, 0};

		if (puzzle[i] == goal[i] && puzzle[j] == goal[j] && puzzle[i] != 0 
			&& puzzle[j] != 0 && goal[i] != 0 && goal[i] != 0) {

			return 2;
		}

		return 0;
	}

	public int linerConflict (int[] puzzle, int col) {

		int temp = col;
		int conflict = 0;
		for (int i = 0; i < puzzle.length - 1; i++) {

			if (i + 1 != temp) {
				int[] copypuz = new int[col * col];
				CopyPuzzle(copypuz, puzzle);
				ft_swap(copypuz, i, i + 1);
				conflict += checkpos(i, i + 1, copypuz);
			} else if ((temp == i + 1 ) && (i + 1 != col * col))
				temp *= 2;
		}

		for (int i = 0; i < puzzle.length - col; i++) {

			if (i != (col * col) - col) {
				int[] copypuz = new int[col * col];
				CopyPuzzle(copypuz, puzzle);
				ft_swap(copypuz, i, i + col);
				conflict += checkpos(i, i + 1, copypuz);
			} else
				break ;
		}

		return conflict + Man(puzzle, col);
	}

	public int heuristicloop() {
		
		Scanner input = new Scanner(System.in);
		System.out.println("Please choose a number to choose a heuristic" 
				+ "\n[1] Manhattan Distance"
				+ "\n[2] Hamming Distance"
				+ "\n[3] Liner Conflict");
		while (input.hasNext()) {

			String nbr = input.next();
			if (nbr.equals("1")) {
				return 1;
			} else if (nbr.equals("2")) {
				return 2;
			} else if (nbr.equals("3")) {
				return 3;
			} else {
				System.out.println("Invalid entry.\n");

				System.out.println("Please choose a number to choose a heuristic" 
				+ "\n[1] Manhattan Distance"
				+ "\n[2] Hamming Distance"
				+ "\n[3] Liner Conflict");
			}
		}
		return 0;
	}
}

class npuzzle {

	public static long p(int[] puzzle, int col) {

		functions f = new functions();
		int heuri = f.heuristicloop();
		Node root = new Node(puzzle, 0, 0);
			
		algo solve = new algo();
		long startTime = System.currentTimeMillis();
		List<String> child = solve.BFS(root, col, heuri);
							
		System.out.println("Complexity in size: " + col);
		for (String nbr : child) {
			System.out.println(nbr);
		}

		return startTime;
	}

	public static void solvingTime (long startTime) {
		long endTime = System.currentTimeMillis();
		long sum = (endTime - startTime);
		if (sum / 1000 != 0)
			System.out.println("\n" + sum / 1000 + "." + sum % 1000 + " seconds");
		else 
			System.out.println("\n" + sum + " Milliseconds");
	}

	public static void main(String[] args) {

		long startTime = 0;
		functions f = new functions();
		boolean time = false;
		int col = 0;
		if (args.length != 0) {
			File file = new File(args[0]);
			try {
				Scanner input = new Scanner(file);
				while (input.hasNext()) {
					col = Integer.parseInt(input.next());
					break ;
				}
				int[] puzzle = f.readFile(input, col);
				if (puzzle != null) {
					if (f.isSolvable(puzzle, col)) {

						startTime = p(puzzle, col);
						time = true;
					} else
						System.out.println("unsolvable");
				} else 
					System.out.println("Invalid characters or number of charactors");
			} catch (FileNotFoundException e)
				System.out.println("File not found");
				
		} else if (args.length == 0)
			System.out.println("please enter file name as input.");

		if (time)
			solvingTime(startTime);
	}
}
