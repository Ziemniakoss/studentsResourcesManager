package pl.ziemniakoss.studentsresourcesmanager.repositories.courses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import pl.ziemniakoss.studentsresourcesmanager.models.Course;
import pl.ziemniakoss.studentsresourcesmanager.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CourseRepository implements ICourseRepository {
	private final Logger log = LoggerFactory.getLogger(CourseRepository.class);
	private final JdbcTemplate jdbcTemplate;
	private final String BASE_QUERY =
			" SELECT c.id as c_id, c.name as c_name, description as c_description, " +
					"coordinator as e_id, u.name as e_name, u.email as e_email, e.www as e_www, e.scientific_titles as e_titles" +
					" FROM courses c" +
					" JOIN employees e ON c.coordinator = e.id " +
					" JOIN users u ON e.id = u.id";

	public CourseRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void create(Course course) {
		if (course == null) {
			throw new IllegalArgumentException("Course can't be null");
		}
		if (course.getCoordinator() == null) {
			log.error("Attempting to create course without coordinator");
			throw new IllegalArgumentException("Coordinator can't be null");
		}
		Integer result = jdbcTemplate.queryForObject("SELECT  * FROM create_course(?,?,?)", new Object[]{
				course.getName(), course.getCoordinator().getEmail(), course.getDescription()}, Integer.class);
		if (result == null) {
			log.error("Result of course creation query should be integer, but was null");
			return;
		}
		switch (result) {
			case 1:
				throw new IllegalArgumentException("Name can't be null");
			case 2:
				throw new UsernameNotFoundException(course.getCoordinator().getEmail());
		}
	}

	@Override
	public void delete(Course course) {
		jdbcTemplate.update("DELETE FROM courses WHERE id = ?", course.getId());
	}

	@Override
	public void update(Course course) {
		jdbcTemplate.update("UPDATE courses set name = ?, coordinator = ?, description = ? where id = ?;",
				course.getName().trim(), course.getCoordinator().getId(), course.getDescription().trim(), course.getId());
	}

	@Override
	public Course get(int id) {
		try {
			return jdbcTemplate.queryForObject(BASE_QUERY + " WHERE c.id = ?", new Object[]{id},
					(rs, rn) -> map(rs));
		}catch (EmptyResultDataAccessException e){
			return null;
		}
	}

	@Override
	public boolean exists(int id) {
		return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT id FROM courses where id = ?)", new Object[]{id}, Boolean.class);
	}

	@Override
	public List<Course> getAll() {
		return jdbcTemplate.query(BASE_QUERY, (rs, rn) -> map(rs));
	}

	@Override
	public List<Course> getAllCoordinatedBy(String email) {
		return jdbcTemplate.query(BASE_QUERY + " WHERE e_id = (SELECT id FROM users uu WHERE uu.email = ?)", new Object[]{email}, (rs, rn) -> map(rs));
	}

	@Override
	public List<Course> getAllWithNameLike(String like) {
		//todo
		return new ArrayList<>();
	}

	private Course map(ResultSet rs) throws SQLException {
		User u = new User();
		u.setEmail(rs.getString("e_email"));
		u.setId(rs.getInt("e_id"));
		u.setName(rs.getString("e_name"));
		u.setWww(rs.getString("e_www"));
		u.setScientificTitles(rs.getString("e_titles"));

		Course c = new Course();
		c.setCoordinator(u);
		c.setDescription(rs.getString("c_description"));
		c.setId(rs.getInt("c_id"));
		c.setName(rs.getString("c_name"));
		return c;
	}
}
