package pl.ziemniakoss.studentsresourcesmanager.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Random;

@Component
public class PasswordUtils {
	Random random = new Random();

	/**
	 * Sprawdza czy hasło spełnia wymagania. Poprawne hasło ma 8 znaków
	 *
	 * @param password hasło do weryfikacji
	 * @return true jeżeli jest poprawne
	 */
	public boolean isValid(String password) {
		return password != null && password.length() >= 8;//todo silniejsze warunki
	}

	/**
	 * Generuje hasło spełniające politykę haseł
	 *
	 * @return hasło zgodne z polityką haseł
	 */
	public String generateRandom() {
		return generateRandom(8);
	}

	public String generateRandom(int length) {
		Assert.isTrue(length >= 1, "Length must be at least 1");
		int startChar = 'a';
		int range = 'z' - 'a';
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append((char) (random.nextInt(range) + startChar));
		}
		return sb.toString();
	}
}
