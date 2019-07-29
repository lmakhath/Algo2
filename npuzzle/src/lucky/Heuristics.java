package lucky;

import java.util.Scanner;

public class Heuristics extends Functions{

	public int[] CopyPuzzle(int[] newPuzzle, int[] puzzle) {

		for (int i = 0; i < puzzle.length; i++) {
			newPuzzle[i] = puzzle[i];
		}

		return newPuzzle;
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

 	public int Hamming (int[] puzzle, int col) {

		int[] goal = generateGoal(col);
		int ham = 0;
		for (int i = 0; i < puzzle.length; i++) {
			if (puzzle[i] != goal[i] && puzzle[i] != 0)
				ham++;
		}
		return ham;
	}

	private int checkpos (int i, int j, int[] puzzle, int col) {

		int[] goal = generateGoal(col);

		if (puzzle[i] == goal[i] && puzzle[j] == goal[j] && puzzle[i] != 0 
			&& puzzle[j] != 0 && goal[i] != 0 && goal[i] != 0) {
			return 2;
		}
		return 0;
	}

	public int LinerConflict (int[] puzzle, int col) {

		int temp = col;
		int conflict = 0;
		for (int i = 0; i < puzzle.length - 1; i++) {

			if (i + 1 != temp) {
				int[] copypuz = new int[col * col];
				CopyPuzzle(copypuz, puzzle);
				ft_swap(copypuz, i, i + 1);
				conflict += checkpos(i, i + 1, copypuz, col);
			} else if ((temp == i + 1 ) && (i + 1 != col * col))
				temp *= 2;
		}

		for (int i = 0; i < puzzle.length - col; i++) {

			if (i != (col * col) - col) {
				int[] copypuz = new int[col * col];
				CopyPuzzle(copypuz, puzzle);
				ft_swap(copypuz, i, i + col);
				conflict += checkpos(i, i + 1, copypuz, col);
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
			if (nbr.equals("1") || nbr.equals("2") || nbr.equals("3")) {
				input.close();
				return Integer.parseInt(nbr);
			} else {
				System.out.println("Invalid entry.\n");
				System.out.println("Please choose a number to choose a heuristic" 
				+ "\n[1] Manhattan Distance"
				+ "\n[2] Hamming Distance"
				+ "\n[3] Liner Conflict");
			}
		}
		input.close();
		return 0;
	}
 }
