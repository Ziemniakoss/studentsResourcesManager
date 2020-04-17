package pl.ziemniakoss.studentsresourcesmanager.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailUtils {
	private final Pattern validEmail = Pattern.compile("\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b", Pattern.CASE_INSENSITIVE);;

	public boolean isEmail(String s) {
		if (s == null) {
			return false;
		}
		return validEmail.matcher(s).matches();
	}
}
