package pl.ziemniakoss.studentsresourcesmanager.controllers.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.ziemniakoss.studentsresourcesmanager.models.Resource;
import pl.ziemniakoss.studentsresourcesmanager.repositories.resources.IResourceRepository;
import pl.ziemniakoss.studentsresourcesmanager.repositories.users.IUserRepository;
import pl.ziemniakoss.studentsresourcesmanager.services.resources.ResourceManagementService;

import java.io.IOException;
import java.rmi.server.ExportException;

@Controller
public class EmployeeResourcesManagementController {
	@Autowired
	private ResourceManagementService resourceManager;
	@Autowired
	private IResourceRepository resourceRepository;
	@Autowired
	private IUserRepository userRepository;
	@GetMapping("/res-employee/resources")
	public String showAllResources(Model model) {
		model.addAttribute("resources", resourceRepository.getAllOwnedBy(userRepository.get(SecurityContextHolder.getContext().getAuthentication().getName())));
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
		try {
			resourceManager.addFile(resourceDetails, file);
			model.addAttribute("successMessage", "Dodano plik");
			return "employee_resources";
		}catch (IllegalArgumentException e){
			model.addAttribute("errorMessage", e.getMessage());
			return "employee_resources-add-file";
		}catch (IOException e){
			model.addAttribute("errorMessage", "Występił nieznany błąd. Spróbuj ponownie póżniej lub skontaktuj się z administratorem");
			return "employee_resources-add-file";
		}
	}
}
