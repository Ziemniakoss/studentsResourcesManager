package pl.ziemniakoss.studentsresourcesmanager.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.ziemniakoss.studentsresourcesmanager.models.Class;
import pl.ziemniakoss.studentsresourcesmanager.repositories.classes.IClassesRepository;

/**
 * Tworzenie, usuwanie i edycja klas.
 */
@Controller
public class AdminClassesManagementController {
	@Autowired
	private IClassesRepository classesRepository;

	@GetMapping("/res-admin/classes/{id}")
	public String showClassDetails(@PathVariable(value = "id") int classId, Model model) {
		Class c = classesRepository.get(classId);
		System.out.println(classId);
		if(c == null){
			return "error_404";
		}
		model.addAttribute("class", c);
		return "class";
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
