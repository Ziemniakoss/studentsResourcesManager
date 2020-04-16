package pl.ziemniakoss.studentsresourcesmanager.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.ziemniakoss.studentsresourcesmanager.CustomUserDetails;
import pl.ziemniakoss.studentsresourcesmanager.repositories.IUserDetailsRepository;
import pl.ziemniakoss.studentsresourcesmanager.utils.EmailUtils;
import pl.ziemniakoss.studentsresourcesmanager.utils.PasswordUtils;

@Service
public class CustomUserDetailsManager implements UserDetailsManager {
	private final Logger log = LoggerFactory.getLogger(CustomUserDetailsManager.class);

	private final IUserDetailsRepository userDetailsRepository;
	private final EmailUtils emailUtils;
	private final PasswordUtils passwordUtils;
	private final PasswordEncoder passwordEncoder;

	public CustomUserDetailsManager(IUserDetailsRepository userDetailsRepository, EmailUtils emailUtils, PasswordUtils passwordUtils, PasswordEncoder passwordEncoder) {
		this.userDetailsRepository = userDetailsRepository;
		this.emailUtils = emailUtils;
		this.passwordUtils = passwordUtils;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		CustomUserDetails u = userDetailsRepository.getByEmail(s);
		if (u == null) {
			throw new UsernameNotFoundException(s);
		}
		return u;
	}

	/**
	 * Tworzy nowego użytkownika w bazie danych
	 *
	 * @param user użytkownik do utworzenia. <b>Pole password musi zawierać hasło w plain text</b> w celu weryfikacji
	 * @throws IllegalArgumentException jeżeli użytkownik istnieje, hasło nie spełnia wymagań lub email nie jest poprawny
	 *
	 */
	@Override
	public void createUser(UserDetails user) {
		if (user == null) {
			throw new NullPointerException("User can't be null");
		}
		Assert.isTrue(user instanceof CustomUserDetails, "UserDetails must be CustomUserDetails");
		CustomUserDetails details = (CustomUserDetails) user;
		Assert.isTrue(emailUtils.isEmail(details.getEmail()), "Illegal email");
		Assert.isTrue(!userExists(details.getUsername()), "User already exists");
		Assert.isTrue(passwordUtils.isValid(details.getPassword()), "Illegal password");

		String passwordHash = passwordEncoder.encode(details.getPassword());
		details.setPassword(passwordHash);
		userDetailsRepository.add(details);
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
