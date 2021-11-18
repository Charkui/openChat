package openchat.domain.users;

public class UserService {

	private IdGenerator idGenerator;
	private UserRepository userRepository;

	public UserService(IdGenerator idGenerator, UserRepository userRepository) {
		this.idGenerator = idGenerator;
		this.userRepository = userRepository;
	}

	public User createUser(RegistrationData registrationData) throws UsernamerAlreadyInUseException {
		throw new UnsupportedOperationException();
	}
 
}
