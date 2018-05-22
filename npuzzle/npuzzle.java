import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Queue;

class Node {

	private List<Node> children = null;
	private int[] value;
	private int man;
	private int dept;

	public Node (int[] value, int man, int dept) {

		this.children = new LinkedList<Node>();
		this.value = value;
		this.man = man;
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

	public List<Node> getChildren() {
		return children;
	}

	public int getManhattan() {
		return man;
	}
}

class	algo {

	public List<String> BFS(Node node, int col) {
		
		List<Node> OpenList = new LinkedList<Node>();
		List<Node> CloseList = new LinkedList<Node>();
		List<String> PathToSolution = new LinkedList<String>();
		List<Node> TempList = new LinkedList<Node>();
		HashMap<String, String> hmap = new HashMap<String, String>();
		functions f = new functions();
		Node currentNode = null;
		OpenList.add(node);
		int moves = 0;

		boolean goalFound = false;
		int j = 0;
		while (OpenList.size() > 0 && j < 5) {

			currentNode = OpenList.get(0);
			OpenList.remove(0);
			f.possibleMoves(currentNode, col, CloseList);
			CloseList.add(currentNode);

			TempList = currentNode.getChildren();
				
			for (Node tmp : TempList) {
					
				if (isSolution(tmp.getValue(), col)) {

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
					addList(tmp, OpenList);
				}

				if (TempList.size() == 0)
					break ;
			}
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

	private int[] generateGoal(int col) {

 		int[] goal = new int[col * col];
 		for (int i = 0; i < col * col; i++) {
 			if (i == (col * col) - 1)
 				goal[i] = 0;
 			else
 				goal[i] = i + 1;
 		}

 		return goal;
 	}

	public boolean isSolution(int[] puzzle, int col) {

		boolean isgoal = false;
		int[] solution = new int[col * col];
		solution = generateGoal(col);
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

	public void addList(Node puzzle, List<Node> queue) {
		boolean add = false;
		int man = puzzle.getManhattan();
		if (queue.size() != 0) {
			
			int i = 0;
			for (Node current : queue) {

				int man2 = current.getManhattan();
				if (man < man2) {
					add = true;
					break ;
				}
				i++;
			}

			if (add) {
				queue.add(i, puzzle);
			} else {
				queue.add(puzzle);
			}

		} else {
			queue.add(puzzle);
		}
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

	public int Man(int[] puzzle, int col) {

		int ans = 0;
		int[] goal = {1, 2, 3, 4, 5, 6, 7, 8, 0};

		for (int i = 0; i < goal.length - 1; i++) {
 			
 			int j = 0;
 			while (goal[i] != puzzle[j]) {
 				j++;
 			}
 			ans += Math.abs(i / col - j / col) + Math.abs(i % col - j % col);
 		}
 		return ans;
 	}
}

class functions {

	List<Node> mytmp = new LinkedList<Node>();
	Node newNode;

	public boolean IsSamePuzzle(int[] p, int[] c) {

		boolean samePuzzle = true;

		for (int i = 0; i < p.length; i++) {

			if (p[i] != c[i])
				samePuzzle = false;
		}
		return samePuzzle;
	}

	public boolean Contains (List<Node> curr, Node node) {

		boolean contains = false;

		for (Node checklist : curr) {
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

	public void Right (Node newNode, int col, List<Node> curr) {

		this.newNode = newNode;

		int[] puzzle = newNode.getValue();
		int dept = newNode.getdept();
		dept++;

		int i = FindIndex(puzzle);
		
		if (i % col < col - 1) {

			int[] newPuzzle = new int[col * col];
			CopyPuzzle(newPuzzle, puzzle);
			ft_swap(newPuzzle, i, i + 1);

			Node addNode = new Node(newPuzzle, Man(newPuzzle, col), dept);
			if (!Contains(curr, addNode)) {
				mytmp.add(addNode);
			}
		}
	}

	public void Left (Node newNode, int col, List<Node> curr) {

		this.newNode = newNode;

		int[] puzzle = newNode.getValue();
		int dept = newNode.getdept();
		dept++;
		int i = FindIndex(puzzle);
		
		if (i % col > 0) {

			int[] newPuzzle = new int[col * col];
			CopyPuzzle(newPuzzle, puzzle);
			ft_swap(newPuzzle, i, i - 1);

			Node addNode = new Node(newPuzzle, Man(newPuzzle, col), dept);
			if (!Contains(curr, addNode)) {
				mytmp.add(addNode);
			}
		}
	}

	public void Down (Node newNode, int col, List<Node> curr) {

		this.newNode = newNode;

		int[] puzzle = newNode.getValue();
		int dept = newNode.getdept();
		dept++;
		int i = FindIndex(puzzle);
		
		if (i < (col * col) - col) {

			int[] newPuzzle = new int[col * col];
			CopyPuzzle(newPuzzle, puzzle);
			ft_swap(newPuzzle, i, i + col);

			Node addNode = new Node(newPuzzle, Man(newPuzzle, col), dept);
			if (!Contains(curr, addNode)) {
				mytmp.add(addNode);
			}
		}
	}

	public void Up (Node newNode, int col, List<Node> curr) {

		this.newNode = newNode;

		int[] puzzle = newNode.getValue();
		int dept = newNode.getdept();
		dept++;
		int i = FindIndex(puzzle);
		
		if (i > col - 1) {

			int[] newPuzzle = new int[col * col];
			CopyPuzzle(newPuzzle, puzzle);
			ft_swap(newPuzzle, i, i - col);
			
			Node addNode = new Node(newPuzzle, Man(newPuzzle, col), dept);
			if (!Contains(curr, addNode)) {
				mytmp.add(addNode);
			}
		}
	}

	public void possibleMoves(Node p, int col, List<Node> curr) {

		Left(p, col, curr);
		Right(p, col, curr);
		Down(p, col, curr);
		Up(p, col, curr);

		for (Node checkNode : mytmp) {
			if (!Contains(curr, checkNode))
				newNode.addChild(checkNode);
		}
		mytmp.clear();
	}

	public int Man(int[] puzzle, int col) {

		int ans = 0;
		int[] goal = {1, 2, 3, 4, 5, 6, 7, 8, 0};

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
}

class npuzzle {

	public static void main(String[] args) {

		functions f = new functions();
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
						Node root = new Node(puzzle, f.Man(puzzle, col), 0);
			
						algo solve = new algo();
						List<String> child = solve.BFS(root, col);
							
						System.out.println("Complexity in size: " + col);
						for (String nbr : child) {
							System.out.println(nbr);
						}
	
					} else {
						System.out.println("unsolvable");
					}
				} else {
					System.out.println("Invalid characters or number of charactors");
				}
			} catch (FileNotFoundException e){
				e.printStackTrace();
			}
				
		} else if (args.length == 0){
			System.out.println("please enter file name as input.");
		}
	}
}
