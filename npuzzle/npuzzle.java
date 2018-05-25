import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Queue;

class npuzzle {

	public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[31m";
	public static final String BLUE = "\u001B[34m";
	public static final String GREEN = "\u001B[32m";

	public static long p(int[] puzzle, int col) {

		heuristics h = new heuristics();
		int heuri = h.heuristicloop();
		Node root = new Node(puzzle, 0, 0);
			
		algo solve = new algo();
		long startTime = System.currentTimeMillis();
		List<String> child = solve.BFS(root, col, heuri);
							
		System.out.println(GREEN + "Complexity in size: " + col + "\n" + RESET);
		for (String nbr : child) {
			System.out.println(nbr);
		}
		return startTime;
	}

	public static void solvingTime (long startTime) {
		long endTime = System.currentTimeMillis();
		long sum = (endTime - startTime);
		if (sum / 1000 != 0)
			System.out.println(BLUE + "\n" + sum / 1000 + "." + sum % 1000 + " seconds");
		else 
			System.out.println(GREEN + "\n" + sum + " Milliseconds");
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
						System.out.println(BLUE + "unsolvable");
				} else 
					System.out.println(RED + "Invalid characters or number of charactors");
			} catch (FileNotFoundException e) {
				System.out.println(RED + "File not found");
			}	
		} else if (args.length == 0) {
			System.out.println(GREEN + "please enter file name as input.");
		}

		if (time)
			solvingTime(startTime);
	}
}
