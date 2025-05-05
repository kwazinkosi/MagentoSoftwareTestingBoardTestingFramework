package pages;

import constants.WaitTime;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class LandingPage extends BasePage {

	// Core elements
	@FindBy(xpath = "//div[@class='panel header']//a[contains(text(),'Sign In')]")
	private WebElement signInLink;
	
	@FindBy(xpath = "//div[@class='panel header']//a[normalize-space()='Create an Account']")
	private WebElement createAccountLink;
	
	@FindBy(id = "search")
	private WebElement searchBar;
	
	@FindBy(xpath = "//a[@class='action showcart']")
	private WebElement cartIcon;
	
	@FindBy(xpath = "//a[@aria-label='store logo']//img")
	private WebElement logo;
	

	public LandingPage(WebDriver driver) {
		
		super(driver);
	}

	// Core Page Actions
	
	
	public SearchPage searchForProduct(String productName) {
		
		try {
			sendKeys(searchBar, productName);
			searchBar.submit();
			customWait.waitForPageLoad(WaitTime.NORMAL);
			return new SearchPage(driver);
		} catch (TimeoutException e) {
			System.out.println("Search button not found or not clickable.");
		}
		return null;
	}
	
	public boolean isLogoDisplayed() {
		return isElementDisplayed(logo);
	}
	
	
	public boolean isLandingPageDisplayed() {
		
		return customWait.until(d -> d.getTitle().contains("Home Page") && isElementDisplayed(logo) && 
				isElementDisplayed(signInLink) && isElementDisplayed(createAccountLink), WaitTime.NORMAL);
	}
	public SignInPage clickSignIn() {
		
		clickElement(signInLink);
		customWait.until(d -> d.getCurrentUrl().contains("login"), WaitTime.NORMAL);
		return new SignInPage(driver);
	}
	
	public CreateAccountPage clickCreateAccount() {
		
		clickElement(createAccountLink);
		customWait.until(d -> d.getCurrentUrl().contains("create"), WaitTime.NORMAL);
		return new CreateAccountPage(driver);
	}
	
	public void clickCartIcon() {
		
		clickElement(cartIcon);
	}

}