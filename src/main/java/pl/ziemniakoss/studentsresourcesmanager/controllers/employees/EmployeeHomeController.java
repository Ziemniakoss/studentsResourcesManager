package pl.ziemniakoss.studentsresourcesmanager.controllers.employees;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeHomeController {
	@GetMapping("/res-employee")
	public String home(){
		return "employee-home";
	}
}
