package pl.ziemniakoss.studentsresourcesmanager.controllers.employees;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.ziemniakoss.studentsresourcesmanager.models.Course;
import pl.ziemniakoss.studentsresourcesmanager.models.Resource;
import pl.ziemniakoss.studentsresourcesmanager.models.ResourceSearch;
import pl.ziemniakoss.studentsresourcesmanager.repositories.courses.ICourseRepository;
import pl.ziemniakoss.studentsresourcesmanager.repositories.resources.ICourseResourcesRepository;
import pl.ziemniakoss.studentsresourcesmanager.repositories.resources.IResourceRepository;
import pl.ziemniakoss.studentsresourcesmanager.repositories.users.IUserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EmployeeCourseResourcesController {
	private final ICourseRepository courseRepository;
	private final ICourseResourcesRepository courseResourcesRepository;
	private final IResourceRepository resourceRepository;
	private final IUserRepository userRepository;

	public EmployeeCourseResourcesController(ICourseRepository courseRepository,
											 ICourseResourcesRepository courseResourcesRepository, IResourceRepository resourceRepository, IUserRepository userRepository) {
		this.courseRepository = courseRepository;
		this.courseResourcesRepository = courseResourcesRepository;
		this.resourceRepository = resourceRepository;
		this.userRepository = userRepository;
	}

	@GetMapping("/res-employee/courses/{courseId}/resources/add-existing")
	public String chooseResource(@PathVariable int courseId, Model model, @ModelAttribute ResourceSearch query) {
		Course c = courseRepository.get(courseId);
		if (c == null) {
			return "error_404";
		}
		if (!c.getCoordinator().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
			return "error_not_owner";
		}

		System.out.println(query.getOwnerId());
		List<Resource> resources = query.getOwnerId() == 0 ?
				resourceRepository.getAll() : resourceRepository.getAllOwnedBy(query.getOwnerId());
		if (query.getOwnerId() != 0) {
			System.out.println("filtr1");
			resources = resources.stream().
					filter(e -> e.getOwner().getId() == query.getOwnerId()).
					collect(Collectors.toList());
		}
		model.addAttribute("employees", userRepository.getAll(null, false, true, false));
		model.addAttribute("query", new ResourceSearch());
		model.addAttribute("resources", resources);
		model.addAttribute("linkedResource", new Resource());
		return "employee_courses-resources-add-existing";
	}

	@PostMapping("/res-employee/courses/{courseId}/resources/add-existing")
	public String linkResourceToCourse(@PathVariable int courseId, Model model, @ModelAttribute Resource linkedResource) {
		System.out.println(linkedResource.getId());
		Course c = courseRepository.get(courseId);
		if (c == null) {
			return "error_404";
		}
		if (!c.getCoordinator().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
			return "error_not_owner";
		}
		try {
			courseResourcesRepository.linkToCourse(courseId, linkedResource.getId());
		} catch (IllegalArgumentException e) {
			model.addAttribute("errorMessage", e.getMessage());
			System.out.println(e.getMessage());
			return "employee_courses-resources-add-existing";
		}

		return "redirect:/res-employee/courses/" + courseId;
	}
}
