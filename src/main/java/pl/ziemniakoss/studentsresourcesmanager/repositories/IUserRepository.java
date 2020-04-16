package pl.ziemniakoss.studentsresourcesmanager.repositories;


import pl.ziemniakoss.studentsresourcesmanager.models.User;

import java.util.List;

/**
 * Repozytorium użytkowników tylko do odczytu. Pozwala na przeszukiwanie listy użytkowników
 * bez koniecznośći wyczytywania ich ról i haseł. Do modyfikacji faktycznych użytkowników
 * należy użyć {@link pl.ziemniakoss.studentsresourcesmanager.repositories.IUserDetailsRepository}
 */
public interface IUserRepository {

	User get(int id);

	User get(String email);


	List<User> getAll();

	/**
	 * Zwraca listę wszsytkich użytkowników systemu, który mają w imieniu
	 * dany ciąg znaków. Podczas przeszukiwania pomijane są rozmiary liter
	 *
	 * @param like ciąg znaków, który musi zawierać imię uzytkownika
	 * @return listę użytkowników spełniających wymagania. Jeżeli nie było żadnego
	 * zwrócona zostanie pusta lista
	 */
	List<User> getAllWithNameLike(String like);

	/**
	 * Zwraca listę wszsytkich użytkowników systemu, którzy mają w imieniu podany ciąg znaków(case insensitive).
	 * Dodatkowo jeżeli sutdent lub employee przyjmie wartość true to wszyscy zwróceni użytkownicy b
	 *
	 * @param like     ciąg znaków który musi zawierać imię. Jeżeli jest nullem lub pustym ciągiem to zostanie pominięte przy wyszukiwaniu
	 * @param student  czy użytkownik musi być studentem
	 * @param employee czy użytkownik musi być pracownikiem
	 * @param admin    czy użytkownik musi być adminem
	 * @return listę użytkowników spełniających te wymagania
	 */
	List<User> getAll(String like, boolean student, boolean employee, boolean admin);
}
