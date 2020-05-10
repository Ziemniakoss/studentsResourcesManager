package pl.ziemniakoss.studentsresourcesmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.ziemniakoss.studentsresourcesmanager.models.EmployeeSearch;
import pl.ziemniakoss.studentsresourcesmanager.models.User;
import pl.ziemniakoss.studentsresourcesmanager.repositories.users.IUserRepository;

import java.util.Collection;

@Controller
public class EmployeeSearchController {

	private final IUserRepository userRepository;

	public EmployeeSearchController(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/search/employees")
	public String employee(Model model, @ModelAttribute EmployeeSearch search) {
		model.addAttribute("EmployeeSearch", new EmployeeSearch());
		Collection<User> employees = userRepository.getAll(search.getName(), false, true, false);
		model.addAttribute("EmployeeList", employees);
		return "employees-search";
	}
}
