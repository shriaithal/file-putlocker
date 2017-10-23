package edu.sjsu.cloud.dto;

import java.io.Serializable;
import java.util.List;

public class GetCustomerFilesResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	List<CustomerFilesDTO> customerFileList;

	public GetCustomerFilesResponse() {
	}

	public GetCustomerFilesResponse(List<CustomerFilesDTO> customerFileList) {
		super();
		this.customerFileList = customerFileList;
	}

	public List<CustomerFilesDTO> getCustomerFileList() {
		return customerFileList;
	}

	public void setCustomerFileList(List<CustomerFilesDTO> customerFileList) {
		this.customerFileList = customerFileList;
	}

	@Override
	public String toString() {
		return "GetCustomerFilesResponse [customerFileList=" + customerFileList + "]";
	}

}
