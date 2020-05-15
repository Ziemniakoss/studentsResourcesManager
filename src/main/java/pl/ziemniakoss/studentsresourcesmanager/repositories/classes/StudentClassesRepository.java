package pl.ziemniakoss.studentsresourcesmanager.repositories.classes;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import pl.ziemniakoss.studentsresourcesmanager.models.Class;
import pl.ziemniakoss.studentsresourcesmanager.models.Course;
import pl.ziemniakoss.studentsresourcesmanager.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StudentClassesRepository implements IStudentClassesRepository {
	private final JdbcTemplate jdbcTemplate;
	private final IClassesRepository classesRepository;
	private final String BASE_QUERY =
			"SELECT sc.student_id AS s_id, class_id AS cl_id, c.course AS cr_id " +
					" FROM students_classes sc " +
					" JOIN classes c ON sc.class_id = c.id ";

	public StudentClassesRepository(JdbcTemplate jdbcTemplate, IClassesRepository classesRepository) {
		this.jdbcTemplate = jdbcTemplate;
		this.classesRepository = classesRepository;
	}

	@Override
	public List<Class> getAll(User student) {
		Assert.notNull(student, "Student jest wymagany");
		return getAll(student.getId());
	}

	@Override
	public List<Class> getAll(int studentId) {
		return jdbcTemplate.query(BASE_QUERY + " WHERE sc.student_id = ?;",
				(rs, rn) -> map(rs), studentId);
	}

	@Override
	public List<Class> getAllInCourse(User student, Course course) {
		Assert.notNull(student, "Stduent nie może być nullem");
		Assert.notNull(course, "Kurs nie może być nullem");
		return getAllInCourse(student.getId(), course.getId());
	}

	@Override
	public List<Class> getAllInCourse(int studentId, int courseId) {
		return jdbcTemplate.query(BASE_QUERY + " WHERE student_id = ? AND c.course = ?;",
				new Object[]{studentId, courseId}, (rs, rn) -> map(rs));
	}

	private Class map(ResultSet rs) throws SQLException {
		return classesRepository.get(rs.getInt("cl_id"));
	}

	@Override
	public void signUp(User student, Class c) {
		Assert.notNull(student, "Student nie może być nullem");
		Assert.notNull(c, "Klasa nie może być nullem");
		signUp(student.getId(), c.getId());
	}

	@Override
	public void signUp(int studentId, int classId) {
		//todo
	}

	@Override
	public void unsubscribe(User student, Class c) {
		Assert.notNull(student, "Student nie może być nullem");
		Assert.notNull(c, "Klasa nie może być nullem");
		unsubscribe(student.getId(), c.getId());
	}

	@Override
	public void unsubscribe(int studentId, int classId) {
//todo
	}
}
