package pl.ziemniakoss.studentsresourcesmanager.utils;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import java.util.regex.Pattern;

@Component
public class EmailUtils {
	private final Pattern validEmail = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public boolean isEmail(String s) {
		if (s == null) {
			return false;
		}
		return validEmail.matcher(s).matches();
	}
}
