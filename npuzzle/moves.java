import java.util.Queue;
import java.util.LinkedList;
import java.util.List;

class moves {

	List<Node> mytmp = new LinkedList<Node>();
	heuristics h = new heuristics();
	functions f = new functions();
	Node newNode;

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
			f.ft_swap(newPuzzle, i, i + 1);

			if (heuri == 1)
				heuriValue = h.Man(newPuzzle, col);
			else if (heuri == 2)
				heuriValue = h.Hamming(newPuzzle, col);
			else
				heuriValue = h.LinerConflict(newPuzzle, col);

			Node addNode = new Node(newPuzzle, heuriValue, dept);
			if (!f.Contains(closed, addNode)) {
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
			f.ft_swap(newPuzzle, i, i - 1);

			if (heuri == 1)
				heuriValue = h.Man(newPuzzle, col);
			else if (heuri == 2)
				heuriValue = h.Hamming(newPuzzle, col);
			else
				heuriValue = h.LinerConflict(newPuzzle, col);

			Node addNode = new Node(newPuzzle, heuriValue, dept);
			if (!f.Contains(closed, addNode)) {
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
			f.ft_swap(newPuzzle, i, i + col);

			if (heuri == 1)
				heuriValue = h.Man(newPuzzle, col);
			else if (heuri == 2)
				heuriValue = h.Hamming(newPuzzle, col);
			else
				heuriValue = h.LinerConflict(newPuzzle, col);

			Node addNode = new Node(newPuzzle, heuriValue, dept);
			if (!f.Contains(closed, addNode)) {
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
			f.ft_swap(newPuzzle, i, i - col);

			if (heuri == 1)
				heuriValue = h.Man(newPuzzle, col);
			else if (heuri == 2)
				heuriValue = h.Hamming(newPuzzle, col);
			else
				heuriValue = h.LinerConflict(newPuzzle, col);
			
			Node addNode = new Node(newPuzzle, heuriValue, dept);
			if (!f.Contains(closed, addNode)) {
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
			if (!f.Contains(closed, checkNode))
				newNode.addChild(checkNode);
		}
		mytmp.clear();
	}
}