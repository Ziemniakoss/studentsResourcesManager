package pl.ziemniakoss.studentsresourcesmanager.repositories.resources;

import pl.ziemniakoss.studentsresourcesmanager.models.Course;
import pl.ziemniakoss.studentsresourcesmanager.models.Resource;

import java.util.List;

public interface ICourseResourcesRepository {
	List<Resource> getAll(Course course);

	List<Resource> getAll(int courseId);

	void linkToCourse(Course course, Resource resource);

	void linkToCourse(int courseId, int resourceId);

	void deleteFromCourse(Course course, Resource resource);

	void deleteFromCourse(int courseId, int resourceId);
}
