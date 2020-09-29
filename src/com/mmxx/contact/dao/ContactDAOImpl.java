package com.mmxx.contact.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.mmxx.contact.model.Contact;

public class ContactDAOImpl implements ContactDAO {

	private JdbcTemplate jdbcTemplate;
	
	public ContactDAOImpl(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public int save(Contact c) {
		String sql = "";
		
		System.out.println("DEBUG: c: " + c.toString());
		
		if (c.getId() == null) {
			sql = "INSERT INTO contact (name, email, address, phone) VALUES (?, ?, ?, ?)";
			return jdbcTemplate.update(sql, c.getName(), c.getEmail(), c.getAddress(), c.getPhone());
		} else {
			sql = "INSERT INTO contact (contact_id, name, email, address, phone) VALUES (?, ?, ?, ?, ?)";
			return jdbcTemplate.update(sql, c.getId(), c.getName(), c.getEmail(), c.getAddress(), c.getPhone());
		}				
	}

	@Override
	public int update(Contact c) {
		String sql = "UPDATE contact SET name=?, email=?, address=?, phone=? WHERE contact_id=?";
		return jdbcTemplate.update(sql, c.getName(), c.getEmail(), c.getAddress(), c.getPhone(), c.getId());
	}

	@Override
	public Contact get(Integer id) {
		String sql = "SELECT * FROM contact WHERE contact_id = " + id;
		ResultSetExtractor<Contact> extractor = new ResultSetExtractor<Contact>() {

			@Override
			public Contact extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {					
					String name = rs.getNString("name");
					String email = rs.getNString("email");
					String address = rs.getNString("address");
					String phone = rs.getNString("phone");
					
					return new Contact(id, name, email, address, phone);
				}
				
				return null;				
			}			
		};
		
		return jdbcTemplate.query(sql, extractor);
	}

	@Override
	public int delete(Integer id) {
		String  sql = "DELETE FROM contact WHERE contact_id = " + id;
		return jdbcTemplate.update(sql);		
	}
	
	@Override
	public int deleteTests() {
		String sql = "DELETE FROM contact WHERE email LIKE '%.test@mail.test'";
		return jdbcTemplate.update(sql);
	}

	@Override
	public List<Contact> list() {
		String sql = "SELECT * FROM contact";
		
		RowMapper<Contact> rowMapper = new RowMapper<Contact>( ) {

			@Override
			public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
				Integer id = rs.getInt("contact_id");
				String name = rs.getNString("name");
				String email = rs.getNString("email");
				String address = rs.getNString("address");
				String phone = rs.getNString("phone");
				
				return new Contact(id, name, email, address, phone);
			}			
		};
		
		return jdbcTemplate.query(sql, rowMapper);
	}

}
