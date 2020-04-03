package pl.ziemniakoss.studentsresourcesmanager.repositories;


import pl.ziemniakoss.studentsresourcesmanager.models.User;

public interface IUserRepository {
	User getUserByEmail(String email);
	User getUserById(int id);
	void changePassword(User user, String passwordPlain);

}
