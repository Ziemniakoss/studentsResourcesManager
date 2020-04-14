package pl.ziemniakoss.studentsresourcesmanager.repositories;


import pl.ziemniakoss.studentsresourcesmanager.UserDetails;

public interface IUserRepository {
	UserDetails getUserByEmail(String email);
	UserDetails getUserById(int id);
	void changePassword(UserDetails user, String passwordPlain);

}
