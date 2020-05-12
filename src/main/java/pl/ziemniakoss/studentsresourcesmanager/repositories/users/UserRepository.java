package pl.ziemniakoss.studentsresourcesmanager.repositories.users;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.ziemniakoss.studentsresourcesmanager.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository implements IUserRepository {
	private final JdbcTemplate jdbcTemplate;
	private final String BASE_QUERY =
			"SELECT u.id AS id, u.name AS name, u.email AS email, (e.id IS NOT NULL) AS employee, (s.id IS NOT NULL) AS student, e.www as www, e.scientific_titles as titles, (a.id IS NOT NULL) as admin " +
					" FROM users u " +
					" FULL OUTER JOIN employees e ON u.id = e.id " +
					" FULL OUTER JOIN admins a ON u.id = a.id " +
					" FULL OUTER JOIN students s ON u.id = s.id ";

	public UserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	@Override
	public User get(int id) {
		try {
			return jdbcTemplate.queryForObject(BASE_QUERY + " WHERE u.id = ?",
					new Object[]{id}, (rs, i) -> extractUser(rs));
		}catch (EmptyResultDataAccessException e){
			return null;
		}
	}

	@Override
	public User get(String email) {
		try {
			return jdbcTemplate.queryForObject(BASE_QUERY + " WHERE u.email = ?",
					new Object[]{email}, (rs, rn) -> extractUser(rs));
		}catch (EmptyResultDataAccessException e){
			return null;
		}
	}

	@Override
	public List<User> getAll() {
		return getAll(null, false, false, false);
	}

	@Override
	public List<User> getAllWithNameLike(String like) {
		return getAll(null, false, false, false);
	}

	@Override
	public List<User> getAll(String like, boolean student, boolean employee, boolean admin) {
		StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
		sqlBuilder.append(" WHERE lower(name) LIKE ?");
		if (student) {
			sqlBuilder.append(" AND s.id IS NOT NULL ");
		}
		if (employee) {
			sqlBuilder.append(" AND e.id IS NOT NULL");
		}
		if (admin) {
			sqlBuilder.append(" AND a.id IS NOT NULL");
		}
		if (like == null || like.length() == 0) {
			like = "%";
		} else {
			like = '%' + like.trim() + '%';
		}
		return jdbcTemplate.query(sqlBuilder.toString(), new Object[]{like}, (rs, rn) -> extractUser(rs));
	}

	private User extractUser(ResultSet rs) throws SQLException {
		User u = new User();
		u.setId(rs.getInt("id"));
		u.setName(rs.getString("name"));
		u.setEmail(rs.getString("email"));
		u.setAdmin(rs.getBoolean("admin"));
		u.setStudent(rs.getBoolean("student"));
		u.setEmployee(rs.getBoolean("employee"));
		if (u.isEmployee()) {
			u.setScientificTitles(rs.getString("titles"));
			u.setWww(rs.getString("www"));
		}
		return u;
	}
}
