package msTask.constants;

import static msTask.constants.PathConstants.*;

public class CommonConstants {

	public static final int MAXIMUM_ACCOUNT_CONFIRM_TIME = 2; // in hours
	public static final String CONFIRM_TITLE_EMAIL = "Confirmation link";
	public static final String CONFIRM_MESSAGE = "To confirm your account, please click on the following link:%n"
			+ BASE_L + AUTH_L + CONFIRM_REGISTRATION_EMAIL_B_L + "/%s%n" + "The link is valid for "
			+ MAXIMUM_ACCOUNT_CONFIRM_TIME + " hours.";

	public static final int MAXIMUM_RESET_PASSWORD_TIME = 2; // in hours
	public static final String RESET_PASSWORD_TITLE_EMAIL = "Reset Password Link";
	public static final String RESET_PASSWORD_MESSAGE = "Click here " + BASE_L + AUTH_L + RESET_PASSWORD_EMAIL_L +"/%s"
			+ " to change your password :%nThe link is valid for "+ MAXIMUM_RESET_PASSWORD_TIME + " hours.";
	
	public static final String NEW_PASSWORD_TITLE_EMAIL = "New Password";
	public static final String NEW_PASSWORD_TEXT_EMAIL = "Your password was changed!";
	
	public static final String ACCOUNT_ACTIVATED_TITLE_EMAIL = "Activated Account";
	public static final String ACCOUNT_ACTIVATED_TEXT_EMAIL = "Account activated successfully. You can log in.";

	public static final String CHECK_YOUR_EMAIL = "Check your email.";

	public static final String COMMON_EXCEPTION_TEXT = "Error in back end!";

	public static final int TOKEN_VALIDITY_IN_MILLISECONDS = 3600000;
	public static final String AUTHORIZATION = "Authorization";
	public static final String JWT_TOKEN_PREFIX = "Bearer ";
	public static final String JWT_SECRET_KEY = "897s8976f87s68s6d57a85dfa85d4f8as76df5sa5df412312234234";
	public static final String FET_MSG = "Failed to extract email from token: %s";
	public static final String JWT_EXPIRED = "Token has expired.";
	public static final String JWT_INVALID_SIGNATURE = "Invalid token signature.";
	public static final String JWT_VALIDATION_FAILED = "Token validation failed: %s";
	
	
	
}