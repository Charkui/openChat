package openchat.domain.users;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class LoginData {
	private final String username;
	private final String password;
	
	public LoginData(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String username() {
		return username;
	}

	public String password() {
		return password;
	}
	
	@Override
	public boolean equals(Object obj) {
		return reflectionEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return reflectionHashCode(this);
	}
}
