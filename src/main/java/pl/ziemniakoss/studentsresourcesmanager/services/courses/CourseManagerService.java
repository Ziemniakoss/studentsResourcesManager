package pl.ziemniakoss.studentsresourcesmanager.services.courses;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.ziemniakoss.studentsresourcesmanager.models.Course;
import pl.ziemniakoss.studentsresourcesmanager.models.User;
import pl.ziemniakoss.studentsresourcesmanager.repositories.ICourseRepository;
import pl.ziemniakoss.studentsresourcesmanager.repositories.IUserRepository;

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
		Assert.isTrue(coordinator.isEmployee(),"koordynator musi być pracownikiem");
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
}
