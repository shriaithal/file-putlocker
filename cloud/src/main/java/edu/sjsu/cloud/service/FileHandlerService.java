package edu.sjsu.cloud.service;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.ValidationException;

import org.springframework.web.multipart.MultipartFile;

import edu.sjsu.cloud.dto.CustomerDTO;
import edu.sjsu.cloud.dto.CustomerFilesDTO;
import edu.sjsu.cloud.dto.GenericFileResponse;
import edu.sjsu.cloud.dto.GetCustomerFilesResponse;
import edu.sjsu.cloud.request.SignUpRequest;

/**
 * Service Interface class
 * 
 * @author Shriaithal
 *
 */
public interface FileHandlerService {

	/**
	 * Upload to S3
	 * 
	 * @param file
	 * @param fileName
	 * @param userName 
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws ValidationException
	 */
	CustomerFilesDTO uploadFile(MultipartFile file, String fileName, String description, String userName)
			throws IllegalStateException, IOException, ValidationException;

	/**
	 * Delete file from S3
	 * 
	 * @param fileName
	 * @param id
	 */
	void deleteFile(String fileName, Integer id, String userName);

	/**
	 * Fetch all files from S3
	 * @param userId 
	 * 
	 * @return
	 */
	GetCustomerFilesResponse getCustomerFileList(String userId);

	List<CustomerDTO> getAllCustomer();

	/**
	 * Login API to verify user credentials
	 * 
	 * @param userName
	 * @param password
	 * @return 
	 * @throws ValidationException 
	 */
	CustomerDTO login(String userName, String password) throws ValidationException;

	/**
	 * Fetch customer using username
	 * @param userName
	 * @return
	 */
	CustomerDTO getCustomer(String userName);

	/**
	 * Create customer record
	 * @param request
	 * @return
	 * @throws ValidationException 
	 */
	GenericFileResponse signUp(SignUpRequest request) throws ValidationException;


}
