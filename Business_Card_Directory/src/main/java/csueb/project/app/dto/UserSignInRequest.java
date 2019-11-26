package csueb.project.app.dto;

import java.io.Serializable;

public class UserSignInRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	private String emailId;
	private String password;
	private String securityQ1 = null;
	private String securityQ2 = null;
	private String securityV1 = null;
	private String securityV2 = null;

	public UserSignInRequest() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecurityQ1() {
		return securityQ1;
	}

	public void setSecurityQ1(String securityQ1) {
		this.securityQ1 = securityQ1;
	}

	public String getSecurityQ2() {
		return securityQ2;
	}

	public void setSecurityQ2(String securityQ2) {
		this.securityQ2 = securityQ2;
	}

	public String getSecurityV1() {
		return securityV1;
	}

	public void setSecurityV1(String securityV1) {
		this.securityV1 = securityV1;
	}

	public String getSecurityV2() {
		return securityV2;
	}

	public void setSecurityV2(String securityV2) {
		this.securityV2 = securityV2;
	}

}
