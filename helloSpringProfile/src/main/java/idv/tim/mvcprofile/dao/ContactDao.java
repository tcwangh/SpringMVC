package idv.tim.mvcprofile.dao;

import java.util.List;

import idv.tim.mvcprofile.model.Contact;

public interface ContactDao {
	
	 public int saveOrUpdate(Contact contact);
     
	 public void delete(int contactId);
	     
	 public List<Contact> get(int contactId);
	     
	 public List<Contact> list();

}
