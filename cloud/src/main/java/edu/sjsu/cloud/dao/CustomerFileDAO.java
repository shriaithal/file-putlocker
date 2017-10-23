package edu.sjsu.cloud.dao;

import java.util.List;

import edu.sjsu.cloud.model.CustomerFiles;

/**
 * Reposirty for CustomerFiles Table Data Access using Hibernate
 * 
 * @author Shriaithal
 *
 */
public interface CustomerFileDAO {

	/**
	 * Delete File
	 * 
	 * @param id
	 */
	void deleteFile(Integer id);

	/**
	 * Get File metadata from user id and fileName
	 * 
	 * @param customerId
	 * @param fileName
	 * @return
	 */
	CustomerFiles getExistingFile(Integer customerId, String fileName);

	/**
	 * Insert File metadata to folder
	 * 
	 * @param fileName
	 */
	void createOrUpdate(CustomerFiles customerFile);

	/**
	 * Fetch all files for a particular customer
	 * @param customerId
	 * @return
	 */
	List<CustomerFiles> getAllCustomerFiles(Integer customerId);

}
