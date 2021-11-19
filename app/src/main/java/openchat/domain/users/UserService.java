package openchat.domain.users;

public class UserService {

	private IdGenerator idGenerator;
	private UserRepository userRepository;

	public UserService(IdGenerator idGenerator, UserRepository userRepository) {
		this.idGenerator = idGenerator;
		this.userRepository = userRepository;
	}

	public User createUser(RegistrationData registrationData) throws UsernamerAlreadyInUseException {
		validateUsername(registrationData.username());
		User newUser = createUserFrom(registrationData);
		userRepository.add(newUser);
		return newUser;
	}

	private void validateUsername(String username) throws UsernamerAlreadyInUseException {
		if(userRepository.itAlreadyExists(username))
			throw new UsernamerAlreadyInUseException();
	}

	private User createUserFrom(RegistrationData registrationData) {
		String idGenerated = idGenerator.next();
		User newUser = new User(idGenerated, 
								registrationData.username(), 
								registrationData.password(), 
								registrationData.about());
		return newUser;
	}
 
}
