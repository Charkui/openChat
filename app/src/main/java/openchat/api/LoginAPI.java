package openchat.api;

import com.eclipsesource.json.JsonObject;

import io.javalin.http.ContentType;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import openchat.domain.users.InvalidUserLoginCredentialsException;
import openchat.domain.users.LoginData;
import openchat.domain.users.User;
import openchat.domain.users.UserRepository;
import openchat.infrastructure.json.UserJson;

public class LoginAPI {

	private UserRepository userRepository;

	public LoginAPI(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public String login(Context ctx) throws InvalidUserLoginCredentialsException {
		try {
			LoginData loginData = loginDataFrom(ctx);
			User user = userRepository.userFor(loginData);
			ctx.status(HttpCode.OK.getStatus());
			ctx.contentType(ContentType.APPLICATION_JSON.getMimeType());
			return UserJson.jsonFor(user);
		}catch(InvalidUserLoginCredentialsException e) {
			ctx.status(HttpCode.BAD_REQUEST.getStatus());
			return "Username/password are incorrect";
		}
	}

	private LoginData loginDataFrom(Context ctx) {
		JsonObject json = JsonObject.readFrom(ctx.body());
		return new LoginData(json.get("username").asString(), json.get("password").asString());
	}
}
