package pl.ziemniakoss.studentsresourcesmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.ziemniakoss.studentsresourcesmanager.models.Employee;
import pl.ziemniakoss.studentsresourcesmanager.models.EmployeeSearch;
import pl.ziemniakoss.studentsresourcesmanager.repositories.EmployeeRepository;

import java.util.Collection;

@Controller
public class EmployeeSearchController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/search/employees")
	public String employee(Model model, @ModelAttribute EmployeeSearch search) {
		model.addAttribute("EmployeeSearch", new EmployeeSearch());
		Collection<Employee> employees = null;
		if(search.getName() == null || search.getName().trim().length() == 0){
			employees = employeeRepository.getAll();
		}else {
			employees = employeeRepository.getAllWithNameLike(search.getName());
		}
		model.addAttribute("EmployeeList", employees);
		return "employees_search";
	}
}
