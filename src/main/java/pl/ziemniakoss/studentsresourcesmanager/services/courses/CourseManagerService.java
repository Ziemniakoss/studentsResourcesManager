package pl.ziemniakoss.studentsresourcesmanager.services.courses;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.ziemniakoss.studentsresourcesmanager.models.Course;
import pl.ziemniakoss.studentsresourcesmanager.models.User;
import pl.ziemniakoss.studentsresourcesmanager.repositories.courses.ICourseRepository;
import pl.ziemniakoss.studentsresourcesmanager.repositories.users.IUserRepository;

import java.util.List;

@Service
public class CourseManagerService implements ICourseManagementService {
	private final ICourseRepository courseRepository;
	private final IUserRepository userRepository;

	public CourseManagerService(ICourseRepository courseRepository, IUserRepository userRepository) {
		this.courseRepository = courseRepository;
		this.userRepository = userRepository;
	}

	/**
	 * Tworzy nowy kurs ale gdy spełnia warunki:
	 * <ul>
	 *     <li>Kurs ma koordynatora i jest nim osoba będąca pracownikiem</li>
	 *     <li>Kurs ma nazwę o długości od 3 do 200 znaków włącznie</li>
	 * </ul>
	 *
	 * @param course kurs do utworzenia
	 */
	@Override
	public void create(Course course) {
		Assert.notNull(course, "Kurs nie może być nullem");
		Assert.notNull(course.getName(), "Kurs musi mieć nazwę");
		Assert.isTrue(course.getName().length() >= 3 && course.getName().length() <= 200,
				"Nazwa kursu musi mię od 3 do 200 znaków");
		Assert.notNull(course.getCoordinator(), "Kurs musi mieć koordynatora");

		User coordinator = userRepository.get(course.getCoordinator().getId());
		Assert.notNull(coordinator, "koordynator nie istnieje");
		Assert.isTrue(coordinator.isEmployee(), "koordynator musi być pracownikiem");
		course.setCoordinator(coordinator);
		courseRepository.create(course);
	}

	@Override
	public void update(Course course) {
		//todo
	}

	@Override
	public void deleteCourse(Course course) {
		//todo
	}

	@Override
	public List<Course> getAllOwnedByCurrentUser() {
		return courseRepository.getAllCoordinatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@Override
	public boolean hasAccess(Course course) {
		Assert.notNull(course, "Kurs nie może być nullem");
		return hasAccess(course.getId());
	}

	@Override
	public boolean hasAccess(int courseId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return false;
		}
		Course course = courseRepository.get(courseId);
		if(course == null){
			return false;
		}
		boolean hasAccess = false;
		for (GrantedAuthority a : auth.getAuthorities()) {
			if ("ROLE_ADMIN".equals(a.getAuthority())) {
				return true;
			}
			if ("ROLE_EMPLOYEE".equals(a.getAuthority())) {
				if (course.getCoordinator().getEmail().equals(auth.getName()))
					return true;
			}
			if("ROLE_STUDENT".equals(a.getAuthority())){
				//todo
			}
		}
		return hasAccess;
	}
}
