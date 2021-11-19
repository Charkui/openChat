package openchat.api;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.*;

import com.eclipsesource.json.JsonObject;

import io.javalin.http.Context;
import openchat.domain.users.InvalidUserLoginCredentialsException;
import openchat.domain.users.LoginData;
import openchat.domain.users.User;
import openchat.domain.users.UserRepository;
import openchat.infrastructure.builders.UserBuilder;

class LoginAPIShould {

	private static final String USERNAME = "Charkui";
	private static final String PASSWORD = "char";
	private static final User USER = UserBuilder.aUser().withUsername(USERNAME).withPassword(PASSWORD).build();
	private static final LoginData LOGIN_DATA = new LoginData(USERNAME, PASSWORD);
	private LoginAPI loginAPI;
	private Context ctx;
	private UserRepository userRepository;

	@BeforeEach
	public void initialize() {
		userRepository = mock(UserRepository.class);
		loginAPI = new LoginAPI(userRepository);
		ctx = mock(Context.class);
		given(ctx.body()).willReturn(jsonContaining(LOGIN_DATA));		
	}
	
	@Test
	void return_a_json_representation_of_a_valid_user() throws InvalidUserLoginCredentialsException {
		given(userRepository.userFor(LOGIN_DATA)).willReturn(USER);
		String result = loginAPI.login(ctx);
		verify(ctx).status(200);
		verify(ctx).contentType("application/json");
		assertThat(result).isEqualTo(jsonContaining(USER));
	}
	
	@Test
	void throw_an_exception_when_login_is_incorrect() throws InvalidUserLoginCredentialsException {
		given(userRepository.userFor(LOGIN_DATA)).willThrow(InvalidUserLoginCredentialsException.class);
		String result = loginAPI.login(ctx);
		verify(ctx).status(400);
		assertThat(result).isEqualTo("Username/password are incorrect");
	}

	private String jsonContaining(LoginData loginData) {
		return new JsonObject()
				.add("username", loginData.username())
				.add("password", loginData.password())
				.toString();
	}

	private String jsonContaining(User user) {
		return new JsonObject()
					.add("id", user.id())
					.add("username", user.username())
					.add("about", user.about())
					.toString();
	}

}
