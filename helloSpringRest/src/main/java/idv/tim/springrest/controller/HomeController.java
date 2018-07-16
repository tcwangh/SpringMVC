package idv.tim.springrest.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import idv.tim.springrest.model.Contact;
import idv.tim.springrest.persistence.ContactRepository;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ContactRepository contactRepository;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale,  @RequestParam(value = "search", required = false) String contactId, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		logger.info("search id is " + contactId );
		List<Contact> listContact = contactRepository.findAll();
		logger.info("Contact list size is " + listContact.size() );
		
		if (contactId!=null) {
			List<Contact> theContact = contactRepository.findById(Integer.parseInt(contactId));
			model.addAttribute("search", theContact );
		}
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("contacts", listContact );
		return "home";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String addContact(Locale locale,@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("address") String address,@RequestParam("telephone") String telephone,
			Model model) {
		System.out.println("Add Contact " + name);
		Contact theContact = new Contact(name,email,address,telephone);
		contactRepository.save(theContact);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteContact(@RequestParam("id") int id, Model model) {
		contactRepository.delete(id);
		return "redirect:/";
	}
	
	@RequestMapping(value="/contacts", method=RequestMethod.GET,produces="application/json")
	public @ResponseBody List<Contact> listContact() {
		return contactRepository.findAll();
	}
	
}
