package lucky;

import java.util.Queue;
import java.util.LinkedList;

public class Node {

	private Queue<Node> children = null;
	private int[] value;
	private int hvalue;
	private int depth;

	public Node (int[] value, int hvalue, int depth) {

		this.children = new LinkedList<Node>();
		this.value = value;
		this.hvalue = hvalue;
		this.depth = depth;
	}

	public void addChild(Node child) {
		children.add(child);
	}

	public int[] getValue() {
		return value;
	}

	public int getdept () {
		return depth;
	}

	public Queue<Node> getChildren() {
		return children;
	}

	public int getHeuristicValue() {
		return hvalue;
	}
}
