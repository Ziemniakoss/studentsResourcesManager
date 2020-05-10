package pl.ziemniakoss.studentsresourcesmanager.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Tworzenie, usuwanie i edycja klas.
 */
@Controller
public class AdminClassesManagementController {

	@GetMapping("/res-admin/classes/{id}")
	public String showClassDetails(@PathVariable(value = "id") int classId) {
		return "error_not-implemented";
	}

	@GetMapping("/res-admin/classes/{id}/delete")
	public String confirmDelete(@PathVariable(value = "id") int classId) {
		return "error_not-implemented";
	}

	@PostMapping("/res-admin/classes/{id}/delete")
	public String deleteClass(@PathVariable(value = "id") int classId){
		return "error_not-implemented";
	}
}
