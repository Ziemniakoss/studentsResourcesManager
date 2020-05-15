package pl.ziemniakoss.studentsresourcesmanager.repositories.courses;

import pl.ziemniakoss.studentsresourcesmanager.models.Course;
import pl.ziemniakoss.studentsresourcesmanager.models.User;

import java.util.List;

public interface IStudentCoursesRepository {
	List<Course> getAll(User student);
	List<Course> getAll(int studentId);
}
