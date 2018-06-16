package idv.tim.springjdbc.dao;

import java.util.List;
import idv.tim.springjdbc.model.Contact;

public interface ContactDao {
	
	 public int saveOrUpdate(Contact contact);
     
	 public void delete(int contactId);
	     
	 public List<Contact> get(int contactId);
	     
	 public List<Contact> list();

}
