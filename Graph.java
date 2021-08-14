package project;
import java.util.*;
public class Graph  { // weighted directed graph

	List<Edge> edges;
	Hashtable<String, List<String>> adjacents;
	
	
	
	public Graph() {
		
		edges = new ArrayList<>();
		adjacents= new Hashtable<>();
		
	}
	public List<Edge> getEdges(){
		return edges;
	}
	public Hashtable<String, List<String>> getAdjacents(){
		return adjacents;
	}
	public void addVertex(String str) {
		adjacents.putIfAbsent(str, new ArrayList<>()); //
		
	}
	
	public void addEdge(String firstVecrtex, String lastVertex, int miles, int mins ) {
		addVertex(firstVecrtex);
		addVertex(lastVertex);
		adjacents.get(firstVecrtex).add(lastVertex);
		adjacents.get(lastVertex).add(firstVecrtex);
		edges.add(new Edge(firstVecrtex,lastVertex,miles,mins));
		
	}
	public void nullCheck() {
		
	}
	

}
