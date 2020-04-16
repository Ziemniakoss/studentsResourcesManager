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
			"trawa.prawa@pw.edu.pl",
//			"ulalal@50.128.210.78", NA razie tego nie wspieramy wgl
//			"matteo@78.47.122.114"
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
			"aaa aa@pw.ed.pl",
			"gaghg\tafsa@pw.edu.pl",
			"bar@",
			"adasfa@.pl",
			"pusty",
			"@gmail.pl",
			" @yahoo.com",
			"adsafa@yak@kek.pl",
			"sfafafafa ala@pw.edu.pl asaifhah",
			"sfafafafa ala@pw.edu.pl asaifhah",
			"ala@p w.edu.pl"
	})
	void incorrectEmail(String s) {
		assertFalse(emailUtils.isEmail(s));
	}

	@Test
	void checkNull(){
		assertFalse(emailUtils.isEmail(null));
	}
}