package pl.ziemniakoss.studentsresourcesmanager.services.courses;

import pl.ziemniakoss.studentsresourcesmanager.models.Course;

public interface ICourseManagementService {

	void create(Course course);

	void update(Course course);

	void deleteCourse(Course course);
}
