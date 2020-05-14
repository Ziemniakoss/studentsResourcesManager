package pl.ziemniakoss.studentsresourcesmanager.controllers.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.ziemniakoss.studentsresourcesmanager.models.Course;
import pl.ziemniakoss.studentsresourcesmanager.repositories.courses.ICourseRepository;
import pl.ziemniakoss.studentsresourcesmanager.repositories.resources.ICourseResourcesRepository;
import pl.ziemniakoss.studentsresourcesmanager.services.courses.ICourseManagementService;

@Controller
public class EmployeeCoursesController {

	private final ICourseManagementService courseManagementService;
	private final ICourseRepository courseRepository;
	private final ICourseResourcesRepository courseResourcesRepository;

	public EmployeeCoursesController(ICourseManagementService courseManagementService, ICourseRepository courseRepository, ICourseResourcesRepository courseResourcesRepository) {
		this.courseManagementService = courseManagementService;
		this.courseRepository = courseRepository;
		this.courseResourcesRepository = courseResourcesRepository;
	}

	@GetMapping("/res-employee/courses")
	public String showCourses(Model model) {
		model.addAttribute("courses", courseManagementService.getAllOwnedByCurrentUser());
		return "employee_courses";
	}

	@GetMapping("/res-employee/courses/{id}")
	public String showCourseData(@PathVariable int id, Model model){
		var course = courseRepository.get(id);
		if(course == null){
			return "error_404";
		}
		//czy jest właścicielem?
		if(!course.getCoordinator().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
			return "error_not_owner";
		}
		model.addAttribute("course", course);
		model.addAttribute("resources", courseResourcesRepository.getAll(course));
		return "employee_course";
	}

}
