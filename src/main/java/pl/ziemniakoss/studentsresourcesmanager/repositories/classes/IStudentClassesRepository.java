package pl.ziemniakoss.studentsresourcesmanager.repositories.classes;

import pl.ziemniakoss.studentsresourcesmanager.models.Class;
import pl.ziemniakoss.studentsresourcesmanager.models.Course;
import pl.ziemniakoss.studentsresourcesmanager.models.User;

import java.util.List;

public interface IStudentClassesRepository {
	List<Class> getAll(User student);

	List<Class> getAll(int studentId);

	List<Class> getAllInCourse(User student, Course course);

	List<Class> getAllInCourse(int studentId, int courseId);

	void signUp(User student, Class c);

	void signUp(int studentId, int classId);

	void unsubscribe(User student, Class c);

	void unsubscribe(int studentId, int classId);
}
