
package Scic.model;

public class CommonUsers extends Users {
	
	public CommonUsers(String userName, String password, String firstName, String lastName, String email, String phone) {
		super( userName,  password,  firstName,  lastName,  email,  phone);
	}
	
	public CommonUsers(String userName) {
		super(userName);
	}
}
