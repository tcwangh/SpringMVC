package idv.tim.repository.persistence;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import idv.tim.repository.model.Contact;

@Repository
@Transactional
@Qualifier("contactRepository")
public class ContactRepositoryImpl implements ContactRepository{
		
	@PersistenceContext
	private EntityManager entityManager;
	
	
    public void persist(Contact contact) {
		entityManager.persist(contact);
	}
	
	
    public List<Contact> list() {
        // implementation details goes here...
		TypedQuery<Contact> query = entityManager.createQuery(
	            "SELECT p FROM Contact p", Contact.class);
	        return query.getResultList();
	}
    
    public void saveOrUpdate(Contact contact) {
    	System.out.println(contact.getId());
    	if (contact.getId() > 0) {
    		entityManager.merge(contact);
    	}else {
    		entityManager.persist(contact);
    	}
    }
	
	/*
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
	*/
    
    @Override
    public void delete(int contactId) {
    	Contact theContact = entityManager.find(Contact.class, contactId);
    	entityManager.remove(theContact);
    }
 
   
    
    public List<Contact> get(int contactId) {
    	Contact theContact = entityManager.find(Contact.class, contactId);
    	List<Contact> listContact = new ArrayList<Contact>();
    	listContact.add(theContact);
    	return listContact;
    	
    }
    
    /*
 
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
    */
        
}
