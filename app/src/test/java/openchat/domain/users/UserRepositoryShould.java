package openchat.domain.users;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import java.util.function.IntPredicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import openchat.infrastructure.builders.UserBuilder;

import static org.mockito.BDDMockito.*;

class UserRepositoryShould {

	private static final String USERNAME = "Charkui";
	private static final String PASSWORD = "char";
	private static final User CHARKUI = UserBuilder.aUser().withUsername(USERNAME).withPassword(PASSWORD).build();
	private static final User REFREET = UserBuilder.aUser().withUsername("Refreet").build();
	private UserRepository userRepository;
	private static final LoginData LOGIN_DATA = new LoginData(USERNAME, PASSWORD);
	
	@BeforeEach
	public void initialize() {
		userRepository = new UserRepository();
	}

	@Test
	void inform_when_a_username_is_already_taken() throws UsernamerAlreadyInUseException {
		userRepository.add(CHARKUI);
		assertThat(userRepository.itAlreadyExists(CHARKUI.username())).isTrue();
		assertThat(userRepository.itAlreadyExists(REFREET.username())).isFalse();
	}
	
	@Test
	void return_a_registered_user_with_the_correct_credentials_on_login() throws UsernamerAlreadyInUseException, InvalidUserLoginCredentialsException {
		userRepository.add(CHARKUI);
		User user = userRepository.userFor(LOGIN_DATA);
		assertThat(user).isEqualTo(CHARKUI);
	}

}
