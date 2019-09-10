import java.util.LinkedList;
import java.util.Queue;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

public class GraphDirected {
	private int weights[][]; //unneeded for BFS and DFS
	
	private int order;
	private int size;
	
	private int time; //counter for DFS
	
	private LinkedList<Node> graph; //list of Nodes

	private LinkedList<Node> topologicalOrder; //result of DFS
	private boolean cycleFlag; //true if cycle detected

	private Graph visualizer; //external library, shows graph visual

	//constructor, takes in n (order) and s (size)
	public GraphDirected(int n, int s)
	{
		visualizer = new SingleGraph("Visualizer");

		int max = (n * (n-1))/2; //if user inputs size too large, defaults to max
		if(s > max)
		{
			s = max;
			System.out.println("Size cannot exceed " + max);
		}
		
		weights = new int[n][n]; //unimplemented
		
		order = n;
		size = s;

		//adding new nodes
		graph = new LinkedList<Node>();
		for(int i = 0; i < order; i++)
		{
			graph.add(new Node());
			visualizer.addNode("" + i); //adding nodes to visualizer
		}
		
		cycleFlag = false; //cycle set to false by default
	
		//randomly generates edges
		generateEdges(size);
		
		//adds labels to visualizer nodes
		for (org.graphstream.graph.Node node : visualizer) {
			node.addAttribute("ui.label", node.getId());
		}
		visualizer.display();
	}

	//generates edges up to the size
	public void generateEdges(int size)
	{
		for(int i = 0; i < size; i++)
		{
			int x = 0;
			int y = 0;
			//randomly selects 2 nodes until and , 
			while(x == y || // they are not equal
					graph.get(x).getNeighbors().contains(graph.get(y)) || //an edge already exists
					graph.get(y).getNeighbors().contains(graph.get(x))) //or adding an edge would create a "2 way edge"
			{
				x = (int) (order * Math.random()); //random number between 0 and order - 1
				y = (int) (order * Math.random());
			}
			graph.get(x).getNeighbors().add(graph.get(y)); //adding y node in neighbor (adjacent) of x
			visualizer.addEdge("" + x + " " + y, "" + x, "" + y, true); //id:xy, edge from x to y, directed: true
			weights[x][y] = (int)(11 * Math.random()); //unimplemented
		}
	}

	//DFS algorithm
	public LinkedList<Node> DFS(int vertex)
	{
		time = 0;
		topologicalOrder = new LinkedList<Node>();
		
		DFS_Visit(graph.get(vertex)); //visits starting node
		
		//explores all other nodes
		for(Node n: graph)
		{
			if(n.getValue() == 0) //if node is unexplored
			{
				DFS_Visit(n);
			}
		}
		return topologicalOrder; //return result (incomplete if cycle exists)
	}

	//recursively visits adjacent nodes
	public void DFS_Visit(Node vertex)
	{
		topologicalOrder.add(vertex); //add to topology
		vertex.setValue(1); //set to explored
		time++;
		vertex.setStart(time);
		
		//explore neighbors
		for(Node a: vertex.neighbors())
		{
			//if start is at initialized value, explore
			if(a.getStart() == -1)
			{
				a.setParent(vertex);
				DFS_Visit(a);
			}
			else if(a.getEnd() == -1) //else the end is at initialized value (still in DFS, hence the cycle)
			{
				cycleFlag = true;
				System.out.println("Cycle detected, topological sort is impossible");
			}
		}
		time++;
		vertex.setEnd(time);
		
		//prints vertex index and the start and end time
		System.out.println("Vertex: " + graph.indexOf(vertex));
		System.out.println("\tStart time: " + vertex.getStart());
		System.out.println("\tEnd time: " + vertex.getEnd());
	}

	//BFS algorithm
	public void BFS(int vertex)
	{
		Node v = graph.get(vertex); //searches starting at user input vertex
		v.setDistance(0); //set initial node distance to 0
		v.setParent(null); //set initial node parent to null
		
		Queue<Node> q = new LinkedList<Node>();
		q.add(v); //add initial node to the queue
		
		Node w;
		while(!q.isEmpty()) //while queue is not empty
		{
			w = q.poll(); //set current node to the popped result of queue
			
			//explore neighbors
			for(Node u : w.getNeighbors())
			{
				//if neighbor is already explored and neighbor is not the start
				if(u.getParent() == null && u != v)
				{
					u.setParent(w); //set neighbors' parent to current
					u.setDistance(w.getDistance() + 1); //increment distance
					
					q.add(u); //add children to queue
					
					//Printing the path from current to initial node
					Node curr = u;
					System.out.println("Vertex " + graph.indexOf(curr) + " is reachable"); //Vertex and index
					System.out.println("\tDistance: " + u.getDistance()); //Vertex Distance
					String path = "\tPath: " + graph.indexOf(curr); //building string of the path
					while(curr.getParent() != null) //while parent != null ( curr != initial)
					{
						path += " <- " + graph.indexOf(curr.getParent());
						curr = curr.getParent();
					}
					System.out.println(path);
				}
			}
		}
	}

	//needed getters and setters below
	
	public LinkedList<Node> getGraph() {
		return graph;
	}

	public void setGraph(LinkedList<Node> graph) {
		this.graph = graph;
	}

	public boolean isCycleFlag() {
		return cycleFlag;
	}

	public void setCycleFlag(boolean cycleFlag) {
		this.cycleFlag = cycleFlag;
	}



}