package openchat.domain.users;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

	private Map<String, User> users = new HashMap<String, User>();
	public void add(User user) throws UsernamerAlreadyInUseException {
		users.put(user.username(), user);
	}

	public boolean itAlreadyExists(String username){
		return users.containsKey(username);
	}

	public User userFor(LoginData loginData) throws InvalidUserLoginCredentialsException {
		if(itAlreadyExists(loginData.username())) {
			User user = users.get(loginData.username());
			if(user.password().contentEquals(loginData.password()))
				return user;
		}
		throw new InvalidUserLoginCredentialsException();
	}

}
