package pl.ziemniakoss.studentsresourcesmanager.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserCreationController {

	@GetMapping("/res-admin/users/add")
	public String get(Model model) {

		return "not-implemented-yet";
	}

	@PostMapping("/res-admin/users/add")
	public String addUser() {
		return "not-implemented-yet";
	}

	@GetMapping("/res-admin/users/mass-add")
	public String getMassAdd(Model model) {
		return "not-implemented-yet";
	}

	@PostMapping("/res-admin/users/mass-add")
	public String addUsersFromFile() {
		return "not-implemented-yet";
	}

}
