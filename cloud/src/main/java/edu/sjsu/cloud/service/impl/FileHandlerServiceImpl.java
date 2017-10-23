package edu.sjsu.cloud.service.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.bind.ValidationException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.util.CollectionUtils;
import com.amazonaws.util.StringUtils;

import edu.sjsu.cloud.dao.CustomerDAO;
import edu.sjsu.cloud.dao.CustomerFileDAO;
import edu.sjsu.cloud.dto.CustomerDTO;
import edu.sjsu.cloud.dto.CustomerFilesDTO;
import edu.sjsu.cloud.dto.GenericFileResponse;
import edu.sjsu.cloud.dto.GetCustomerFilesResponse;
import edu.sjsu.cloud.model.Customer;
import edu.sjsu.cloud.model.CustomerFiles;
import edu.sjsu.cloud.request.SignUpRequest;
import edu.sjsu.cloud.service.FileHandlerService;

/**
 * Service implementation class to handle upload dowload and delete
 * 
 * @author Shriaithal
 *
 */
@Service
@SuppressWarnings("deprecation")
public class FileHandlerServiceImpl implements FileHandlerService {

	private AmazonS3Client s3Client;

	@Autowired
	private FileHandlerHelper fileHandlerHelper;

	@Autowired
	CustomerDAO customerDao;

	@Autowired
	CustomerFileDAO customerFilesDao;

	/**
	 * Initialize S3 with appropriate access keys
	 */
	@PostConstruct
	private void initS3Client() {
		// fetch access keys from property file
		String accessId = fileHandlerHelper.getFileProperties().get("aws.s3.access.id");
		String secretKey = fileHandlerHelper.getFileProperties().get("aws.s3.access.key");
		AWSCredentials s3Credentials = new BasicAWSCredentials(accessId, secretKey);

		// instantiate S3 client
		s3Client = new AmazonS3Client(s3Credentials);

		// Set S3 region
		s3Client.setRegion(Region.getRegion(Regions.US_WEST_1));

		// Enable Transfer Acceleration Mode
		s3Client.setS3ClientOptions(S3ClientOptions.builder().setAccelerateModeEnabled(true).build());
	}

	@Override
	public CustomerFilesDTO uploadFile(MultipartFile file, String fileName, String description, String userName)
			throws IllegalStateException, IOException, ValidationException {

		// Check if file size is greater than 10MB
		int maxSizeInBytes = 10485760;
		if (file.getSize() > maxSizeInBytes) {
			throw new ValidationException("File Size larger than 10MB");
		}
		// Fetch bucket names and keys for upload to S3
		File tempFile = fileHandlerHelper.multipartToFile(file);
		String bucketName = fileHandlerHelper.getFileProperties().get("aws.s3.bucket.name");

		String key = fileHandlerHelper.getFileProperties().get("aws.s3.key.name") + userName + "/" + fileName;

		// upload to S3
		@SuppressWarnings("unused")
		PutObjectResult awsResult = s3Client.putObject(bucketName, key, tempFile);

		// Create DB entry if new file or else update
		CustomerFilesDTO customerFilesDTO = createOrUpdateCustomerFiles(fileName, description, key, userName,
				file.getSize());
		return customerFilesDTO;
	}

	private CustomerFilesDTO createOrUpdateCustomerFiles(String fileName, String description, String path,
			String userName, Long fileSize) {

		Customer customer = customerDao.getCustomerByUserName(userName);

		CustomerFiles customerFile = customerFilesDao.getExistingFile(customer.getId(), fileName);

		if (null == customerFile) {
			customerFile = new CustomerFiles();
			customerFile.setName(fileName);
			customerFile.setCreatedTime(new Timestamp(System.currentTimeMillis()));
			customerFile.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
			customerFile.setDescription(description);
			customerFile.setFileSize(FileUtils.byteCountToDisplaySize(fileSize));
			customerFile.setPath(path);
			customerFile.setCustomer(customer);
		} else {
			customerFile.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
			customerFile.setFileSize(FileUtils.byteCountToDisplaySize(fileSize));
			if (!StringUtils.isNullOrEmpty(description)) {
				customerFile.setDescription(description);
			}
		}

		customerFilesDao.createOrUpdate(customerFile);
		CustomerFiles returnCustomerFile = customerFilesDao.getExistingFile(customer.getId(), fileName);
		CustomerFilesDTO customerFilesDTO = new CustomerFilesDTO(returnCustomerFile.getId(),
				returnCustomerFile.getName(), returnCustomerFile.getDescription(), returnCustomerFile.getFileSize(),
				returnCustomerFile.getPath(), returnCustomerFile.getCreatedTime(), returnCustomerFile.getUpdatedTime());

		return customerFilesDTO;
	}

	@Override
	public List<CustomerDTO> getAllCustomer() {
		List<CustomerDTO> customerReturnList = new ArrayList<CustomerDTO>();
		List<Customer> customerList = customerDao.getAllCustomer();
		for (Customer customer : customerList) {
			CustomerDTO cust = new CustomerDTO(customer.getId(), customer.getFirstName(), customer.getLastName(),
					customer.getEmailId());
			customerReturnList.add(cust);
		}
		System.out.println(customerReturnList);
		return customerReturnList;
	}

	@Override
	public void deleteFile(String fileName, Integer id, String userName) {
		// Fetch bucket names and keys
		String bucketName = fileHandlerHelper.getFileProperties().get("aws.s3.bucket.name");

		String key = fileHandlerHelper.getFileProperties().get("aws.s3.key.name") + userName + "/" + fileName;

		// delete file from S3
		s3Client.deleteObject(bucketName, key);

		// delete entry from table
		customerFilesDao.deleteFile(id);
	}

	@Override
	public GetCustomerFilesResponse getCustomerFileList(String userId) {
		GetCustomerFilesResponse response = new GetCustomerFilesResponse();
		List<CustomerFilesDTO> fileList = new ArrayList<CustomerFilesDTO>();

		Integer customerId = Integer.parseInt(userId);

		List<CustomerFiles> customerFiles = customerFilesDao.getAllCustomerFiles(customerId);
		if (!CollectionUtils.isNullOrEmpty(customerFiles)) {
			for (CustomerFiles customerFile : customerFiles) {
				CustomerFilesDTO customerFilesDTO = new CustomerFilesDTO(customerFile.getId(), customerFile.getName(),
						customerFile.getDescription(), customerFile.getFileSize(), customerFile.getPath(), customerFile.getCreatedTime(),
						customerFile.getUpdatedTime());
				fileList.add(customerFilesDTO);
			}
		}

		response.setCustomerFileList(fileList);
		return response;
	}

	@Override
	public CustomerDTO login(String userName, String password) throws ValidationException {
		Customer customer = customerDao.getCustomer(userName, password);
		if (null == customer) {
			throw new ValidationException("Invalid username/password");
		}
		CustomerDTO customerDTO = new CustomerDTO(customer.getId(), customer.getFirstName(), customer.getLastName(),
				customer.getEmailId());
		return customerDTO;
	}

	@Override
	public CustomerDTO getCustomer(String userName) {
		Customer customer = customerDao.getCustomerByUserName(userName);
		CustomerDTO customerDTO = new CustomerDTO(customer.getId(), customer.getFirstName(), customer.getLastName(),
				customer.getEmailId());
		return customerDTO;
	}

	@Override
	public GenericFileResponse signUp(SignUpRequest request) throws ValidationException {
		Customer existingCustomer = customerDao.getCustomerByUserName(request.getEmailId());
		if(null != existingCustomer) {
			throw new ValidationException("User already exist");
		}
		Customer customer = new Customer();
		customer.setFirstName(request.getFirstname());
		customer.setLastName(request.getLastname());
		customer.setEmailId(request.getEmailId());
		customer.setPassword(request.getPassword());
		
		customerDao.createCustomer(customer);
		GenericFileResponse response = new GenericFileResponse("SUCCESS");
		return response;
	}
}
