package pl.ziemniakoss.studentsresourcesmanager.repositories.resources;

import pl.ziemniakoss.studentsresourcesmanager.models.Class;
import pl.ziemniakoss.studentsresourcesmanager.models.Resource;

import java.util.List;

public interface IClassesResourcesRepository {
	List<Resource> getAll(Class c);

	List<Resource> getAll(int classId);

	void linkToClass(Class c, Resource resource);

	void linkToClass(int classId, int resourceId);

	void deleteFromClass(Class c, Resource resource);

	void deleteFromClass(int classId, int resourceId);
}
