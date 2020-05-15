package pl.ziemniakoss.studentsresourcesmanager.repositories.resources;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import pl.ziemniakoss.studentsresourcesmanager.models.Course;
import pl.ziemniakoss.studentsresourcesmanager.models.Resource;
import pl.ziemniakoss.studentsresourcesmanager.models.ResourceType;
import pl.ziemniakoss.studentsresourcesmanager.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CourseResourcesRepository implements ICourseResourcesRepository {
	private final String BASE_QUERY =
			"SELECT r.id as r_id, r.name as r_name, r.type as r_type, r.is_public as r_public, r.description as r_description, " +
					" u.name as o_name, u.email as o_email, u.id as o_id, e.www as o_www, e.scientific_titles as o_titles " +
					" FROM courses_resources cr " +
					" JOIN resources r ON cr.resource_id = r.id " +
					" JOIN employees e ON r.owner = e.id " +
					" JOIN users u ON e.id = u.id ";
	private final JdbcTemplate jdbcTemplate;

	public CourseResourcesRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Resource> getAll(Course course) {
		Assert.notNull(course, "Kurs nie może być nullem");
		return getAll(course.getId());
	}

	@Override
	public List<Resource> getAll(int courseId) {
		return jdbcTemplate.query(BASE_QUERY + " WHERE cr.course_id = ?",
				new Object[]{courseId}, (rs, rn) -> map(rs));
	}

	@Override
	public void linkToCourse(Course course, Resource resource) {
		Assert.notNull(course, "Kurs nie może być nullem");
		Assert.notNull(resource, "Zasób nie może być nullem");
		linkToCourse(course.getId(), resource.getId());
	}

	@Override
	public void linkToCourse(int courseId, int resourceId) {
		var result = jdbcTemplate.queryForObject("SELECT * FROM link_to_course(?, ?);",
				new Object[]{courseId, resourceId}, Integer.class);
		switch (result) {
			case 1:
				throw new IllegalArgumentException("Kurs nie istnieje");
			case 2:
				throw new IllegalArgumentException("Zasób nie istnieje");

		}
	}

	@Override
	public void deleteFromCourse(Course course, Resource resource) {
		Assert.notNull(course, "Kurs nie może być nullem");
		Assert.notNull(resource, "Zasób nie może być nullem");
		deleteFromCourse(course.getId(), resource.getId());
	}

	@Override
	public void deleteFromCourse(int courseId, int resourceId) {
		int result = jdbcTemplate.queryForObject("SELECT * FROM unlink_from_course(?, ?)",
				new Object[]{courseId, resourceId}, Integer.class);
		switch (result) {
			case 1:
				throw new IllegalArgumentException("Kurs nie istnieje");
			case 2:
				throw new IllegalArgumentException("Zasób nie istnieje");
		}
	}

	private Resource map(ResultSet rs) throws SQLException {
		User owner = new User();
		owner.setId(rs.getInt("o_id"));
		owner.setName(rs.getString("o_name"));
		owner.setEmail(rs.getString("o_email"));
		owner.setWww(rs.getString("o_www"));
		owner.setScientificTitles(rs.getString("o_titles"));

		Resource res = new Resource();
		res.setId(rs.getInt("r_id"));
		res.setName(rs.getString("r_name"));
		res.setOwner(owner);
		res.setAvailableToAll(rs.getBoolean("r_public"));
		res.setDescription(rs.getString("r_description"));
		res.setType(ResourceType.valueOf(rs.getString("r_type")));

		return res;
	}
}
