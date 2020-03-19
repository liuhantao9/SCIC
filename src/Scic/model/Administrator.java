
package Scic.model;

public class Administrator extends Users {
	
	public Administrator(String userName, String password, String firstName, String lastName, String email, String phone) {
		super( userName,  password,  firstName,  lastName,  email,  phone);
	}
	
	public Administrator(String userName) {
		super(userName);
	}
}
