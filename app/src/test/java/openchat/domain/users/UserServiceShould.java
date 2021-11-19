package openchat.domain.users;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.*;

class UserServiceShould {

	private static final String USER_ID = UUID.randomUUID().toString();
	private static final String USERNAME = "char";
	private static final String PASSWORD = "123";
	private static final String ABOUT = "about char";
	private static final RegistrationData REGISTRATION_DATA = new RegistrationData(USERNAME, PASSWORD, ABOUT);
	private static final User USER = new User(USER_ID, 
												USERNAME, 
												PASSWORD, 
												ABOUT);
	IdGenerator idGenerator;
	UserRepository userRepository;
	private UserService userService;
	
	@BeforeEach
	public void initialize() {
		idGenerator = mock(IdGenerator.class);
		userRepository = mock(UserRepository.class);
		userService = new UserService(idGenerator, userRepository);
	}

	@Test
	void create_a_user() throws UsernamerAlreadyInUseException {
		given(idGenerator.next()).willReturn(USER_ID);
		User result = userService.createUser(REGISTRATION_DATA);
		verify(userRepository).add(USER);
		assertThat(result).isEqualTo(USER);
	}

	@Test
	void throw_exception_when_username_already_in_use() throws UsernamerAlreadyInUseException {
		given(userRepository.itAlreadyExists(USERNAME)).willReturn(true);
		assertThrows(UsernamerAlreadyInUseException.class, 
				()-> userService.createUser(REGISTRATION_DATA));
	}
}
