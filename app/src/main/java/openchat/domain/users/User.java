package openchat.domain.users;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class User {

	private String id;
	private String username;
	private String password;
	private String about;

	public User(String id, String username, String password, String about) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.about = about;
	}

	public String id() {
		return id;
	}

	public String username() {
		return username;
	}

	public String password() {
		return password;
	}

	public String about() {
		return about;
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
