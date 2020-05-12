package pl.ziemniakoss.studentsresourcesmanager.repositories.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.ziemniakoss.studentsresourcesmanager.models.Resource;
import pl.ziemniakoss.studentsresourcesmanager.models.User;

import java.io.InputStream;
import java.util.List;

@Repository
public class ResourceRepository implements IResourceRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Resource get(int id) {
		return null;//todo
	}

	@Override
	public Resource exists(int id) {
		return null;//todo
	}

	@Override
	public boolean hasAccess(int id, int userId) {
		return false;//todo
	}

	@Override
	public void addFile(Resource resource, byte [] content, User owner) {//todo
		jdbcTemplate.update("INSERT INTO resources (name, type, content, owner, is_public, description) VALUES (?,'FILE',?,?,?,?);",
				resource.getName(), content, owner.getId(), resource.isAvailableToAll(), resource.getDescription());
	}

	@Override
	public void addExternalResource(Resource resource, String link, User owner) {//todo

	}

	@Override
	public void delete(Resource resource) {//todo

	}

	@Override
	public List<Resource> getAll() {
		return null;//todo
	}

	@Override
	public List<Resource> getAllOwnedBy(int userId) {//todo
		return null;
	}

	@Override
	public List<Resource> getAllOwnedBy(User user) {//todo
		return null;
	}
}
