package openchat.domain.users;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.*;

import openchat.infrastructure.builders.UserBuilder;

class UserServiceShould {

	private static final String USER_ID = UUID.randomUUID().toString();
	private static final String USERNAME = "char";
	private static final String PASSWORD = "123";
	private static final String ABOUT = "about char";
	private static final RegistrationData REGISTRATION_DATA = new RegistrationData(USERNAME, PASSWORD, ABOUT);
	private static final User USER = UserBuilder.aUser().build();
	IdGenerator idGenerator;
	UserRepository userRepository;
	private UserService userService;
	
	@BeforeEach
	public void initialize() {
		idGenerator = mock(IdGenerator.class);
		userRepository = mock(UserRepository.class);
		userService = new UserService(idGenerator, userRepository);
	}

	@Disabled
	@Test
	void create_a_user() throws UsernamerAlreadyInUseException {
		given(idGenerator.next()).willReturn(USER_ID);
		User result = userService.createUser(REGISTRATION_DATA);
		verify(userRepository).add(USER);
		assertThat(result).isEqualTo(USER);
	}

}
