package pl.ziemniakoss.studentsresourcesmanager.repositories.resources;

import pl.ziemniakoss.studentsresourcesmanager.models.Class;
import pl.ziemniakoss.studentsresourcesmanager.models.Course;
import pl.ziemniakoss.studentsresourcesmanager.models.Resource;
import pl.ziemniakoss.studentsresourcesmanager.models.ResourceGroup;

import java.util.List;

public interface IResourceGroupRepository {
	void add(ResourceGroup group);

	boolean exists(int groupId);

	ResourceGroup get(int id);

	void delete(ResourceGroup group);

	void update(ResourceGroup group);

	List<ResourceGroup> getAll(Class c);

	List<ResourceGroup> getAll(Course course);

	void addToGroup(Resource resource, ResourceGroup group);

	void deleteFromGroup(Resource resource, ResourceGroup group);

	boolean isResourceIn(int resourceId, int groupId);
}
