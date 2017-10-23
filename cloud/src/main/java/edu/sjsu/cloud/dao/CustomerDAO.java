package edu.sjsu.cloud.dao;

import java.util.List;

import edu.sjsu.cloud.model.Customer;

public interface CustomerDAO {

	/**
	 * Fetch all customers
	 * @return
	 */
	public List<Customer> getAllCustomer();

	/**
	 * Fetch user based on credentials
	 * @param userName
	 * @param password
	 * @return
	 */
	public Customer getCustomer(String userName, String password);

	/**
	 * Fetch customer by username
	 * @param userName
	 * @return
	 */
	public Customer getCustomerByUserName(String userName);

	/**
	 * method to create customer object
	 * @param customer
	 */
	public void createCustomer(Customer customer);
}
