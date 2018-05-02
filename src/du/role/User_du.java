package du.role;

/**
 * 
 * @author Shibowen
 *
 */

public abstract class User_du {
	private static String user_name;
	private static String password;

	public User_du(String user_name, String password) {
		super();
		User_du.user_name = user_name;
		User_du.password = password;
	}
	
	public static String getUser_name() {
		return user_name;
	}

	public static void setUser_name(String user_name) {
		User_du.user_name = user_name;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		User_du.password = password;
	}
}
