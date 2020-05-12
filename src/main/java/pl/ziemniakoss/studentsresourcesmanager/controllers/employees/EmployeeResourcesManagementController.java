package pl.ziemniakoss.studentsresourcesmanager.controllers.employees;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeResourcesManagementController {
	@GetMapping("/res-employee/resources")
	public String showAllResources(){
		return "employee_resources";
	}

	@GetMapping("/res-employee/resources/add")
	public String selectResourceType(){
		return "employee_resources-add-select-type";
	}
}
