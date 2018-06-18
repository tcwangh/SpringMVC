package idv.tim.mvcprofile.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import idv.tim.mvcprofile.dao.ContactDao;
import idv.tim.mvcprofile.model.Contact;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Value("${spring.datasource.url}")
	private String dataSourceURL;
	
	@Autowired
	private ContactDao contactDAO;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale,  @RequestParam(value = "search", required = false) String contactId, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		logger.info("DataSource URL is " + dataSourceURL );
		logger.info("search id is " + contactId );
		List<Contact> listContact = contactDAO.list();
		logger.info("Contact list size is " + listContact.size() );
		if (contactId!=null) {
			List<Contact> theContact = contactDAO.get(Integer.parseInt(contactId));
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
		logger.info("Add Contact " + name);
		Contact theContact = new Contact(name,email,address,telephone);
		int result = contactDAO.saveOrUpdate(theContact);
		logger.info("Result is " + result);
		return "redirect:/";
	}
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteContact(@RequestParam("id") int id, Model model) {
		contactDAO.delete(id);
		return "redirect:/";
	}
}
