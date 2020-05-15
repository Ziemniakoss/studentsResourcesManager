package pl.ziemniakoss.studentsresourcesmanager.repositories.courses;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import pl.ziemniakoss.studentsresourcesmanager.models.Course;
import pl.ziemniakoss.studentsresourcesmanager.models.User;

import java.util.List;

@Repository
public class StudentCoursesRepository implements IStudentCoursesRepository {
	private final JdbcTemplate jdbcTemplate;
	private final ICourseRepository courseRepository;

	public StudentCoursesRepository(JdbcTemplate jdbcTemplate, ICourseRepository courseRepository) {
		this.jdbcTemplate = jdbcTemplate;
		this.courseRepository = courseRepository;
	}

	@Override
	public List<Course> getAll(User student) {
		Assert.notNull(student, "Student nie może być nullem");
		return getAll(student.getId());
	}

	@Override
	public List<Course> getAll(int studentId) {
		return jdbcTemplate.query(
				"SELECT DISTINCT course " +
						" FROM students_classes " +
						" JOIN classes c ON students_classes.class_id = c.id WHERE student_id = ?;",
				(rs, rn) -> courseRepository.get(rs.getInt("course")), studentId);
	}

	@Override
	public boolean hasAccess(User student, Course course) {
		Assert.notNull(student, "Student nie może być nullem");
		Assert.notNull(course, "Kurs nie może być nullem");
		return hasAccess(student.getId(), course.getId());
	}

	@Override
	public boolean hasAccess(int studentId, int courseId) {
		return jdbcTemplate.queryForObject(
				"SELECT EXISTS(SELECT 1" +
						" FROM students_classes sc " +
						" JOIN classes c ON sc.class_id = c.id WHERE student_id = ? AND course = ?)",
				Boolean.class, studentId, courseId);
	}

}
