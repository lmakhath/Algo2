package lucky;

import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class npuzzle extends Functions{

	public static long p(int[] puzzle, int col) {

		Heuristics h = new Heuristics();
		int heuri = h.heuristicloop();
		Node root = new Node(puzzle, 0, 0);
			
		Algo solve = new Algo();
		long startTime = System.currentTimeMillis();
		List<String> child = solve.BFS(root, col, heuri);
							
		System.out.println("Complexity in size: " + col + "\n");
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
	
	public static void getFile(String filename) {
		int col = 0;
		Functions f = new Functions();
		boolean time = false;
		long startTime = 0;
		try {
				File file = new File(filename);
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
		if (time)
			solvingTime(startTime);
	}

	public static void main(String[] args) {
		
		if (args.length != 0) {
			getFile(args[0]);	
		} else if (args.length == 0) {
			System.out.println("please enter file path.");
			Scanner in = new Scanner(System.in);
			getFile(in.next());
			in.close();
		}
	}
}
