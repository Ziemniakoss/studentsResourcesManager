package pl.ziemniakoss.studentsresourcesmanager.repositories.resources;

import pl.ziemniakoss.studentsresourcesmanager.models.Resource;
import pl.ziemniakoss.studentsresourcesmanager.models.User;

import java.io.InputStream;
import java.util.List;

public interface IResourceRepository {
	Resource get(int id);

	Resource exists(int id);

	boolean hasAccess(int id, int userId);

	void addFile(Resource resource, byte [] content, User owner);

	/**
	 * Dodaje zasób z innego serwera(link lub repozytoium).
	 * @param resource dane nowego zasobu. <b>Pole {@code type} jest wymagane</b>.
	 * @param link link do zdalengo zasobu
	 */
	void addExternalResource(Resource resource, String link, User owner);

	void delete(Resource resource);

	List<Resource> getAll();

	List<Resource> getAllOwnedBy(int userId);

	List<Resource> getAllOwnedBy(User user);
}
