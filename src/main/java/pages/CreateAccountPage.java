package pages;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import constants.WaitTime;

public class CreateAccountPage extends BasePage {

	@FindBy(id = "firstname")
	private WebElement firstNameInput;

	@FindBy(id = "lastname")
	private WebElement lastNameInput;

	@FindBy(id = "email_address")
	private WebElement emailInput;

	@FindBy(id = "password")
	private WebElement passwordInput;

	@FindBy(id = "password-confirmation")
	private WebElement confirmPasswordInput;

	@FindBy(xpath = "//button[@title='Create an Account']")
	private WebElement createAccountButton;

	final String PAGE_TITLE = "Create New Customer Account";

	public CreateAccountPage(WebDriver driver) {
		super(driver);
	}

	public BasePage createAccount(String firstName, String lastName, String email, String password,
			String confirmPassword) {

		enterFirstName(firstName);
		enterLastName(lastName);
		enterEmail(email);
		enterPassword(password);
		enterConfirmPassword(confirmPassword);
		return clickCreateAccountButton();
	}

	public CreateAccountPage enterFirstName(String firstName) {

		sendKeys(firstNameInput, firstName);
		return this;
	}

	public CreateAccountPage enterLastName(String lastName) {
		sendKeys(lastNameInput, lastName);
		return this;
	}

	public CreateAccountPage enterEmail(String email) {

		sendKeys(emailInput, email);
		return this;
	}

	public CreateAccountPage enterPassword(String password) {

		sendKeys(passwordInput, password);
		return this;
	}

	public CreateAccountPage enterConfirmPassword(String confirmPassword) {

		sendKeys(confirmPasswordInput, confirmPassword);
		return this;
	}

	public BasePage clickCreateAccountButton() {

		try {
			clickElement(createAccountButton);
			List<WebElement> errorMessages = driver.findElements(By.xpath("//div[@class = 'mage-error']"));
			if (errorMessages.size() > 0) {
				System.out.println("Error messages found: ");
				for (WebElement errorMessage : errorMessages) {
					System.out.println(errorMessage.getText());
				}
				return this;
			}
			customWait.until(d -> d.getCurrentUrl().contains("customer/account/"), WaitTime.FAST);

			return new HomePage(driver);
		} catch (TimeoutException e) {
			return this;
		} catch (Exception e) {
			System.out.println("Error clicking Create Account button: " + e.getMessage());
		}
		return this;
	}

	public Set<String> getErrorMessages() {

		try {
			
			List<WebElement> errorMessages = driver.findElements(By.xpath("//div[@class = 'mage-error']"));
			return errorMessages.stream().map(WebElement::getText).collect(Collectors.toSet());
		} catch (TimeoutException e) {
			return null;
		}
	}

	@Override
	public boolean isPageDisplayed() {
		return customWait.until(
				d -> d.getCurrentUrl().contains("account/create/") && d.getTitle().equalsIgnoreCase(PAGE_TITLE),
				WaitTime.NORMAL);
	}
}