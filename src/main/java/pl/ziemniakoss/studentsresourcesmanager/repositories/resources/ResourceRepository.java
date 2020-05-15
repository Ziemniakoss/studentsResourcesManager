package pl.ziemniakoss.studentsresourcesmanager.repositories.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.ziemniakoss.studentsresourcesmanager.models.Resource;
import pl.ziemniakoss.studentsresourcesmanager.models.ResourceType;
import pl.ziemniakoss.studentsresourcesmanager.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ResourceRepository implements IResourceRepository {
	private final String BASE_QUERY =
			"SELECT r.id as r_id, r.name as r_name, r.description as r_description, r.is_public as r_public, r.type as r_type, " +
					" u.id as ow_id, u.email as ow_email, u.name as ow_name, e.scientific_titles as ow_titles, e.www as ow_www " +
					" FROM resources r " +
					" JOIN employees e ON e.id = r.owner " +
					" JOIN users u ON e.id = u.id ";
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
	public void addFile(Resource resource, byte[] content, User owner) {//todo
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
		return jdbcTemplate.query(BASE_QUERY,(rs,rn)->map(rs));	}

	@Override
	public List<Resource> getAllOwnedBy(int userId) {//todo
		return null;
	}

	@Override
	public List<Resource> getAllOwnedBy(User user) {//todo
		return null;
	}

	private Resource map(ResultSet rs) throws SQLException {
		User owner = new User();
		owner.setId(rs.getInt("ow_id"));
		owner.setName(rs.getString("ow_name"));
		owner.setEmail(rs.getString("ow_email"));
		owner.setScientificTitles(rs.getString("ow_titles"));
		owner.setWww(rs.getString("ow_www"));

		Resource resource = new Resource();
		resource.setOwner(owner);
		resource.setId(rs.getInt("r_id"));
		resource.setType(ResourceType.valueOf(rs.getString("r_type")));
		resource.setDescription(rs.getString("r_description"));
		resource.setName(rs.getString("r_name"));
		resource.setAvailableToAll(rs.getBoolean("r_public"));
		return resource;
	}
}
