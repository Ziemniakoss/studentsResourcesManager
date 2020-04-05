package pl.ziemniakoss.studentsresourcesmanager.repositories;

import pl.ziemniakoss.studentsresourcesmanager.models.Employee;

import java.util.Collection;

public interface IEmployeeRepository {
	Collection<Employee> getAllWithNameLike(String nameLike);
	Employee getEmployeeByEmail(String email);
	Employee getEmployeeById(int id);
	void updateEmployee(Employee employee);
	Collection<Employee> getAll();
}
