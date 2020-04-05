package pl.ziemniakoss.studentsresourcesmanager.models;

public class Employee {
	private int id;
	private String name;
	private String email;
	private String www;
	private String scientificTitles;

	public Employee() {
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
