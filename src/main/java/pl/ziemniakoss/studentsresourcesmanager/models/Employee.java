package pl.ziemniakoss.studentsresourcesmanager.models;

public class Employee extends User {
	private String www;
	private String scientificTitles;

	public Employee() {
	}

	public Employee(int id, String name, String email, String www, String scientificTitles) {
		super(id, name, email);
		this.www = www;
		this.scientificTitles = scientificTitles;
	}

	public Employee(String name, String email, String www, String scientificTitles) {
		super(name, email);
		this.www = www;
		this.scientificTitles = scientificTitles;
	}

	public String getWww() {
		return www;
	}

	public void setWww(String www) {
		this.www = www;
	}

	public String getScientificTitles() {
		return scientificTitles;
	}

	public void setScientificTitles(String scientificTitles) {
		this.scientificTitles = scientificTitles;
	}
}
