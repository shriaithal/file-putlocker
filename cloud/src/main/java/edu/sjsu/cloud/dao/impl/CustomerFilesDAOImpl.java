package edu.sjsu.cloud.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.util.CollectionUtils;

import edu.sjsu.cloud.dao.CustomerFileDAO;
import edu.sjsu.cloud.model.CustomerFiles;

/**
 * DAO Implementation class
 * 
 * @author Shriaithal
 *
 */
@Repository
@Transactional
@SuppressWarnings("unchecked")
public class CustomerFilesDAOImpl implements CustomerFileDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void deleteFile(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Object persistentInstance = session.load(CustomerFiles.class, id);
		if (persistentInstance != null) {
			session.delete(persistentInstance);
		}
	}

	@Override
	public CustomerFiles getExistingFile(Integer customerId, String fileName) {
		Query query = sessionFactory.getCurrentSession()
				.createQuery("from CustomerFiles where customerId = :customerId and name = :fileName");
		query.setParameter("customerId", customerId);
		query.setParameter("fileName", fileName);

		List<CustomerFiles> customerFile = query.list();
		if (CollectionUtils.isNullOrEmpty(customerFile)) {
			return null;
		}
		return customerFile.get(0);
	}

	@Override
	public void createOrUpdate(CustomerFiles customerFile) {
		sessionFactory.getCurrentSession().saveOrUpdate(customerFile);
	}

	@Override
	public List<CustomerFiles> getAllCustomerFiles(Integer customerId) {

		Query query = sessionFactory.getCurrentSession()
				.createQuery("from CustomerFiles where customerId = :customerId");
		query.setParameter("customerId", customerId);
		List<CustomerFiles> customerFiles = query.list();
		return customerFiles;
	}
}
