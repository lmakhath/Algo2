import java.util.Queue;
import java.util.LinkedList;

class Node {

	private Queue<Node> children = null;
	private int[] value;
	private int hvalue;
	private int dept;

	public Node (int[] value, int hvalue, int dept) {

		this.children = new LinkedList<Node>();
		this.value = value;
		this.hvalue = hvalue;
		this.dept = dept;
	}

	public void addChild(Node child) {
		children.add(child);
	}

	public int[] getValue() {
		return value;
	}

	public int getdept () {
		return dept;
	}

	public Queue<Node> getChildren() {
		return children;
	}

	public int getHeuristicValue() {
		return hvalue;
	}
}