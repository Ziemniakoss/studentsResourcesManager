package pl.ziemniakoss.studentsresourcesmanager.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.ziemniakoss.studentsresourcesmanager.models.UserSearch;
import pl.ziemniakoss.studentsresourcesmanager.services.IUserSearchService;

@Controller
public class UserSearchController {
	private final IUserSearchService userSearchService;

	public UserSearchController(IUserSearchService userSearchService) {
		this.userSearchService = userSearchService;
	}

	@GetMapping("/res-admin/users")
	public String showUsers(@ModelAttribute UserSearch querry, Model model) {
		model.addAttribute("querry", new UserSearch());
		model.addAttribute("result", userSearchService.getAllMatching(querry));
		return "admin_user-search";
	}
}
