import java.util.LinkedList;

public class Node {
	private int distance;
	private int value; //0 = not explored
	private int start; //start time
	private int end; //end time
	private Node parent;
	private LinkedList<Node> neighbors; //adjacent nodes (connected by an edge)
	
	public Node()
	{
		start = -1; //initialized to -1
		end = -1; //initialized to -1
		distance = 0; //initialized to 0
		value = 0; //initialized to 0 (unexplored)
		neighbors = new LinkedList<Node>();
		parent = null; //initialized to null
	}
	
	//needed getters and setters below
	
	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		this.value = value;
	}


	public int getStart() {
		return start;
	}



	public void setStart(int start) {
		this.start = start;
	}



	public int getEnd() {
		return end;
	}



	public void setEnd(int end) {
		this.end = end;
	}



	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	

	public int getDistance() {
		return distance;
	}


	public void setDistance(int distance) {
		this.distance = distance;
	}



	public LinkedList<Node> getNeighbors() {
		return neighbors;
	}



	public void setNeighbors(LinkedList<Node> neighbors) {
		this.neighbors = neighbors;
	}



	public void addNeighbor(Node n)
	{
		neighbors.add(n);
	}

	public LinkedList<Node> neighbors()
	{
		return neighbors;
	}
}
