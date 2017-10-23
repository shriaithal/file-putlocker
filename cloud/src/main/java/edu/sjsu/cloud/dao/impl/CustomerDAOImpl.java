package edu.sjsu.cloud.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.util.CollectionUtils;

import edu.sjsu.cloud.dao.CustomerDAO;
import edu.sjsu.cloud.model.Customer;

/**
 * Customer Data Access Layer
 * @author Shriaithal
 *
 */
@Repository("customerDao")
@Transactional
@SuppressWarnings("unchecked")
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getAllCustomer() {
		List<Customer> customer = sessionFactory.getCurrentSession().createQuery("from Customer").list();
		return customer;
	}

	@Override
	public Customer getCustomer(String userName, String password) {
		Customer customer = null;
		Query query = sessionFactory.getCurrentSession()
				.createQuery("from Customer where emailId= :userName and password= :password");
		query.setParameter("userName", userName);
		query.setParameter("password", password);
		List<Customer> customerList = query.list();
		if (!CollectionUtils.isNullOrEmpty(customerList)) {
			customer = customerList.get(0);
		}
		return customer;
	}

	@Override
	public Customer getCustomerByUserName(String userName) {
		Customer customer = null;
		Query query = sessionFactory.getCurrentSession()
				.createQuery("from Customer where emailId= :userName");
		query.setParameter("userName", userName);
		List<Customer> customerList = query.list();
		if (!CollectionUtils.isNullOrEmpty(customerList)) {
			customer = customerList.get(0);
		}
		return customer;
	}

	@Override
	public void createCustomer(Customer customer) {
		sessionFactory.getCurrentSession().save(customer);
	}
}
