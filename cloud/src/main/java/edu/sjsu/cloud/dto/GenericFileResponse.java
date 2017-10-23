package edu.sjsu.cloud.dto;

import java.io.Serializable;

public class GenericFileResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String message;

	public GenericFileResponse() {
	}

	public GenericFileResponse(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "GenericFileResponse [message=" + message + "]";
	}

}
