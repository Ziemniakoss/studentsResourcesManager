package pl.ziemniakoss.studentsresourcesmanager.repositories;

import pl.ziemniakoss.studentsresourcesmanager.CustomUserDetails;

/**
 * Repozytorium danych użytkownika. Pozwala na
 * zarządzanie faktycznymi użytkownikami systemu
 */
public interface IUserDetailsRepository {
	/**
	 * Zwraca dane użytkownika z podanym emailem. Podczas przesuzukiwania brane
	 * są pod uwagi małe i wielkie litery
	 *
	 * @param email email użytkownika
	 * @return zwraca dane użytkownika o podanym emailu  lub nulla gdy takiego nie ma
	 */
	CustomUserDetails getByEmail(String email);

	CustomUserDetails getById(int id);

	/**
	 * Uaktualnia dane użytkownika. Podczas uatkualniania nie zmienia id oraz adresu email.
	 * Uaktualnia także role użytkownika
	 *
	 * @param updatedUserDetails uatkualnione dane użytkownika
	 */
	void update(CustomUserDetails updatedUserDetails);

	/**
	 * Sprawdza czy użytkownik o emailu istnieje.
	 *
	 * @param email adres email użytkownika. Nie jest sprawdzane, czy to pole jest poprawnym
	 *              adresem email
	 * @return false jeżeli nie istniej lub email był nullem, true jeżeli istnieje
	 */
	boolean exists(String email);

	/**
	 * Tworzy nowego użytkownika. Metoda nie sprawdza czy użytkownik o takim emailu istnieje
	 * ani nie sprawdza poprawności danych. Jeżeli będą niepoprawne może nastąpić błąd check na tablei
	 * <i>users</i>
	 * @param userDetails dane nowego użytkownik. <b>Pole password musi zawierać zahashowane hasło</b>
	 */
	void add(CustomUserDetails userDetails);
}
