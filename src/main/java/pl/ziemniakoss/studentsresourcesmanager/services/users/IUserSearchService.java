package pl.ziemniakoss.studentsresourcesmanager.services.users;


import pl.ziemniakoss.studentsresourcesmanager.models.User;
import pl.ziemniakoss.studentsresourcesmanager.models.UserSearch;

import java.util.List;

/**
 * Pozwala na przeszukiwanie bazy użytkowników.
 */
public interface IUserSearchService {
	/**
	 * Zwraca listę wszytkich użytkowników systemu
	 *
	 * @return lista wszystkich użytkowników systemu
	 */
	List<User> getAll();

	/**
	 * Zwraca listę użytkowników spełniających podane wymagania
	 *
	 * @param querry Wymagania co do zwracanych użytkownikow
	 * @return listę wszystkich użytkowników spełniających podane wymagania
	 */
	List<User> getAllMatching(UserSearch querry);
}
