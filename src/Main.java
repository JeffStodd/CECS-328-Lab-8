import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Main {
	public static void main(String [] args) throws NumberFormatException, IOException
	{
		//Reads in user input
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		//getting order and size of graph
		System.out.print("Enter order of the graph: ");
		int order = Integer.parseInt(reader.readLine());
		System.out.print("\nEnter size of the graph: ");
		int size = Integer.parseInt(reader.readLine());
		
		GraphDirected g = new GraphDirected(order,size);

		//getting index of start node (between 0 and order - 1)
		System.out.print("\nEnter starting node for DFS/BFS: ");
		int node = Integer.parseInt(reader.readLine());

		//Running BFS to get reachable vertices
		System.out.println("Part A");
		System.out.println("Potential Paths: ");
		g.BFS(node);

		//Running DFS to get topological order
		System.out.println("Part B");
		System.out.println("Randomizing graph for DFS");
		GraphDirected g2 = new GraphDirected(order,size); //need to declare second graph because weird bug with library

		LinkedList<Node> output = g2.DFS(node); //topologically sorted list
		
		if(!g2.isCycleFlag()) //prints if no cycle was found and sorting was complete
		{
			System.out.println("\nTopological Order: ");
			for(Node n : output)
			{
				System.out.println("Vertex: " + g2.getGraph().indexOf(n));
				System.out.println("\tStart time: " + n.getStart());
				System.out.println("\tEnd time: " + n.getEnd());
			}
		}
	}
}
