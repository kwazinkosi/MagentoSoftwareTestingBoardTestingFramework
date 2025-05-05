package utils.dataproviders.models;

import utils.dataproviders.interfaces.TestDataModel;

public class SignUpTestData implements TestDataModel {
    
	private String testCaseId; 
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passwordConfirm;
    private String expectedResult;
   
    @Override
    public String getTestCaseId() {
        return testCaseId;
    }
    
    @Override
    public String getExpectedResult() {
		return expectedResult;
        
    }
    
	public String getFirstName() {
		return firstName;
	}
	
	public SignUpTestData setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public SignUpTestData setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	
	public SignUpTestData setTestCaseId(String testCaseId) {
		
		this.testCaseId = testCaseId;
		return this;
	}
	
	public SignUpTestData setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public SignUpTestData setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public SignUpTestData setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public SignUpTestData setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
		return this;
	}
}
