package pl.ziemniakoss.studentsresourcesmanager.repositories.classes;

import pl.ziemniakoss.studentsresourcesmanager.models.Class;
import pl.ziemniakoss.studentsresourcesmanager.models.Course;
import pl.ziemniakoss.studentsresourcesmanager.models.User;

import java.util.List;

public interface IClassesRepository {
	void add(Class c);

	void update(Class c);

	void delete(Class c);

	Class get(int index);

	/**
	 * Zwraca wszsytkie klasy należące do danego kursu
	 *
	 * @param course kurs dla którego szukamy klas
	 * @return wszystkie klasy należące do podanego kursu
	 */
	List<Class> getAll(Course course);

	List<Class> getAllCoordinatedBy(int coordinatorId);

	List<Class> getAllCoordinatedBy(User coordinator);

	List<Class> getAllAttendedBy(int userId);

	List<Class> getAllAttendedBy(User student);
}
