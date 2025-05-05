package utils.dataproviders.models;

import utils.dataproviders.interfaces.TestDataModel;

public class NavbarTestData implements TestDataModel {
    
	private String testCaseId;
	private String mainMenu;
    private String subMenu;  
    private String subSubMenu;
    private String expectedUrl; 
	
    @Override
	public String getTestCaseId() {
		
		return testCaseId;
	}

	@Override
	public String getExpectedResult() {
		
		return expectedUrl;
	}
	
	// Fluent setters and getters
    public NavbarTestData setTestCaseId(String testCaseId) {
        
    	this.testCaseId = testCaseId;
        return this;
    }

	public String getMainMenu() {
		return mainMenu;
	}

	public NavbarTestData setMainMenu(String mainMenu) {
		this.mainMenu = mainMenu;
		return this;
	}

	public String getSubMenu() {
		return subMenu;
	}

	public NavbarTestData setSubMenu(String subMenu) {
		this.subMenu = subMenu;
		return this;
	}

	public String getSubSubMenu() {
		return subSubMenu;
	}

	public NavbarTestData setSubSubMenu(String subSubMenu) {
		this.subSubMenu = subSubMenu;
		return this;
	}

	public String getExpectedUrl() {
		return expectedUrl;
	}

	public NavbarTestData setExpectedUrl(String expectedUrl) {
		this.expectedUrl = expectedUrl;
		return this;
	}
	
}
