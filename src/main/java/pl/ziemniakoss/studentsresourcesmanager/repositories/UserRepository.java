package pl.ziemniakoss.studentsresourcesmanager.repositories;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import pl.ziemniakoss.studentsresourcesmanager.models.User;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

@Repository
public class UserRepository implements IUserRepository {
	private final JdbcTemplate jdbcTemplate;

	public UserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public User getUserByEmail(String email) {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		System.out.println(email);
		Future<User> getUser = (Future<User>) threadPool.submit(() -> {
			User user = jdbcTemplate.query("SELECT * FROM users WHERE email = ?", new Object[]{email}, new ResultSetExtractor<User>() {
						@Override
						public User extractData(ResultSet rs) throws SQLException, DataAccessException {
							if(rs.next()) {
								User u = new User();
								u.setEmail(rs.getString("email"));
								u.setPassword(rs.getString("password_hash"));
								u.setName(rs.getString("name"));
								u.setId(rs.getInt("id"));
								return u;
							}
							return null;
						}
					}
			);
			return user;
		});
		Future<Collection<GrantedAuthority>> getAuthorities;
		getAuthorities = (Future<Collection<GrantedAuthority>>) threadPool.submit(()-> getRoles(email));
		try {
			User u = getUser.get();
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
	public User getUserById(int id) {//todo
		return null;
	}

	@Override
	public void changePassword(User user, String passwordPlain) {//todo

	}

	private Collection<GrantedAuthority> getRoles(String email) {
		Array array = jdbcTemplate.queryForObject("SELECT get_roles(?) as roles", new Object[]{email}, Array.class);
		List<GrantedAuthority> result = new ArrayList<>(3);
		try {
			String [] aa = (String [] ) array.getArray();
			for(String s: aa){
				result.add(new SimpleGrantedAuthority(s));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
