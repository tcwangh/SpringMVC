package idv.tim.springrest.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import idv.tim.springrest.Exception.ContactNotFoundException;
import idv.tim.springrest.model.Contact;
import idv.tim.springrest.persistence.ContactRepository;


@Component
public class ContactHandler {
	private static final Logger logger = LoggerFactory.getLogger(ContactHandler.class);
	
	@Autowired
    private ApplicationContext ctx;
	
	public Contact findContactById(String id) {
		Object obj = ctx.getBean("contactRepository");
		logger.info("ApplicationContext id is " + ctx.getId());
		logger.info("contactRepository object is " + obj);
		ContactRepository contactRepository = null;
		if (obj instanceof ContactRepository) {
			contactRepository = (ContactRepository)obj;
		}
		Contact theContact =   contactRepository.findOne(Integer.parseInt(id));
		if (theContact == null) {
			throw new ContactNotFoundException(id);
		}
		logger.info(theContact.toString());
		return theContact;
	}

}
