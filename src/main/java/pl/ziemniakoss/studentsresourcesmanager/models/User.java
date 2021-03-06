package pl.ziemniakoss.studentsresourcesmanager.models;

public class User {
	private int id;
	private String name;
	private String email;
	private boolean admin;
	private boolean student;
	private boolean employee;
	private String www;
	private String scientificTitles;

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

	public User() {
	}

	public User(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public User(int id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public User(int id, String name, String email, boolean admin, boolean student, boolean employee, String www, String titles) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.admin = admin;
		this.student = student;
		this.employee = employee;
		this.www = www;
		this.scientificTitles = titles;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isStudent() {
		return student;
	}

	public void setStudent(boolean student) {
		this.student = student;
	}

	public boolean isEmployee() {
		return employee;
	}

	public void setEmployee(boolean employee) {
		this.employee = employee;
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

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", admin=" + admin +
				", student=" + student +
				", employee=" + employee +
				", www='" + www + '\'' +
				", scientificTitles='" + scientificTitles + '\'' +
				'}';
	}
}
