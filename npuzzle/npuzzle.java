import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Queue;

class npuzzle {

	public static long p(int[] puzzle, int col) {

		heuristics h = new heuristics();
		int heuri = h.heuristicloop();
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
			} catch (FileNotFoundException e) {
				System.out.println("File not found");
			}	
		} else if (args.length == 0) {
			System.out.println("please enter file name as input.");
		}

		if (time)
			solvingTime(startTime);
	}
}
