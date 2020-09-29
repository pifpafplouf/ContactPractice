package com.mmxx.contactDAO;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.mmxx.contact.dao.ContactDAO;
import com.mmxx.contact.dao.ContactDAOImpl;
import com.mmxx.contact.model.Contact;

import com.mmxx.contact.config.DataBaseInfo;

class ContactDAOTest {
	
	private DriverManagerDataSource dataSource;
	private ContactDAO dao;
	
	@BeforeEach
	void driverManagerDataSourceGenerator() {
		dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(DataBaseInfo.driver);
		dataSource.setUrl(DataBaseInfo.url);
		dataSource.setUsername(DataBaseInfo.user);
		dataSource.setPassword(DataBaseInfo.password);
		
		dao = new ContactDAOImpl(dataSource);
		
		clearThenGenerateDataTest();
	}
	
	private void clearThenGenerateDataTest () {
		
		dao.deleteTests();
		
		Contact contact = new Contact(1, "Anatol Test", "anatol.test@mail.test", "1er avenue des tests, TT", "987 654-3211");		
		dao.save(contact);
		
		contact = new Contact(2, "Bella Test", "bella.test@mail.test", "2nd avenue des tests, TT", "987 654-3212");
		dao.save(contact);		
		
		contact = new Contact(3, "Chris Test", "tiuann.test@mail.test", "3eme avenue des tests, TT", "987 654-3213");
		dao.save(contact);
	}

	@Test
	void testSave() {		
		Contact contact = new Contact(4, "Trevor Test", "trevor.test@mail.test", "Test State, TT", "987 654-3212");
		int result = dao.save(contact);
		
		assertTrue(result > 0);
	}

	@Test
	void testUpdate() {		
		Contact contact = new Contact(3, "Dean Test", "Dean.test@mail.test", "3eme avenue des tests State, TT", "987 654-3213");
		int result = dao.update(contact);
		
		assertTrue(result > 0);
	}

	@Test
	void testGet() {		
		Integer id = 1;
		
		Contact contact = dao.get(id);
		
		if (contact != null)
			System.out.println("ContactDAOTest testGet result: " + contact.toString());
		
		assertNotNull(contact);		
	}

	@Test
	void testDelete() {
		Integer id = 2;
		int result = dao.delete(id);
		
		assertTrue(result > 0);		
	}

	@Test
	void testList() {
		List<Contact> listContacts = dao.list();
		
		for (Contact aContact : listContacts) {
			System.out.println(aContact.toString());
		}
		
		assertTrue(!listContacts.isEmpty());
	}		

}
