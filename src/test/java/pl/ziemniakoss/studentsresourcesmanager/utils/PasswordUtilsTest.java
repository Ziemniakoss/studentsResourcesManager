package pl.ziemniakoss.studentsresourcesmanager.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.annotation.Repeat;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilsTest {
	private PasswordUtils passwordUtils = new PasswordUtils();

	@ParameterizedTest
	@ValueSource(strings = {
			"",
			"ala",
			"amakota"
	})
	void isValidIncorrect() {
	}

	@ParameterizedTest
	@ValueSource(strings = {
			"ahsfhas;ohaffa",
			"sahfah;fhahf;ao;fho;af;h",
			"jao;hoas;fa;sfhpfashfhafhd"
	})
	void isValidCorrect(String password) {
		assertTrue(passwordUtils.isValid(password));
	}

	/**
	 * Sprawdza czy automatycznie generowane hasła są poprawne
	 * Powatrzamy pare razy żeby zwiększyć dokładność testu
	 */
	@Test
	@Repeat(100)
	void generateRandom() {
		assertTrue(passwordUtils.isValid(passwordUtils.generateRandom()));
	}

	@ParameterizedTest
	@ValueSource(ints = {1, 2, 4, 20, 100, 200, 2000})
	void testGenerateRandomTestLength(int length) {
		assertEquals(length, passwordUtils.generateRandom(length).length());
	}

	@ParameterizedTest
	@ValueSource(ints = {-1, 0, -100})
	void generateRandomIntIllegalValues(int length){
		assertThrows(IllegalArgumentException.class, () ->passwordUtils.generateRandom(length));

	}
}