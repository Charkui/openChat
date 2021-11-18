package openchat.api;

import static org.mockito.Mockito.*;

import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.*;

import com.eclipsesource.json.JsonObject;

import io.javalin.http.Context;
import openchat.domain.users.RegistrationData;
import openchat.domain.users.User;
import openchat.domain.users.UserService;
import openchat.domain.users.UsernamerAlreadyInUseException;
import openchat.infrastructure.builders.UserBuilder;

class UsersAPIShould {

	private static final String USERNAME = "char";
	private static final String PASSWORD = "123";
	private static final String ABOUT = "about char";
	private static final RegistrationData REGISTRATION_DATA = new RegistrationData(USERNAME, PASSWORD, ABOUT);
	private static final String USER_ID = UUID.randomUUID().toString();
	private static final User USER = UserBuilder.aUser()
														.withId(USER_ID)
														.withUsername(USERNAME)
														.withPassword(PASSWORD)
														.withAbout(ABOUT)
														.build();
	UserService userService;
	Context ctx;
	private UsersAPI userAPI;
	
	@BeforeEach
	public void initialize() throws UsernamerAlreadyInUseException {
		ctx = mock(Context.class);
		userService = mock(UserService.class);
		userAPI = new UsersAPI(userService);
		given(ctx.body()).willReturn(jsonContaining(REGISTRATION_DATA));
		given(userService.createUser(REGISTRATION_DATA)).willReturn(USER);
	}
	
	@Test
	void create_a_new_user() throws UsernamerAlreadyInUseException {
		userAPI.createUser(ctx);
		
		verify(userService).createUser(REGISTRATION_DATA);
	}
	
	@Test
	public void return_json_representing_a_newly_created_user() throws UsernamerAlreadyInUseException {
		String result = userAPI.createUser(ctx);
		verify(ctx).status(201);
		verify(ctx).contentType("application/json");
		assertThat(result).isEqualTo(jsonContaining(USER));
	}
	
	@Test
	public void return_an_error_when_creating_a_user_with_an_existing_username() throws UsernamerAlreadyInUseException {
		given(userService.createUser(REGISTRATION_DATA)).willThrow(UsernamerAlreadyInUseException.class);
		String result = userAPI.createUser(ctx);
		verify(ctx).status(400);
		assertThat(result).isEqualTo("Username already exists");
	}

	private String jsonContaining(User user) {
		return new JsonObject()
				.add("id", user.id())
				.add("username", user.username())
				.add("about", user.about())
				.toString();
	}

	private String jsonContaining(RegistrationData registrationData) {
		return new JsonObject()
				.add("username", registrationData.username())
				.add("password", registrationData.password())
				.add("about", registrationData.about())
				.toString();
	}

}
 