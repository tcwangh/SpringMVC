package idv.tim.springrest.Exception;

public class ContactNotFoundException extends RuntimeException{
	
	private String contactId;

	public ContactNotFoundException(String contactId) {
		super();
		this.contactId = contactId;
	}

	public String getContactId() {
		return contactId;
	}
	
	

}
