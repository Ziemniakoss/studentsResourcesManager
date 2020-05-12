package pl.ziemniakoss.studentsresourcesmanager.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.ziemniakoss.studentsresourcesmanager.models.Course;
import pl.ziemniakoss.studentsresourcesmanager.models.CourseSearch;
import pl.ziemniakoss.studentsresourcesmanager.models.User;
import pl.ziemniakoss.studentsresourcesmanager.repositories.classes.IClassesRepository;
import pl.ziemniakoss.studentsresourcesmanager.repositories.courses.CourseRepository;
import pl.ziemniakoss.studentsresourcesmanager.repositories.courses.ICourseRepository;
import pl.ziemniakoss.studentsresourcesmanager.repositories.users.IUserRepository;
import pl.ziemniakoss.studentsresourcesmanager.services.courses.ICourseManagementService;


@Controller
public class AdminCourseManagementController {
	private final IUserRepository userRepository;
	private final ICourseManagementService courseManager;
	private final ICourseRepository courseRepository;
	private final IClassesRepository classesRepository;

	public AdminCourseManagementController(IUserRepository userRepository, ICourseManagementService courseManager,
										   ICourseRepository courseRepository, IClassesRepository classesRepository) {
		this.userRepository = userRepository;
		this.courseManager = courseManager;
		this.courseRepository = courseRepository;
		this.classesRepository = classesRepository;
	}

	@GetMapping("/res-admin/courses/add")
	public String addPanel(Model model) {
		var employees = userRepository.getAll(null, false, true, false);
		model.addAttribute("employees", employees);
		model.addAttribute("course", new Course());
		model.addAttribute("user", new User());
		return "admin_courses-add";
	}

	@PostMapping("/res-admin/courses/add")
	public String addCourse(@ModelAttribute Course course, @ModelAttribute User user, Model model) {
		course.setCoordinator(user);
		try {
			courseManager.create(course);
		} catch (IllegalArgumentException e) {
			model.addAttribute("errorMessage", e.getMessage());
			System.out.println("lll");
			System.out.println(e.getMessage());
			return "admin_courses-add";
		}
		model.addAttribute("successMessage", "Utworzono kurs");
		return "admin_home";
	}

	@GetMapping("/res-admin/courses")
	public String showAllCourses(Model model, @ModelAttribute CourseSearch query) {
		//todo query uwzgledniane
		model.addAttribute("courses", courseRepository.getAll());
		model.addAttribute("query", new CourseSearch());
		var employees = userRepository.getAll(null, false, true, false);
		model.addAttribute("employees", employees);
		return "admin_courses";
	}

	@GetMapping("/res-admin/courses/{id}")
	public String showCourseInfo(Model model, @PathVariable int id){
		Course c = courseRepository.get(id);
		if(c == null){
			return "error_404";
		}
		model.addAttribute("course", c);
		model.addAttribute("classList", classesRepository.getAll(c));
		return "admin_course";
	}

	@GetMapping("/res-admin/courses/{id}/delete")
	public String deleteCourse(@PathVariable int id){
		return "error_not-implemented";
	}
}
