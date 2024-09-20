package msTask.config;

public class PathConstants {
	
	public final static String CROSS_ORGIN_PATTERN_L = "*";

	public final static String BASE_L = "http://localhost:4200";
	public final static String BASE_API_L = "http://localhost:8080";
	public final static String AUTH_L = "/auth";
	public final static String USERS_L = "/users";
	
	private final static String REGISTER_L_B = "/register";
	private final static String CONFIRM_L_B = "/confirm";
	private final static String REGISTRATION_L_B = "/registration";
	private final static String LOGIN_L_B = "/login";
	private final static String RESET_L_B = "/reset";
	private final static String PASSWORD_L_B = "/password";
	private final static String NEW_L_B = "/new";
	private final static String CREATE_L_B = "/create";
	private final static String ALL_L_B = "/all";
	private final static String PAGES_L_B = "/pages";
	private final static String PAGES_NA_L_B = "/pages-nq";
	private final static String UPDATE_L_B = "/update";
	private final static String DELETE_L_B = "/delete";
	
	private final static String ACTIVATION_PATH_VARIABLE = "/{aLink}";
	private final static String UNIQUE_STRING_FOR_PASSWORD_RESET_PATH_VARIABLE = "/{uniqueStringForPasswordReset}";
	private final static String USER_ID_PATH_VARIABLE = "/{userId}";
	
	//AUTH CONTROLLER LINKS
	public final static String LOGIN_L = LOGIN_L_B;
	public final static String REGISTER_L = REGISTER_L_B;
	public final static String CONFIRM_REGISTRATION_L = CONFIRM_L_B + REGISTRATION_L_B + ACTIVATION_PATH_VARIABLE;
	public final static String RESET_PASSWORD_L = RESET_L_B + PASSWORD_L_B;
	public final static String CREATE_NEW_PASSWORD_L = CREATE_L_B + NEW_L_B + PASSWORD_L_B + UNIQUE_STRING_FOR_PASSWORD_RESET_PATH_VARIABLE;
	
	//AUTH SERVICE
	public final static String CONFIRM_REGISTRATION_EMAIL_B_L = CONFIRM_L_B + REGISTRATION_L_B;
	public final static String RESET_PASSWORD_EMAIL_L = CREATE_L_B + NEW_L_B + PASSWORD_L_B;
	
	//USER CONTROLLER LINKS
	public final static String GET_ALL_USERS_BY_PAGES_L = ALL_L_B + PAGES_L_B;
	public final static String GET_ALL_USERS_BY_PAGES_NA_L = ALL_L_B + PAGES_NA_L_B;
	public final static String GET_ALL_USERS_L = ALL_L_B;
	public final static String GET_ALL_USER_BY_ID_L = USER_ID_PATH_VARIABLE;
	public final static String UPDATE_USER_L = UPDATE_L_B;
	public final static String DELETE_USER_BY_ID_L = DELETE_L_B + USER_ID_PATH_VARIABLE;
	
}
