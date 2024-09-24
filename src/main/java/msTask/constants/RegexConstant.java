package msTask.constants;

public final class RegexConstant {

	public final static String EMAIL_REGEX_MSG_ERR = "An error occurred. Please enter a valid email address.";
	
	public final static String USERNAME_REGEX = "^[a-zA-Z0-9._-]{3,16}$";
	public final static String USERNAME_REGEX_MSG_ERR = "An error occurred. Please enter a valid username."
					+ "The input must contain between 3 and 16 characters,"
					+ "consisting of only letters (uppercase and lowercase),"
					+ "numbers, periods (.), underscores (_), or hyphens (-)." 
					+ "No other special characters are allowed."
					+ "The length must be strictly"
					+ "within the specified range for the input to be valid";
	
	public final static String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+{}\\[\\]:;\"'<>,.?\\/~`|\\\\-]).{8,}$";
	public final static String PASSWORD_REGEX_MSG_ERR = "An error occurred. Please enter a valid password."
					+ "The input must contain at least one lowercase letter, one uppercase letter,"
					+ "one digit, one special character (@, $, !, %, *, ?, &),"
					+ "and be at least 8 characters long to be valid according to the regex.";
	
	public final static String FIRST_NAME_REGEX = "^[A-ZА-Я][a-zа-я'-]{1,49}$";
	public final static String FIRST_NAME_REGEX_MSG_ERR = "An error occurred. Please enter a valid first name."
				+ "The input must start with an uppercase Latin or Cyrillic letter, "
				+ "followed by 1 to 49 lowercase Latin or Cyrillic letters, apostrophes, or hyphens.";
	
	public final static String LAST_NAME_REGEX = "^[A-ZА-Я][a-zа-я'-]{1,49}$";
	public final static String LAST_NAME_REGEX_MSG_ERR = "An error occurred. Please enter a valid last name."
				+ "The input must start with an uppercase Latin or Cyrillic letter, "
				+ "followed by 1 to 49 lowercase Latin or Cyrillic letters, apostrophes, or hyphens.";
	
	public final static String BIRTHDAY_REGEX_MSG_ERR = "An error occurred. Please enter a valid birthday."
				+ "Birthday must be a date in the past";
	
	public final static String PHONE_NUMBER_REGEX = "^\\+[0-9 -\\.]{7,21}$";
	public final static String PHONE_NUMBER_REGEX_MSG_ERR = "An error occurred. Please enter a valid phone number."
				+ "It can includes spaces, dashes, dots. Max lenght 20 symbols.";
	
	public final static String AUTHORITY_NAME_REGEX = "^[a-zA-Z\\-]+$";
	public final static String AUTHORITY_NAME_REGEX_MSG_ERR = "An error occurred. Please enter a valid authority name."
				+ "The input must only contain uppercase and lowercase letters and hyphens, "
				+ "with no spaces or other special characters, to be valid according to the regex.";
	
	public final static String ROLE_NAME_REGEX = AUTHORITY_NAME_REGEX;
	public final static String ROLE_NAME_REGEX_MSG_ERR = "An error occurred. Please enter a valid role name."
				+ "The input must only contain uppercase and lowercase letters and hyphens, "
				+ "with no spaces or other special characters, to be valid according to the regex.";
	
	public final static String ID_REGEX = "^[a-zA-Z0-9\\-]+$";
	public final static String ID_REGEX_MSG_ERR = "An error occurred. Please enter a valid id."
				+ "The input must only contain uppercase and lowercase letters and hyphens, "
				+ "with no spaces or other special characters, to be valid according to the regex.";
	
}

