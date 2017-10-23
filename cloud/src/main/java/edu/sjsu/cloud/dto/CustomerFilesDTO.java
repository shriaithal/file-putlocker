package edu.sjsu.cloud.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class CustomerFilesDTO implements Serializable {

	private static final long serialVersionUID = 498770093043158762L;

	private int id;
	private String name;
	private String path;
	private String description;
	private String fileSize;
	private Timestamp createdTime;
	private Timestamp updatedTime;

	public CustomerFilesDTO() {
	}

	public CustomerFilesDTO(int id, String name, String description, String fileSize, String path, Timestamp createdTime,
			Timestamp updatedTime) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.fileSize = fileSize;
		this.path = path;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public Timestamp getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return "CustomerFilesDTO [id=" + id + ", name=" + name + ", path=" + path + ", description=" + description
				+ ", fileSize=" + fileSize + ", createdTime=" + createdTime + ", updatedTime=" + updatedTime + "]";
	}
}
