package idv.tim.springrest.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import idv.tim.springrest.model.Contact;
import java.lang.Integer;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Integer>{
	
	 public Contact save(Contact contact);
     
	 public void delete(Integer id);
	
	 public List<Contact> findById(Integer id);
	     
	 public List<Contact> findAll();
	 
	 public Contact findOne(Integer id);

}
