package pl.ziemniakoss.studentsresourcesmanager.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class IndexController {
	@GetMapping("/")
	public String index() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return "redirect:/login";
		}
		List<? extends GrantedAuthority> roles = auth.getAuthorities().stream().filter(e -> e.getAuthority().startsWith("ROLE_")).collect(Collectors.toList());
		if(roles.size() > 1){
			return "home";
		}
		switch (roles.get(0).getAuthority()){
			case "ROLE_ADMIN":
				return "admin_home";
			case "ROLE_STUDENT":
				return "student_home";
			case "ROLE_EMPLOYEE":
				return "employee_home";
		}


		return "index";
	}
}
