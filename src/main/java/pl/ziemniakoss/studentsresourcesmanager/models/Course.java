package pl.ziemniakoss.studentsresourcesmanager.models;

import org.hibernate.validator.constraints.Length;


public class Course {
	private int id;
	@Length(min = 3, max = 200, message = "Nazwa kursu musi zawierać co najmniej 3 znaki i maksymalnie 200")
	private String name;
	private String description;
	private User coordinator;

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getCoordinator() {
		return coordinator;
	}

	public void setCoordinator(User coordinator) {
		this.coordinator = coordinator;
	}

	@Override
	public String toString() {
		return "Course{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", coordinator=" + coordinator +
				'}';
	}
}
