package utils.dataproviders.models;


import utils.dataproviders.interfaces.TestDataModel;

public class SearchTestData implements TestDataModel {
    
	private String testCaseId; 
    private String searchTerm;
    private String expectedResult;

   
    @Override
    public String getTestCaseId() {
        return testCaseId;
    }

    public SearchTestData setTestCaseId(String testCaseId) {
        
    	this.testCaseId = testCaseId;
        return this;
    }

    @Override
    public String getExpectedResult() {
		return expectedResult;
        
    }

	public String getSearchTerm() {
		return searchTerm;
	}

	public SearchTestData setSearchTerm(String searchTerm) {
		
		this.searchTerm = searchTerm;
        return this;
	}

	public SearchTestData setExpectedResult(String expectedResult) {
		
		this.expectedResult = expectedResult;
        return this;
	}
    
}
