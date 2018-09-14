package idv.tim.springrest.controller;

import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import idv.tim.springrest.Exception.ContactNotFoundException;
import idv.tim.springrest.bo.ContactHandler;
import idv.tim.springrest.model.Contact;
import idv.tim.springrest.persistence.ContactRepository;

@RestController
@RequestMapping(value="/restapi")
public class ApController {
	
	private static final Logger logger = LoggerFactory.getLogger(ApController.class);
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
    private ApplicationContext ctx;
	
	@Autowired
    private ContactHandler contactHandler;
	
	@RequestMapping(value="/contact/{id}", method=RequestMethod.GET)
	public Contact contactById(@PathVariable String id) {
		System.out.println(ctx.getId());
		logger.info("SpringContextId:"+ ctx.getId());
		Contact theContact =   contactRepository.findOne(Integer.parseInt(id));
		if (theContact == null) {
			throw new ContactNotFoundException(id);
		}
		Contact theContact2 = contactHandler.findContactById(id);
		//return theContact;
		return theContact2;
	}
	
	@RequestMapping(value="/contact", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Contact> saveContact(@RequestBody Contact contact,UriComponentsBuilder ucb) {
		Contact theContact = contactRepository.save(contact);
		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path("/contact/").
							path(String.valueOf(theContact.getId())).build().toUri();
		headers.setLocation(locationUri);
		ResponseEntity<Contact> responseEntity = new ResponseEntity<Contact>(theContact,headers,HttpStatus.CREATED);
		return responseEntity;
	}
	
	//@ExceptionHandler(ContactNotFoundException.class)
	//@ResponseStatus(HttpStatus.NOT_FOUND)
	//public Error contactNotFound(ContactNotFoundException e) {
	//	String contactId = e.getContactId();
	//	return new Error("Contact [" + contactId + "] not found");
	//}

}
