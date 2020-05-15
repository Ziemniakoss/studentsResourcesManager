package pl.ziemniakoss.studentsresourcesmanager.controllers.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.ziemniakoss.studentsresourcesmanager.models.Course;
import pl.ziemniakoss.studentsresourcesmanager.models.User;
import pl.ziemniakoss.studentsresourcesmanager.repositories.courses.IStudentCoursesRepository;
import pl.ziemniakoss.studentsresourcesmanager.repositories.users.IUserRepository;

import java.util.Collections;
import java.util.List;

@Controller
public class StudentHomeController {
	private final IStudentCoursesRepository studentCoursesRepository;
	private final IUserRepository userRepository;

	public StudentHomeController(IUserRepository userRepository, IStudentCoursesRepository studentCoursesRepository) {
		this.userRepository = userRepository;
		this.studentCoursesRepository = studentCoursesRepository;
	}

	@GetMapping("/res-student")
	public String home(Model model) {
		User student = userRepository.get(SecurityContextHolder.getContext().getAuthentication().getName());
		List<Course> courses = studentCoursesRepository.getAll(student);
		System.out.println(courses);
		System.out.println(courses.size());
		model.addAttribute("courses", courses);
		return "student_home";
	}
}
