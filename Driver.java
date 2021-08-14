package project;
import java.io.*;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver{
	public static void main(String args[]) throws FileNotFoundException, Exception{
        RoadTrip rt = new RoadTrip();
        String roadscsv = "roads.csv";
        String attractionscsv = "attractions.csv";
    
        List<String> userAttractions = new ArrayList<>();
        rt.parseRoads(roadscsv);
        rt.parseAttraction(attractionscsv);
        Scanner scanner= new Scanner(System.in);
        System.out.println("Name of starting city or exit to quit: ");
        String startingCity= scanner.nextLine();
        if(startingCity.equalsIgnoreCase("exit")){
            System.exit(0);
        }
        System.out.println("Name of ending city: ");
        String endingCity= scanner.nextLine();        
        System.out.println("List an attraction along the way (or ENOUGH to stop listing):");
        String firstAttraction= scanner.nextLine();
        userAttractions.add(firstAttraction);
        System.out.println("List an attraction along the way (or ENOUGH to stop listing):");
        while(!(firstAttraction=scanner.nextLine()).equalsIgnoreCase("enough")) {
        	userAttractions.add(firstAttraction);
        }
        List<String> path = rt.route(startingCity, endingCity, userAttractions);
        rt.toString(path);
    }
    }

