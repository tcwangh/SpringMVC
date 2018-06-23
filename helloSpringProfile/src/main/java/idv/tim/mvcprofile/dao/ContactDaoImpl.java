package idv.tim.mvcprofile.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import idv.tim.mvcprofile.model.Contact;

@Repository
@Qualifier("contactDAO")
public class ContactDaoImpl implements ContactDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
    public int saveOrUpdate(Contact contact) {
		int result = 0;
        if (contact.getId() > 0) {
	        // update
	        String sql = "UPDATE contact SET name=?, email=?, address=?, "
	                    + "telephone=? WHERE contact_id=?";
	        result = jdbcTemplate.update(sql, contact.getName(), contact.getEmail(),
	                contact.getAddress(), contact.getTelephone(), contact.getId());
	    } else {
	        // insert
	        String sql = "INSERT INTO contact (name, email, address, telephone)"
	                    + " VALUES (?, ?, ?, ?)";
	        result = jdbcTemplate.update(sql, contact.getName(), contact.getEmail(),
	                contact.getAddress(), contact.getTelephone());
	    }
        return result;
    }
 
    @Override
    public void delete(int contactId) {
    	String sql = "DELETE FROM contact WHERE contact_id=?";
        jdbcTemplate.update(sql, contactId);
    }
 
    @Override
    public List<Contact> list() {
        // implementation details goes here...
    	String sql = "SELECT * FROM contact";
        List<Contact> listContact = jdbcTemplate.query(sql, new RowMapper<Contact>() {
            @Override
            public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
                Contact aContact = new Contact();
                aContact.setId(rs.getInt("contact_id"));
                aContact.setName(rs.getString("name"));
                aContact.setEmail(rs.getString("email"));
                aContact.setAddress(rs.getString("address"));
                aContact.setTelephone(rs.getString("telephone"));
                return aContact;
            }
        });
        return listContact;
    }
 
    @Override
    public List<Contact> get(int contactId) {
    	String sql = "SELECT * FROM contact WHERE contact_id=" + contactId;
    	List<Contact> listContact = jdbcTemplate.query(sql, new RowMapper<Contact>() {
            @Override
            public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
                Contact aContact = new Contact();
                aContact.setId(rs.getInt("contact_id"));
                aContact.setName(rs.getString("name"));
                aContact.setEmail(rs.getString("email"));
                aContact.setAddress(rs.getString("address"));
                aContact.setTelephone(rs.getString("telephone"));
                return aContact;
            }
        });
    	return listContact;
    }
        
}
