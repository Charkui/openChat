package openchat.api;

import com.eclipsesource.json.JsonObject;

import io.javalin.http.ContentType;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import openchat.domain.users.RegistrationData;
import openchat.domain.users.User;
import openchat.domain.users.UserService;
import openchat.domain.users.UsernamerAlreadyInUseException;
import openchat.infrastructure.json.UserJson;

public class UsersAPI {

	private UserService userService;

	public UsersAPI(UserService userService) {
		this.userService = userService;
	}

	public String createUser(Context ctx) {
		RegistrationData registration = registrationDataFrom(ctx);
		try {
			User user = userService.createUser(registration);
			ctx.status(HttpCode.CREATED.getStatus());
			ctx.contentType(ContentType.APPLICATION_JSON.getMimeType());
			return UserJson.jsonFor(user);			
		}catch (UsernamerAlreadyInUseException e) {
			ctx.status(HttpCode.BAD_REQUEST.getStatus());
			return "Username already exists";
		}
	}

	private RegistrationData registrationDataFrom(Context ctx) {
		JsonObject json = JsonObject.readFrom(ctx.body());
		return new RegistrationData(
				json.get("username").asString(), 
				json.get("password").asString(), 
				json.get("about").asString());
	}
}
