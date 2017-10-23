package edu.sjsu.cloud.request;

import java.io.Serializable;

public class DeleteFileRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	Integer id;
	String fileName;
	String userName;

	public DeleteFileRequest() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "DeleteFileRequest [id=" + id + ", fileName=" + fileName + ", userName=" + userName + "]";
	}

}
