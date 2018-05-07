import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class Node {

	private List<Node> children = null;
	private int[] value;

	public Node (int[] value) {

		this.children = new ArrayList<Node>();
		this.value = value;
	}

	public void addChild(Node child) {
		children.add(child);
	}

	public int[] getValue() {
		return value;
	}

	public List<Node> getChildren() {
		return children;
	}
}

class	algo {
	
	public List<String> BFS(Node node, int col) {
		
		List<Node> OpenList = new ArrayList<Node>();
		List<Node> CloseList = new ArrayList<Node>();
		List<String> PathToSolution = new ArrayList<String>();
		List<Node> SuperList = new ArrayList<Node>();
		List<Node> TempList = new ArrayList<Node>();
		HashMap<String, String> hmap = new HashMap<String, String>();
		functions f = new functions();
		Node currentNode = null;
		OpenList.add(node);
		List<Node> lastNode = null;

		boolean goalFound = false;
		
		while (OpenList.size() > 0) {

			while (OpenList.size() > 0) {

				currentNode = OpenList.get(0);
				OpenList.remove(0);
				CloseList.add(currentNode);
				f.possibleMoves(currentNode, col, CloseList);
				SuperList.add(currentNode);
			}

			for (Node TheNode : SuperList) {
				TempList = TheNode.getChildren();

				for (Node tmp : TempList) {
					
					if (isSolution(tmp.getValue(), col)) {

						String var = makeString(tmp.getValue()); 
						PathToSolution.add(var);
						var = makeString(TheNode.getValue());
						PathToSolution.add(0, var);
						while (var != null) {
							
							var = hmap.get(var); //returns null
							if (var != null) {
								PathToSolution.add(0, var);
							}
						}

						TempList.clear();
						SuperList.clear();
						OpenList.clear();
					} else {
						
						String key = makeString(tmp.getValue());
						String value = makeString(TheNode.getValue());
						hmap.put(key, value);

						OpenList.add(tmp);
					}

					if (TempList.size() == 0)
						break ;
				}

				if (SuperList.size() == 0)
						break ;
			}
			SuperList.clear();
		}

		return PathToSolution;
	}

	public void PathTrack(List<Node> path, Node n) {

		System.out.println("Tracing path ...");

		List<Node> Children = n.getChildren();
		
		ListIterator it = Children.listIterator();

		while (it.hasPrevious()) {

			Node newNode = (Node)it.previous();
			path.add(newNode);
		}
	}

	public String makeString (int[] puz){

		String var = null;
		for (int i = 0; i < puz.length; i++) {
			if (var != null)
				var += String.valueOf(puz[i]);
			else
				var = String.valueOf(puz[i]);
		}

		return var;
	}

	public boolean isSolution(int[] puzzle, int col) {

		boolean isgoal = false;
		int[] solution = {1, 2, 3, 4, 5, 6, 7, 8, 0};
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
}

class functions {

	public static boolean IsSamePuzzle(int[] p, int[] c) {

		boolean samePuzzle = true;

		for (int i = 0; i < p.length; i++) {

			if (p[i] != c[i])
				samePuzzle = false;
		}
		return samePuzzle;
	}

	public static boolean Contains (List<Node> curr, Node node) {

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

		int[] puzzle = newNode.getValue();
		int i = FindIndex(puzzle);
		
		if (i % col < col - 1) {

			int[] newPuzzle = new int[col * col];
			CopyPuzzle(newPuzzle, puzzle);
			ft_swap(newPuzzle, i, i + 1);

			if (!Contains(curr, (new Node(newPuzzle)))) {
				newNode.addChild(new Node(newPuzzle));
			}
		}
	}

	public void Left (Node newNode, int col, List<Node> curr) {

		int[] puzzle = newNode.getValue();
		int i = FindIndex(puzzle);
		
		if (i % col > 0) {

			int[] newPuzzle = new int[col * col];
			CopyPuzzle(newPuzzle, puzzle);
			ft_swap(newPuzzle, i, i - 1);

			if (!Contains(curr, (new Node(newPuzzle)))) {
				newNode.addChild(new Node(newPuzzle));
			}
		}

	}

	public void Down (Node newNode, int col, List<Node> curr) {

		int[] puzzle = newNode.getValue();
		int i = FindIndex(puzzle);
		
		if (i < (col * col) - col) {

			int[] newPuzzle = new int[col * col];
			CopyPuzzle(newPuzzle, puzzle);
			ft_swap(newPuzzle, i, i + col);

			if (!Contains(curr, (new Node(newPuzzle)))) {
				newNode.addChild(new Node(newPuzzle));
			}
		}
	}

	public void Up (Node newNode, int col, List<Node> curr) {

		int[] puzzle = newNode.getValue();
		int i = FindIndex(puzzle);
		
		if (i > col - 1) {

			int[] newPuzzle = new int[col * col];
			CopyPuzzle(newPuzzle, puzzle);
			ft_swap(newPuzzle, i, i - col);
			
			if (!Contains(curr, (new Node(newPuzzle)))) {
				newNode.addChild(new Node(newPuzzle));
			}
		}
	}

	public void possibleMoves(Node p, int col, List<Node> curr) {

		Left(p, col, curr);
		Right(p, col, curr);
		Down(p, col, curr);
		Up(p, col, curr);
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
				for (int i = 0; i < tmp2.length; i++) {
					puzzle[i] = Integer.parseInt(tmp2[i]); 
				}
				Node root = new Node(puzzle);

				algo solve = new algo();
				List<String> child = solve.BFS(root, col);

				for (String nbr : child) {
					System.out.println(nbr);
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else if (args.length == 0){
			System.out.println("please enter file name as input.");
		}
	}
}
