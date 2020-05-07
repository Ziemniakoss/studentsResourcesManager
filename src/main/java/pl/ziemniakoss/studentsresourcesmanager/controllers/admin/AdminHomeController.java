package pl.ziemniakoss.studentsresourcesmanager.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AdminHomeController {

	@GetMapping("/res-admin")
	public String home(){
		return "admin_home";
	}
}
