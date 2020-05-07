package pl.ziemniakoss.studentsresourcesmanager.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.ziemniakoss.studentsresourcesmanager.models.Course;
import pl.ziemniakoss.studentsresourcesmanager.models.User;
import pl.ziemniakoss.studentsresourcesmanager.repositories.IUserRepository;
import pl.ziemniakoss.studentsresourcesmanager.services.courses.ICourseManagementService;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Controller
public class CourseManagementController {
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private ICourseManagementService courseManager;

	@GetMapping("/res-admin/courses/add")
	public String addPanel(Model model) {
		var employees = userRepository.getAll(null, false, true, false);
		model.addAttribute("employees", employees);
		model.addAttribute("course",  new Course());
		model.addAttribute("user", new User());
		return "admin_courses-add";
	}

	@PostMapping("/res-admin/courses/add")
	public String addCourse(@ModelAttribute Course course, @ModelAttribute User user, Model model){
		course.setCoordinator(user);
		try{
			courseManager.create(course);
		}catch (IllegalArgumentException e){
			model.addAttribute("errorMessage", e.getMessage());
			System.out.println("lll");
			System.out.println(e.getMessage());
			return "admin_courses-add";
		}
		model.addAttribute("successMessage", "Utworzono kurs");
		return "admin_home";
	}
}
