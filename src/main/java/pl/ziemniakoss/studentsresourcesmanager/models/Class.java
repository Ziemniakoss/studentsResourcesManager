package pl.ziemniakoss.studentsresourcesmanager.models;

import javax.validation.constraints.Pattern;

public class Class {
	private int id;
	private User coordinator;
	private Course course;
	@Pattern(regexp = "20[0-9]{2}[LZ]", message = "Semestr musi być w formacie YYYYS, gdzie S to litera 'L' dla letniego a 'Z' dla zimowego")
	private String semester;

	public User getCoordinator() {
		return coordinator;
	}

	public void setCoordinator(User coordinator) {
		this.coordinator = coordinator;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Class{" +
				"id=" + id +
				", coordinator=" + coordinator +
				", course=" + course +
				", semester='" + semester + '\'' +
				'}';
	}
}
