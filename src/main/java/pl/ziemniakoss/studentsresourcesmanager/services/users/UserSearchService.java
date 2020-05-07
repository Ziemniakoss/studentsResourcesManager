package pl.ziemniakoss.studentsresourcesmanager.services.users;

import org.springframework.stereotype.Service;
import pl.ziemniakoss.studentsresourcesmanager.models.User;
import pl.ziemniakoss.studentsresourcesmanager.models.UserSearch;
import pl.ziemniakoss.studentsresourcesmanager.repositories.IUserRepository;
import pl.ziemniakoss.studentsresourcesmanager.services.users.IUserSearchService;

import java.util.List;

@Service
public class UserSearchService implements IUserSearchService {
	private final IUserRepository userRepository;

	public UserSearchService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<User> getAll() {
		return userRepository.getAll();
	}

	@Override
	public List<User> getAllMatching(UserSearch querry) {
		if (querry == null) {
			return getAll();
		}
		return userRepository.getAll(querry.getLike(), querry.isStudent(), querry.isEmployee(), querry.isAdmin());
	}
}
