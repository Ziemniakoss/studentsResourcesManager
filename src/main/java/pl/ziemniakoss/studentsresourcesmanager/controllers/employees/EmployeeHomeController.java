package pl.ziemniakoss.studentsresourcesmanager.controllers.employees;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.ziemniakoss.studentsresourcesmanager.services.courses.ICourseManagementService;

@Controller
public class EmployeeHomeController {
	private final ICourseManagementService courseManager;

	public EmployeeHomeController(ICourseManagementService courseManager) {
		this.courseManager = courseManager;
	}

	@GetMapping("/res-employee")
	public String home(Model model) {
		model.addAttribute("courses", courseManager.getAllOwnedByCurrentUser());
		return "employee_home";
	}
}
