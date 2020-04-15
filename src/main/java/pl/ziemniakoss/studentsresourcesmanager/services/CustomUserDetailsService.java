package pl.ziemniakoss.studentsresourcesmanager.services;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import pl.ziemniakoss.studentsresourcesmanager.CustomUserDetails;
import pl.ziemniakoss.studentsresourcesmanager.repositories.IUserDetailsRepository;

@Service
public class CustomUserDetailsService implements UserDetailsManager {

	private final IUserDetailsRepository userDetailsRepository;

	public CustomUserDetailsService(IUserDetailsRepository userDetailsRepository) {
		this.userDetailsRepository = userDetailsRepository;
	}

	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		CustomUserDetails u = userDetailsRepository.getByEmail(s);
		if (u == null) {
			throw new UsernameNotFoundException(s);
		}
		return u;
	}

	@Override
	public void createUser(UserDetails user) {
		//todo
	}

	@Override
	public void updateUser(UserDetails user) {
		//todo
	}

	@Override
	public void deleteUser(String username) throws UsernameNotFoundException {
		//todo
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		//todo
	}

	@Override
	public boolean userExists(String username) {
		return userDetailsRepository.exists(username);
	}
}
