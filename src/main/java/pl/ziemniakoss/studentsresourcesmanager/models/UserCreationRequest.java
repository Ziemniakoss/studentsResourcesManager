package pl.ziemniakoss.studentsresourcesmanager.models;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserCreationRequest {
	@Size(min = 3, max = 200, message = "Imię musi mieć od 3 do 200 znaków")
	@NotBlank(message = "Imię nie może być puste")
	private String name;
	@NotBlank(message = "Email jest wymagany")
	@Email(message = "Niepoprawny email")
	private String email;
	private String password;// może być puste wtedy autamtycznie generujemy hasło i wysyłamy je do użytkownika na maila
	private boolean student;
	private boolean employee;
	private String www;
	@Size(max = 50, message = "Tytuły naukowe mogą mieć maksymalnie 50 znaków.")
	private String scientificTitles;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	@Override
	public String toString() {
		return "UserCreationRequest{" +
				"name='" + name + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", student=" + student +
				", employee=" + employee +
				", www='" + www + '\'' +
				", scientificTitles='" + scientificTitles + '\'' +
				'}';
	}
}
