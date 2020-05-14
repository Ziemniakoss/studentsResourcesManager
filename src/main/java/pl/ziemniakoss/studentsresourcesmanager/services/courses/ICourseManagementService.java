package pl.ziemniakoss.studentsresourcesmanager.services.courses;

import pl.ziemniakoss.studentsresourcesmanager.models.Course;

import java.util.List;

public interface ICourseManagementService {

	void create(Course course);

	void update(Course course);

	void deleteCourse(Course course);

	List<Course> getAllOwnedByCurrentUser();

	boolean hasAccess(Course course);

	boolean hasAccess(int courseId);
}
