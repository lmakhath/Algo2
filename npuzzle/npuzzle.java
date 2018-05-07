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
		OpenList.add(node); //add root node to open list.
		List<Node> lastNode = null;

		boolean goalFound = false;
		
		while (OpenList.size() > 0) { //check if open list is greater than zero.

			while (OpenList.size() > 0) { //check again.

				currentNode = OpenList.get(0); //get first node of openlist.
				OpenList.remove(0); //remove first node of open list.
				CloseList.add(currentNode); // add node to close list.
				f.possibleMoves(currentNode, col, CloseList); //check for possible moves in the node.
				SuperList.add(currentNode); //add current node to list Superlist.
			}

			for (Node TheNode : SuperList) { //get node from superlist
				TempList = TheNode.getChildren(); //get children and save them on TempList

				for (Node tmp : TempList) { // get node for TempList
					
					if (isSolution(tmp.getValue(), col)) { //check if the current node is the solution

						String var = makeString(tmp.getValue()); //if the node is the solution save it as a string
											//var.
						PathToSolution.add(var); //add to list PathToSolution
						var = makeString(TheNode.getValue()); //get the parent
						PathToSolution.add(0, var); // save it on the list at the top.
						while (var != null) {
							
							var = hmap.get(var); //get all parents from hashmap
							if (var != null) {
								PathToSolution.add(0, var);//save every move to the solution 
							}
						}

						TempList.clear();
						SuperList.clear(); //clear everything so that we break out of the loop
						OpenList.clear();
					} else {
						
						String key = makeString(tmp.getValue()); // child
						String value = makeString(TheNode.getValue()); //parent
						hmap.put(key, value); //save parent and child as hashmaps

						OpenList.add(tmp); //add tmp node to open list
					}

					if (TempList.size() == 0) // if the is nothing in the list break
						break ;
				}

				if (SuperList.size() == 0) // if the is nothing in the list break
						break ;
			}
			SuperList.clear(); //clear list
		}

		return PathToSolution; //return a list of the shortest posible moves to the solution
	}

	public String makeString (int[] puz){ //Covert interger array to string.

		String var = null;
		for (int i = 0; i < puz.length; i++) {
			if (var != null)
				var += String.valueOf(puz[i]);
			else
				var = String.valueOf(puz[i]);
		}

		return var;
	}

	public boolean isSolution(int[] puzzle, int col) { // checks if the current puzzle is the same as this.

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

	public static boolean IsSamePuzzle(int[] p, int[] c) { //checks if the puzzle is the same

		boolean samePuzzle = true;

		for (int i = 0; i < p.length; i++) {

			if (p[i] != c[i])
				samePuzzle = false;
		}
		return samePuzzle;
	}

	public static boolean Contains (List<Node> curr, Node node) { //return true of false after checking the puzzle using
									//the method isSamePuzzle

		boolean contains = false;

		for (Node checklist : curr) {
			if (IsSamePuzzle(checklist.getValue(), node.getValue()))
				contains = true;
		}
		return contains;
	}

	public void ft_swap(int[] puzzle, int i, int j) {

		int tmp = puzzle[i];
		puzzle[i] = puzzle[j]; //Just like c Prudy
		puzzle[j] = tmp;
	}

	public int FindIndex(int[] puzzle) { // It finds the zero in the array and returns the possition of that zero (index).

		int position = 0;

		for (int i = 0; i < puzzle.length; i++) {
			if (puzzle[i] == 0)
				position = i;
		}

		return position;
	}

	public int[] CopyPuzzle(int[] newPuzzle, int[] puzzle) {

		for (int i = 0; i < puzzle.length; i++) { //It makes a copy of the puzzle
			newPuzzle[i] = puzzle[i];
		}

		return newPuzzle;
	}

	public void Right (Node newNode, int col, List<Node> curr) { // Move rigth

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

	public void Left (Node newNode, int col, List<Node> curr) { //Move left

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

	public void Down (Node newNode, int col, List<Node> curr) { //move Down

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

	public void Up (Node newNode, int col, List<Node> curr) { //Move up

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
		Right(p, col, curr); //checks all possible moves
		Down(p, col, curr);
		Up(p, col, curr);
	}
}

class npuzzle {

	public static void main(String[] args) {

		functions f = new functions();
		int col = 0;
		if (args.length != 0) { 		// args[0] is not the program name like in c. It is the first argument 
							//which in this case will be the name of the text file we take as input.
			File file = new File(args[0]);
			try {

				Scanner input = new Scanner(file);
				while (input.hasNext()) {	//hasNext is like getnextline
					col = Integer.parseInt(input.next()); 	//Interger.parseInt will convert the charecters into
										// intergers. In this case it will only convert
										// the number at the top which will be our column size
					break ;					// then break out of the loop.
				}

				int[] puzzle = new int[col * col]; //set our column size e.g if column is 3 then it will be 3 * 3
				String tmp = null;
				while (input.hasNext()) {

					String mystring = input.next(); //save the rest of the file as a string.
					if (tmp != null) {
						tmp += " ";
						tmp += mystring;
					} else {
						tmp = mystring;
					}
				}

				String[] tmp2 = tmp.split(" "); // Split the string
				for (int i = 0; i < tmp2.length; i++) {
					puzzle[i] = Integer.parseInt(tmp2[i]); //Conver Split string into intergers
				}
				Node root = new Node(puzzle); // Create our first node

				algo solve = new algo(); 
				List<String> child = solve.BFS(root, col); // Return a list of the shortest path

				for (String nbr : child) { //print all puzzles returned as the shortest path
					System.out.println(nbr);
				}
				
			} catch (FileNotFoundException e) { //If the file doesn't exist
				e.printStackTrace();
			}
		} else if (args.length == 0){
			System.out.println("please enter file name as input."); //return this is the is no file input
		}
	}
}
