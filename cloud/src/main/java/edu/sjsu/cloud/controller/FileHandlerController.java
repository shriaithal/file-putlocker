package edu.sjsu.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import edu.sjsu.cloud.dto.CustomerDTO;
import edu.sjsu.cloud.dto.CustomerFilesDTO;
import edu.sjsu.cloud.dto.GenericFileResponse;
import edu.sjsu.cloud.dto.GetCustomerFilesResponse;
import edu.sjsu.cloud.request.DeleteFileRequest;
import edu.sjsu.cloud.request.LoginRequest;
import edu.sjsu.cloud.request.SignUpRequest;
import edu.sjsu.cloud.service.FileHandlerService;

/**
 * Rest Controller Class Entry point for all rest APIs
 * 
 * @author Shriaithal
 *
 */
@Controller
public class FileHandlerController {

	// private static Logger LOG =
	// LoggerFactory.getLogger(FileHandlerController.class);

	@Autowired
	FileHandlerService fileHandlerService;

	/**
	 * Upload file to S3. Rewrite if exists Create New if not
	 * 
	 * @param file
	 * @param fileName
	 * @return version id
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<CustomerFilesDTO> uploadFile(
			@RequestParam(value = "file", required = true) MultipartFile file,
			@RequestParam(value = "fileName", required = true) String fileName,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "userName", required = true) String userName) {

		// LOG.debug("Start File Upload fileName :"+ fileName);
		System.out.println("Start File Upload fileName :"+ fileName);

		ResponseEntity<CustomerFilesDTO> responseEntity = null;
		try {
			CustomerFilesDTO response = fileHandlerService.uploadFile(file, fileName, description, userName);
			responseEntity = new ResponseEntity<CustomerFilesDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			// LOG.error("Exception", e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<CustomerFilesDTO>(new CustomerFilesDTO(),
					HttpStatus.EXPECTATION_FAILED);
		}

		// LOG.debug("End File Upload fileName :"+ fileName);
		System.out.println("End File Upload fileName :"+ fileName);
		return responseEntity;
	}

	/**
	 * Get All files of a customer
	 * 
	 * @return
	 */
	@RequestMapping(value = "/allCustomerFiles", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<GetCustomerFilesResponse> getCustomerFileList(@RequestParam String userId) {
		System.out.println("Calling getCustomerFiles");
		ResponseEntity<GetCustomerFilesResponse> responseEntity = null;
		try {
			GetCustomerFilesResponse customerFileList = fileHandlerService.getCustomerFileList(userId);
			System.out.println("Customer List:" + customerFileList);
			responseEntity = new ResponseEntity<GetCustomerFilesResponse>(customerFileList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<GetCustomerFilesResponse>(new GetCustomerFilesResponse(),
					HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}

	/**
	 * Get All files of a customer
	 * 
	 * @return
	 */
	@RequestMapping(value = "/customer", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<CustomerDTO> getCustomer(@RequestParam String userName) {
		System.out.println("Calling getCustomer");
		ResponseEntity<CustomerDTO> responseEntity = null;
		try {
			CustomerDTO customer = fileHandlerService.getCustomer(userName);
			System.out.println("Customer List:" + customer);
			responseEntity = new ResponseEntity<CustomerDTO>(customer, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<CustomerDTO>(new CustomerDTO(), HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}

	/**
	 * Delete file
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<GenericFileResponse> deleteFile(@RequestBody DeleteFileRequest request) {
		System.out.println("Calling deleteFile");
		ResponseEntity<GenericFileResponse> responseEntity = null;
		try {
			fileHandlerService.deleteFile(request.getFileName(), request.getId(), request.getUserName());
			responseEntity = new ResponseEntity<GenericFileResponse>(new GenericFileResponse("SUCCESS"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<GenericFileResponse>(new GenericFileResponse(e.getMessage()),
					HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}

	/**
	 * Login API to validate user credentials
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login/action", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<CustomerDTO> login(@RequestBody LoginRequest request) {
		System.out.println("Calling Login API");
		ResponseEntity<CustomerDTO> responseEntity = null;
		try {
			CustomerDTO response = fileHandlerService.login(request.getUserName(), request.getPassword());
			responseEntity = new ResponseEntity<CustomerDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<CustomerDTO>(new CustomerDTO(), HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}
	
	/**
	 * Create customer record
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/signUp", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<GenericFileResponse> signUp(@RequestBody SignUpRequest request) {
		System.out.println("Calling Login API");
		ResponseEntity<GenericFileResponse> responseEntity = null;
		try {
			GenericFileResponse response = fileHandlerService.signUp(request);
			responseEntity = new ResponseEntity<GenericFileResponse>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<GenericFileResponse>(new GenericFileResponse(e.getMessage()), HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}
}
