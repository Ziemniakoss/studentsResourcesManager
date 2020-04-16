package pl.ziemniakoss.studentsresourcesmanager.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.RolesAllowed;

@Controller
public class AdminHomeController {

	@GetMapping("/res-admin")
	@RolesAllowed("ADMIN")
	public String home(){
		return "admin_home";

	}
}
