import java.util.Scanner;
import java.util.Queue;

class functions {

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
}