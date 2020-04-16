package pl.ziemniakoss.studentsresourcesmanager.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Repository;
import pl.ziemniakoss.studentsresourcesmanager.CustomUserDetails;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;


/**
 * Repozytorium danych użytkownika łączące się z bazą danych Postgresql
 */
//todo LOG
@Repository
public class UserDetailsRepository implements IUserDetailsRepository {
	private final Logger log = LoggerFactory.getLogger(UserDetailsManager.class);
	private final JdbcTemplate jdbcTemplate;

	public UserDetailsRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public CustomUserDetails getByEmail(String email) {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		System.out.println(email);
		Future<CustomUserDetails> getUser = (Future<CustomUserDetails>) threadPool.submit(() -> {
			CustomUserDetails user = jdbcTemplate.query("SELECT * FROM users WHERE email = ?", new Object[]{email}, rs -> {
						if (rs.next()) {
							CustomUserDetails u = new CustomUserDetails();
							u.setEmail(rs.getString("email"));
							u.setPassword(rs.getString("password_hash"));
							u.setName(rs.getString("name"));
							u.setId(rs.getInt("id"));
							return u;
						}
						return null;
					}
			);
			return user;
		});
		Future<Collection<GrantedAuthority>> getAuthorities;
		getAuthorities = threadPool.submit(() -> getRoles(email));
		try {
			CustomUserDetails u = getUser.get();
			u.setAuthorities(getAuthorities.get());
			return getUser.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public CustomUserDetails getById(int id) {
		//todo
		return null;
	}

	@Override
	public void update(CustomUserDetails updatedUserDetails) {
		if (!exists(updatedUserDetails.getUsername())) {
			throw new UsernameNotFoundException(updatedUserDetails.getUsername());
		}
		jdbcTemplate.update("UPDATE users set password_hash = ?, name = ? WHERE id = ?", updatedUserDetails.getPassword(), updatedUserDetails.getName(), updatedUserDetails.getId());
		List<String> roles = updatedUserDetails.getAuthorities().parallelStream()
				.filter(a -> a.getAuthority().startsWith("ROLE_"))
				.map(a -> a.getAuthority().substring(6))
				.collect(Collectors.toList());
		boolean student = false, employee = false, admin = false;
		for (String role : roles) {
			switch (role) {
				case "STUDENT":
					student = true;
					break;
				case "ADMIN":
					admin = true;
					break;
				case "EMPLOYEE":
					employee = true;
					break;
				default:
					log.error("Unknown role detected while trying to update user '" + updatedUserDetails.getUsername() + "': ROLE_" + role);
			}
		}
		//todo może równolegle?
		updateAdminStatus(updatedUserDetails.getEmail(), admin);
		updateStudentStatus(updatedUserDetails.getEmail(), student);
		updateEmployeeStatus(updatedUserDetails.getEmail(), employee);
	}

	@Override
	public boolean exists(String email) {
		if (email == null) {
			return false;
		}
		Boolean res = jdbcTemplate.queryForObject("SELECT EXISTS(SELECT id FROM users WHERE email = ?);",
				new Object[]{email}, Boolean.class);
		return res != null ? res : false;
	}

	/**
	 * Pobiera role użytkownika z bazy danych
	 *
	 * @param email email użytkownika
	 * @return listę ról użytkownika. Gdy nie ma takiego użytkownika zwraca pustą listę
	 */
	private Collection<GrantedAuthority> getRoles(String email) {
		Array array = jdbcTemplate.queryForObject("SELECT get_roles(?) as roles", new Object[]{email}, Array.class);
		List<GrantedAuthority> result = new ArrayList<>(3);
		try {
			String[] aa = (String[]) array.getArray();
			for (String s : aa) {
				result.add(new SimpleGrantedAuthority(s));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	private void updateStudentStatus(String email, boolean student) {
		Integer res = jdbcTemplate.queryForObject("SELECT * FROM is_student(?);", new Object[]{email}, Integer.class);
		if (res == 1 && !student) {
			jdbcTemplate.update("DELETE FROM students WHERE id = (SELECT id FROM users WHERE email = ?);", email);
			log.info("'" + email + "' is no longer a student");
		} else if (res == 0 && student) {
			jdbcTemplate.update("INSERT INTO students (id) VALUES (?);", email);
			log.info('\'' + email + "' is now a student");
		}
	}

	private void updateAdminStatus(String email, boolean admin) {
		//todo

	}

	private void updateEmployeeStatus(String email, boolean employee) {
		Integer res = jdbcTemplate.queryForObject("SELECT * FROM is_employee(?);", new Object[]{email}, Integer.class);
		if (res == 1 && !employee) {
			jdbcTemplate.update("DELETE FROM employees WHERE id = (SELECT id FROM users WHERE email = ?);", email);
			log.info("'" + email + "' is no longer an employee");
		} else if (res == 0 && employee) {
			jdbcTemplate.update("INSERT INTO employees (id) VALUES ((SELECT id FROM users WHERE email = ?))", email);
			log.info("'" + email + "' is now employee");
		}
	}

}
