package com.mmxx.contact.dao;

import java.util.List;

import com.mmxx.contact.model.Contact;

public interface ContactDAO {
	public int save(Contact c);
	public int update(Contact c);
	public Contact get(Integer id);
	public int delete(Integer id);
	public int deleteTests();
	public List<Contact> list();
}
