package pl.ziemniakoss.studentsresourcesmanager.controllers.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.ziemniakoss.studentsresourcesmanager.models.Resource;
import pl.ziemniakoss.studentsresourcesmanager.services.resources.ResourceManagementService;

@Controller
public class EmployeeResourcesManagementController {
	@Autowired
	private ResourceManagementService resourceManager;
	@GetMapping("/res-employee/resources")
	public String showAllResources() {
		return "employee_resources";
	}

	@GetMapping("/res-employee/resources/add")
	public String selectResourceType() {
		return "employee_resources-add-select-type";
	}

	@GetMapping("/res-employee/resources/add/file")
	public String getFileAddingPanel(Model model) {
		model.addAttribute("resource", new Resource());
		return "employee_resources-add-file";
	}

	@PostMapping("/res-employee/resources/add/file")
	public String addFile(@ModelAttribute Resource resourceDetails, @RequestParam("file") MultipartFile file, Model model) {
		resourceManager.addFile(resourceDetails,file);
		System.out.println(resourceDetails);
		return "error_not-implemented";
	}
}
