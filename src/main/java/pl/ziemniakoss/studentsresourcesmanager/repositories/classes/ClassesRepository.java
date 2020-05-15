package pl.ziemniakoss.studentsresourcesmanager.repositories.classes;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import pl.ziemniakoss.studentsresourcesmanager.models.Class;
import pl.ziemniakoss.studentsresourcesmanager.models.Course;
import pl.ziemniakoss.studentsresourcesmanager.models.User;
import pl.ziemniakoss.studentsresourcesmanager.repositories.courses.ICourseRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClassesRepository implements IClassesRepository {
	private final String BASE_QUERY =
			" SELECT cl.id as cl_id,cl.semester as cl_semester, cl.course as cl_course, " +
					" e.id as cord_id, u.name as cord_name, u.email as cord_email,e.www as cord_www, e.scientific_titles as cord_titles " +
					" FROM classes cl " +
					" JOIN employees e ON cl.coordinator = e.id " +
					" JOIN users u ON e.id = u.id";
	private final JdbcTemplate jdbcTemplate;
	private final ICourseRepository courseRepository;

	public ClassesRepository(JdbcTemplate jdbcTemplate, ICourseRepository courseRepository) {
		this.jdbcTemplate = jdbcTemplate;
		this.courseRepository = courseRepository;
	}

	@Override
	public void add(Class c) {
		Assert.notNull(c, "Klasa nie może być nullem");
		//todo
		jdbcTemplate.update("INSERT INTO classes (course, coordinator, semester) VALUES (?,?,?)", c.getCourse().getId(), c.getCoordinator().getId(), c.getSemester());
	}

	@Override
	public void update(Class c) {
		//todo
	}

	@Override
	public void delete(Class c) {
		//todo
	}

	@Override
	public Class get(int index) {
		try {
			return jdbcTemplate.queryForObject(BASE_QUERY + " WHERE cl.id = ? ",
					new Object[]{index},
					(resultSet, i) -> {
						Course course = courseRepository.get(resultSet.getInt("cl_course"));
						Class c = map(resultSet);
						c.setCourse(course);
						return c;
					});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Class> getAll(Course course) {
		if (course == null) {
			return new ArrayList<>();
		}
		Course c = courseRepository.get(course.getId());
		List<Class> classes = jdbcTemplate.query(BASE_QUERY + " WHERE cl.course = ?",
				new Object[]{course.getId()}, (rs, rn) -> map(rs));
		classes.forEach(e -> e.setCourse(c));
		return classes;
	}

	@Override
	public List<Class> getAllCoordinatedBy(int coordinatorId) {
		return jdbcTemplate.query(BASE_QUERY + " WHERE coordinator = ?;", new Object[]{coordinatorId}, (rs, rn) -> map(rs));
	}

	@Override
	public List<Class> getAllCoordinatedBy(User coordinator) {
		return coordinator == null ? new ArrayList<>(1) : getAllCoordinatedBy(coordinator.getId());
	}

	@Override
	public List<Class> getAllAttendedBy(int userId) {
		return null;//todo
	}

	@Override
	public List<Class> getAllAttendedBy(User student) {
		return null;//todo
	}

	private Class map(ResultSet rs) throws SQLException {
		Class c = new Class();
		c.setSemester(rs.getString("cl_semester"));
		c.setId(rs.getInt("cl_id"));

		User coordinator = new User();
		coordinator.setEmployee(true);
		coordinator.setId(rs.getInt("cord_id"));
		coordinator.setName(rs.getString("cord_name"));
		coordinator.setEmail(rs.getString("cord_email"));
		coordinator.setWww(rs.getString("cord_www"));
		coordinator.setScientificTitles(rs.getString("cord_titles"));
		c.setCoordinator(coordinator);
		return c;
	}
}
