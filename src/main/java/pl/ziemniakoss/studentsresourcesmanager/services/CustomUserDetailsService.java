package pl.ziemniakoss.studentsresourcesmanager.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.ziemniakoss.studentsresourcesmanager.UserDetails;
import pl.ziemniakoss.studentsresourcesmanager.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		UserDetails u = userRepository.getUserByEmail(s);
		if(u == null){
			throw new UsernameNotFoundException(s);
		}
		return u;
	}
}
