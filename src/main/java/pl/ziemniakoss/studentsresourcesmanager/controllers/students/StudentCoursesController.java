package pl.ziemniakoss.studentsresourcesmanager.controllers.students;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.ziemniakoss.studentsresourcesmanager.models.Class;
import pl.ziemniakoss.studentsresourcesmanager.models.Course;
import pl.ziemniakoss.studentsresourcesmanager.models.Resource;
import pl.ziemniakoss.studentsresourcesmanager.models.User;
import pl.ziemniakoss.studentsresourcesmanager.repositories.classes.IStudentClassesRepository;
import pl.ziemniakoss.studentsresourcesmanager.repositories.courses.ICourseRepository;
import pl.ziemniakoss.studentsresourcesmanager.repositories.courses.IStudentCoursesRepository;
import pl.ziemniakoss.studentsresourcesmanager.repositories.resources.IClassesResourcesRepository;
import pl.ziemniakoss.studentsresourcesmanager.repositories.resources.ICourseResourcesRepository;
import pl.ziemniakoss.studentsresourcesmanager.repositories.users.IUserRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class StudentCoursesController {
	private final IUserRepository userRepository;
	private final ICourseRepository courseRepository;
	private final ICourseResourcesRepository courseResourcesRepository;
	private final IStudentCoursesRepository studentCoursesRepository;
	private final IClassesResourcesRepository classesResourcesRepository;
	private final IStudentClassesRepository studentClassesRepository;

	public StudentCoursesController(IUserRepository userRepository, ICourseRepository courseRepository,
									ICourseResourcesRepository courseResourcesRepository, IStudentCoursesRepository studentCoursesRepository, IClassesResourcesRepository classesResourcesRepository, IStudentClassesRepository studentClassesRepository) {
		this.userRepository = userRepository;
		this.courseRepository = courseRepository;
		this.courseResourcesRepository = courseResourcesRepository;
		this.studentCoursesRepository = studentCoursesRepository;
		this.classesResourcesRepository = classesResourcesRepository;
		this.studentClassesRepository = studentClassesRepository;
	}

	@GetMapping("/res-student/courses/{courseId}")
	public String showCourseResources(Model model, @PathVariable int courseId){
		User user = userRepository.get(SecurityContextHolder.getContext().getAuthentication().getName());
		Course course = courseRepository.get(courseId);
		if(course == null){
			return "error_404";
		}
		if(!studentCoursesRepository.hasAccess(user, course)){
			return "error_access-denied";
		}
		model.addAttribute("course", course);
		model.addAttribute("courseResources", courseResourcesRepository.getAll(courseId));
		Map<Class, List<Resource>> classesResources = studentClassesRepository.
				getAllInCourse(user, course).parallelStream().
				collect(Collectors.toMap(e -> e, classesResourcesRepository::getAll));
		model.addAttribute("classesResources", classesResources);
		return "student_course";
	}
}
