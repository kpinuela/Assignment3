package project;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;



import java.io.BufferedReader;
import java.io.*;
public class RoadTrip extends Graph{
    Hashtable<String, String> aHash;
    Hashtable<String, Boolean> check;
    Hashtable<String, String> temp; 
    Hashtable<String, Integer> distance;
    ArrayList<String> cities;
    ArrayList<String> attractCheck;
    int miles; 
    int minutes;
    Graph graph;

    public RoadTrip(){
        aHash = new Hashtable<>(200);
        check = new Hashtable<>();
        temp = new Hashtable<>();
        distance = new Hashtable<>();
        cities = new ArrayList<>();
        miles = 0;
        minutes = 0;
        graph = new Graph();
         attractCheck=new ArrayList<>();
    }
    public void parseRoads(String roadscsv) { //parses road file and addes them to an ArrayList
        String readRoad="";
        String roadData=roadscsv;
          try (BufferedReader br = new BufferedReader(new FileReader(roadData))){
              while ((readRoad = br.readLine()) != null) {
                  String[] line = readRoad.split(",");
                  int miles = Integer.parseInt(line[2]);
                  int minutes = Integer.parseInt(line[3]);
                  if (line[0]!= null && line[1] != null){
                      graph.addEdge(line[0], line[1], miles, minutes);
                      cities.add(line[0]);
                      cities.add(line[1]);
                  }
              }
          } 
          catch (Exception e){
              System.out.println("Road.csv is missing");
          }
     
    }
    public void parseAttraction(String attractioncsv){ //parses the attraction file and adds to a HashTable and ArrayList
        String attraction = attractioncsv;
        String attractionInfo = "";
        try(BufferedReader read = new BufferedReader(new FileReader(attraction))){
            while((attractionInfo = read.readLine()) != null){
                String[] line = attractionInfo.split(",");
                aHash.put(line[0],line[1]);
                attractCheck.add(line[0]);
            }
        } 
        catch(Exception e){
            System.out.println("attractions.csv is missing");
            
        }
        
    }
    
    
   
    public int milesEdge(String aParam, String bParam) { //the weight for distance in terms of edgest
        int distance=0;
        List<Edge> edges=graph.getEdges();
        for(Edge edge : edges){
           
            if(edge.getStartCity().equals(aParam) && edge.getEndCity().equals(bParam)){
                
                return edge.getMiles();
            }
            else if(edge.getStartCity().equals(bParam) && edge.getEndCity().equals(aParam)){
                
                return edge.getMiles();
            }
        }
        return distance;
       
       
   }
    
    public boolean validAttraction(String userAttractionInput) throws Exception { //checks to see if it is a valid attraction
   	 for(int i=0;i<this.attractCheck.size();i++) {
            if (attractCheck.get(i).equals(userAttractionInput)) 
            {return true;
            }
   	 }
    
   	 
		return false;
    }
    public int minutesEdge(String cParam, String dParam) { // gets the weight in minutes
        int time=0;
        List<Edge> edges=graph.getEdges();
        for(Edge edge : edges){
           
         if(edge.getStartCity().equals(cParam) && edge.getEndCity().equals(dParam)){
             
                return edge.getminutes();
            }
            else if(edge.getStartCity().equals(dParam) && edge.getEndCity().equals(cParam)){
             
                return edge.getminutes();
            }
         
        }
        return time;
    }
    
    public void toString(List<String> path){ 
    	for(String place : path) {
            System.out.print(place+"-->");
        }
        System.out.println("\nDistance and time: "+ miles + " miles " + minutes + " minutes");
    }
    
    
  
    private String minVertex(){ // Finds the minimum vertex in the cities arrayList
        String minVert = "";
        int min = Integer.MAX_VALUE;

        for(String s : cities) {
            if(!check.get(s) && distance.get(s) <= min){
                min = distance.get(s);
                minVert = s;
            }
        }
        return minVert;
    }
    
    List<String> route(String start, String last, List<String> attractions) { //Dijkstra's algorithim to find the shortest path in terms of miles and minutes
        ArrayList<String> router = new ArrayList<>();
        ArrayList<Integer> sortedList = new ArrayList<>();
        ArrayList<String> attractionsList = new ArrayList<>();
        Hashtable<Integer, String> numberVal = new Hashtable<>();
        Hashtable<String, List<String>> adjacencyList= graph.getAdjacents();
        check = new Hashtable<>();
        temp = new Hashtable<>();
        distance = new Hashtable<>();
        graph.addEdge(start, start, 0, 0);
        for(String city : cities){
            if(city != null){
                check.put(city, false);
                distance.put(city, Integer.MAX_VALUE);
            }
        }
        distance.put(start, 0);
        for(String city : cities){
            while(!check.get(city)){
                String vertex = minVertex();
                if(vertex != null){
                    check.put(vertex, true);
                }
                for(String v : adjacencyList.get(vertex)){
                    int weight = milesEdge(vertex, v);
                    if(distance.get(v) > distance.get(vertex) + weight && !v.equals(vertex)){
                        distance.put(v, distance.get(vertex) + weight);
                        temp.put(v, vertex);
                    }
                }
            }
        }
        for(String a : attractions){
            sortedList.add(distance.get(aHash.get(a)));
            numberVal.put(distance.get(aHash.get(a)), a);

        }
        Collections.sort(sortedList);

        for(int att : sortedList){
            attractionsList.add(aHash.get(numberVal.get(att)));
        }

        attractionsList.add(0, start);
        if(attractionsList.contains(last)){
            attractionsList.remove(last);
            attractionsList.add(last);
        }
        else{
            attractionsList.add(last);
        }

        ArrayList <String> attractionCheck = new ArrayList<String>();
        
        for(int i = 0; i < attractionsList.size()-1; i++){
            String vtex = attractionsList.get(i);
            String next = attractionsList.get(i + 1);
            String tempv = attractionsList.get(i + 1);
            attractionCheck.add(next);
            while(!vtex.equals(next)){
                String tempCity = temp.get(next);
                miles += milesEdge(next, tempCity);
                minutes += minutesEdge(next, tempCity);
                attractionCheck.add(tempCity);
                next = tempCity;
            }
            while(!attractionCheck.isEmpty()){
                router.add((String) attractionCheck.remove(0));
            }
           
            for(String city : cities){
                if(city != null){
                    check.put(city, false);
                    distance.put(city, Integer.MAX_VALUE);
                }
            }
            distance.put(tempv, 0);
            for(String city : cities){
                while(!check.get(city)){
                    String vertex = minVertex();
                    if(vertex != null){
                        check.put(vertex, true);
                    }
                    for(String v : adjacencyList.get(vertex)){
                        int weight = milesEdge(vertex, v);
                        if(distance.get(v) > distance.get(vertex) + weight && !v.equals(vertex)){
                            distance.put(v, distance.get(vertex) + weight);
                            temp.put(v, vertex);
                        }
                    }
                }
            }
        }

        return router;
    }
}
    

