package pl.ziemniakoss.studentsresourcesmanager.repositories.classes;

import pl.ziemniakoss.studentsresourcesmanager.models.Class;
import pl.ziemniakoss.studentsresourcesmanager.models.Course;

import java.util.List;

public interface IClassesRepository {
	void add(Class c);

	void update(Class c);

	void delete(Class c);

	Class get(int index);

	/**
	 * Zwraca wszsytkie klasy należące do danego kursu
	 *
	 * @param course
	 * @return
	 */
	List<Class> getAll(Course course);
}
