package edu.sjsu.cloud.request;

import java.io.Serializable;

public class SignUpRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String firstname;
	private String lastname;
	private String emailId;
	private String password;

	public SignUpRequest() {
	}

	public SignUpRequest(String firstname, String lastname, String emailId, String password) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.emailId = emailId;
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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

	@Override
	public String toString() {
		return "SignUpRequest [firstname=" + firstname + ", lastname=" + lastname + ", emailId=" + emailId
				+ ", password=" + password + "]";
	}
}
