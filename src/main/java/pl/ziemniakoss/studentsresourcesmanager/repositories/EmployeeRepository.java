package pl.ziemniakoss.studentsresourcesmanager.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.ziemniakoss.studentsresourcesmanager.models.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Repository
public class EmployeeRepository implements IEmployeeRepository{
	private final JdbcTemplate jdbcTemplate;

	public EmployeeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Collection<Employee> getAllWithNameLike(String nameLike) {
		return jdbcTemplate.query("SELECT * FROM get_all_employees_name_like(?);",
				new Object[]{nameLike}, (rs,rn)->extractEmployee(rs));
	}

	@Override
	public Employee getEmployeeByEmail(String email) {
		return null;//todo
	}

	@Override
	public Employee getEmployeeById(int id) {
		return null;//todo
	}

	@Override
	public void updateEmployee(Employee employee) {
		//todo
	}

	@Override
	public Collection<Employee> getAll() {
		return jdbcTemplate.query("SELECT  * FROM get_all_employees()", (rs,rn)->extractEmployee(rs));
	}

	private Employee extractEmployee(ResultSet rs) throws SQLException {
		Employee e = new Employee();
		e.setScientificTitles(rs.getString("scientific_titles"));
		e.setId(rs.getInt("id"));
		e.setEmail(rs.getString("email"));
		e.setWww(rs.getString("www"));
		e.setName(rs.getString("name"));
		return e;
	}
}
