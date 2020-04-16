package pl.ziemniakoss.studentsresourcesmanager.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EmailUtilsTest {
	private final EmailUtils emailUtils = new EmailUtils();

	@ParameterizedTest
	@ValueSource(strings = {
			"trawa@gmail.com",
			"gwiazda@pw.edu.pl",
			"trawa.prawa@pw.edu.pl"
	})
	void correctEmail(String s) {
		assertTrue(emailUtils.isEmail(s));
	}

	@ParameterizedTest
	@ValueSource(strings = {
			"ala ma kota",
			"",
			"              ",
			"gwiazda@pl",


	})
	void incorrectEmail(String s) {
		assertFalse(emailUtils.isEmail(s));
	}

	@Test
	void checkNull(){
		assertFalse(emailUtils.isEmail(null));
	}
}