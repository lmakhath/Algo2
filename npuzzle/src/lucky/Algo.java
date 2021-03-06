package lucky;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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

public class Algo extends Functions {

	public List<String> BFS(Node root, int col, int hueri) {
		
		Queue<Node> CloseList = new LinkedList<Node>();
		List<String> PathToSolution = new LinkedList<String>();
		PriorityQueue<Node> OpenList = new PriorityQueue<Node>(new puzzleComparator());
		Queue<Node> TempList = new LinkedList<Node>();
		HashMap<String, String> hmap = new HashMap<String, String>();
		Moves m = new Moves();
		Node currentNode = null;
		OpenList.add(root);
		int moves = 0;

		if (!isSolution(root.getValue(), col)) {
			while (OpenList.size() > 0) {

				currentNode = OpenList.remove();
				m.possibleMoves(currentNode, col, hueri, CloseList);
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
}
