package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import constants.WaitTime;

public class SignInPage extends BasePage {

	@FindBy(id = "email")
	private WebElement usernameInput;
	
	@FindBy(id = "pass")
	private WebElement passwordInput;
	
	@FindBy(id = "send2")
	private WebElement loginButton;
	
	@FindBy(xpath = "//a[@class='action create primary']")
	private WebElement createAccountButton;
	
	@FindBy(xpath = "//div[@data-bind='html: $parent.prepareMessageForHtml(message.text)']")
	private WebElement errorMessage;
	
	public SignInPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageDisplayed() {
		
		return customWait.until(d -> d.getTitle().contains("Customer Login") && isElementDisplayed(usernameInput)
				&& isElementDisplayed(passwordInput) && isElementDisplayed(loginButton), WaitTime.NORMAL);
	}

	public BasePage login(String username, String password) {
		
		enterUsername(username);
		enterPassword(password);
		try {
			return clickLoginButton();
		} catch (TimeoutException e) {
			return null;
		}
	}
	
	public SignInPage enterUsername(String username) {
		sendKeys(usernameInput, username);
		return this;
	}

	public SignInPage enterPassword(String password) {
		sendKeys(passwordInput, password);
		return this;
	}

	private BasePage clickLoginButton() {
		try {
			clickElement(loginButton);
			List<WebElement> errorMessages = driver.findElements(
					By.xpath("//div[@class = 'mage-error' or @data-bind='html: $parent.prepareMessageForHtml(message.text)']"));
			if (errorMessages.size() > 0) {
				return this;
			} else {
				return new HomePage(driver);
			}
		} catch (TimeoutException e) {
			return null;
		}
		
	}

	public List<String> getErrorMessages() {
		try {
			List<WebElement> errorMessages = driver.findElements(
					By.xpath("//div[@class = 'mage-error' or @data-bind='html: $parent.prepareMessageForHtml(message.text)']"));
			return errorMessages.stream().map(WebElement::getText).toList();
		} catch (TimeoutException e) {
			return null;
		}
	}
	
	public CreateAccountPage clickCreateAccountButton() {
		try {
			clickElement(createAccountButton);
			customWait.waitForPageLoad(WaitTime.NORMAL);
			return new CreateAccountPage(driver);
		} catch (TimeoutException e) {
			return null;
		}
	}

}
