package pl.ziemniakoss.studentsresourcesmanager.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.ziemniakoss.studentsresourcesmanager.models.Class;
import pl.ziemniakoss.studentsresourcesmanager.models.Course;
import pl.ziemniakoss.studentsresourcesmanager.models.User;
import pl.ziemniakoss.studentsresourcesmanager.repositories.classes.IClassesRepository;
import pl.ziemniakoss.studentsresourcesmanager.repositories.courses.ICourseRepository;
import pl.ziemniakoss.studentsresourcesmanager.repositories.resources.ICourseResourcesRepository;
import pl.ziemniakoss.studentsresourcesmanager.repositories.users.IUserRepository;

/**
 * Tworzenie, usuwanie i edycja klas.
 */
@Controller
public class AdminClassesManagementController {
	private final IClassesRepository classesRepository;
	private final ICourseRepository courseRepository;
	private final IUserRepository userRepository;

	public AdminClassesManagementController(IClassesRepository classesRepository,
											ICourseRepository courseRepository, IUserRepository userRepository) {
		this.classesRepository = classesRepository;
		this.courseRepository = courseRepository;
		this.userRepository = userRepository;
	}

	@GetMapping("/res-admin/classes/{id}")
	public String showClassDetails(@PathVariable(value = "id") int classId, Model model) {
		Class c = classesRepository.get(classId);
		System.out.println(classId);
		if (c == null) {
			return "error_404";
		}
		model.addAttribute("class", c);
		return "class";
	}

	@GetMapping("/res-admin/classes/{id}/delete")
	public String confirmDelete(@PathVariable(value = "id") int classId) {
		return "error_not-implemented";
	}

	@PostMapping("/res-admin/classes/{id}/delete")
	public String deleteClass(@PathVariable(value = "id") int classId) {
		return "error_not-implemented";
	}

	@GetMapping("/res-admin/courses/{courseId}/classes/add")
	public String showClassCreationPanel(Model model, @PathVariable int courseId) {
		Course course = courseRepository.get(courseId);
		if (course == null) {
			return "error_404";
		}
		model.addAttribute("course", course);
		model.addAttribute("employees", userRepository.getAll(null, false, true, false));
		model.addAttribute("newClass", new Class());
		return "admin_classes-create";
	}

	@PostMapping("/res-admin/courses/{courseId}/classes/add")
	public String createNewClass(@PathVariable int courseId, @ModelAttribute Class newClass
								 ) {
		System.out.println(newClass.getSemester());
		Course course = courseRepository.get(courseId);
		if (course == null) {
			return "error_404";
		}
		User coordinator = userRepository.get(newClass.getId());
		if (coordinator == null) {
			return "error_404";
		}
		newClass.setCourse(course);
		newClass.setCoordinator(coordinator);
		classesRepository.add(newClass);
		return "redirect:/res-admin";
	}
}
