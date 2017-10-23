package edu.sjsu.cloud.request;

import java.io.Serializable;

public class LoginRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	String userName;
	String password;

	public LoginRequest() {
	}

	public LoginRequest(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginRequest [userName=" + userName + ", password=" + password + "]";
	}

}
