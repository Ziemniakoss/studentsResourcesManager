package pl.ziemniakoss.studentsresourcesmanager.controllers.students;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentHomeController {
	@GetMapping("/res-student")
	public String home(){
		return "student_home";
	}
}
