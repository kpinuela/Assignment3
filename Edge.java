package project;
public class Edge {
	
	String startCity;
	String endCity;
	int miles;
	int minutes;
	public Edge(String startCity, String endCity, int miles, int minutes) {
		super();
		this.startCity = startCity;
		this.endCity = endCity;
		this.miles = miles;
		this.minutes = minutes;
	}
	public String getStartCity() {
		return startCity;
	}
	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}
	public String getEndCity() {
		return endCity;
	}
	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}
	public int getMiles() {
		return miles;
	}
	public void setMiles(int miles) {
		this.miles = miles;
	}
	public int getminutes() {
		return minutes;
	}
	public void setminutes(int minutes) {
		this.minutes = minutes;
	}

	

}