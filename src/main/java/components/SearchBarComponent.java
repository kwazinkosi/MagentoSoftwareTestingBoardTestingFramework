package components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import constants.WaitTime;
import pages.SearchPage;
import pages.base.CustomWait;



public class SearchBarComponent {
	
    @FindBy(xpath = "//input[@id='search']")
    private WebElement searchBar;

    @FindBy(xpath = "//button[@title='Search']")
    private WebElement searchButton;
    
    private CustomWait customWait;
    private WebDriver driver;
    public SearchBarComponent(WebDriver driver) {
    	
    	this.driver = driver;
    	this.customWait = new CustomWait(driver);
        PageFactory.initElements(driver, this);
    }

    private void clickOnSearchButton(String socialName) {
       
    	searchButton.click();
    }
    
	public SearchPage searchItem(String text) {
		try {
			searchBar.sendKeys(text);
			clickOnSearchButton(text);
			// Wait for the search results to load
			 customWait.waitForPageLoad(WaitTime.FAST);
			 return new SearchPage(driver);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
    public boolean isDisplayed() {
    	
        return searchBar.isDisplayed();
    }
}