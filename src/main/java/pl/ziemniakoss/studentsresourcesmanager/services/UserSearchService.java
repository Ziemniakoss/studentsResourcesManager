package pl.ziemniakoss.studentsresourcesmanager.services;

import org.springframework.stereotype.Service;
import pl.ziemniakoss.studentsresourcesmanager.models.User;
import pl.ziemniakoss.studentsresourcesmanager.models.UserSearch;

import java.util.List;

@Service
public class UserSearchService implements IUserSearchService {
	@Override
	public List<User> getAll() {
		return null;
	}

	@Override
	public List<User> getAllMatching(UserSearch querry) {
		return null;
	}
}
