package pl.ziemniakoss.studentsresourcesmanager.repositories.resources;

import pl.ziemniakoss.studentsresourcesmanager.models.Resource;
import pl.ziemniakoss.studentsresourcesmanager.models.User;

import java.io.InputStream;
import java.util.List;

public interface IResourceRepository {
	Resource get(int id);

	Resource exists(int id);

	boolean hasAccess(int id, int userId);

	void add(Resource resource, InputStream content);

	void delete(Resource resource);

	List<Resource> getAll();

	List<Resource> getAllOwnedBy(int userId);

	List<Resource> getAllOwnedBy(User user);
}
