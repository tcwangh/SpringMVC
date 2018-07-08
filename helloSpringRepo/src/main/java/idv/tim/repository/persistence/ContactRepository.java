package idv.tim.repository.persistence;

import java.util.List;
import idv.tim.repository.model.Contact;


public interface ContactRepository {
	
	 public void saveOrUpdate(Contact contact);
     
	 public void delete(int contactId);
	
	 public void persist(Contact contact);
	     
	 public List<Contact> get(int contactId);
	     
	 public List<Contact> list();

}
