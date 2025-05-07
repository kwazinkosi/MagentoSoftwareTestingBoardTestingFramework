package tests.pages;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.BasePage;
import pages.CreateAccountPage;
import pages.HomePage;
import pages.LandingPage;
import pages.SignInPage;
import tests.base.BaseTest;
import utils.LoggingManager;
import utils.dataproviders.MagentoDataProviders;
import utils.dataproviders.models.SignUpTestData;

public class CreateAccountPageTests extends BaseTest {

	private CreateAccountPage createAccountPage;
	private WebDriver driver;
	
	@BeforeMethod
	@Override
	@Parameters("browser") // Parameter from XML
	public void setup(@Optional String browser) {
		// Use the provided browser parameter, otherwise fallback to config file defined browser name
		if (browser != null && !browser.isEmpty()) {
		    browserName = browser;
		} else {
//		    browserName = ConfigReader.getProperty("browser"); 
		}
		System.out.println("Running tests on browser: " + browserName);
		LoggingManager.info("Running tests on browser: " + browserName);
		logStep("Running tests on browser:: " + browserName);
		driver = driverFactory.initDriver(browserName, browserMode);
		LandingPage landingPage = new LandingPage(driver);
		landingPage.navigateTo(baseUrl);
		logStep("Navigating to the account creation page.");
		createAccountPage =landingPage.clickCreateAccount();
		
	}

	@Test(priority = 0, enabled = true, description = "Verify that the Create Account page is displayed correctly")
	public void testCreateAccountPageDisplayed() {
		
		logStep("verifying that the Create Account page is displayed correctly.");
		LoggingManager.info("verifying that the Create Account page is displayed correctly.");
		Assert.assertTrue(createAccountPage.isPageDisplayed(), "Create Account page is not displayed.");
		logStep("Create Account page is displayed correctly.");
	}
	
	@Test(priority = 1, enabled = true, description = "Verify that the user cannot create an account with invalid details",
			dataProvider = "signUpData",  dataProviderClass = MagentoDataProviders.class)
	public void testCreateAccountWithInvalidDetails(SignUpTestData data) {
		
		logStep("=========================Starting test case: " + data.getTestCaseId()+"=========================");
		LoggingManager.info("=========================Starting test case: " + data.getTestCaseId()+"=========================");
		logStep("Entering invalid details for account creation.");
		LoggingManager.info("Entering invalid details for account creation.");
		logStep("First name: " + data.getFirstName() + ", Last name: " + data.getLastName() + ", Email: " + data.getEmail() + ", Password: " + data.getPassword());
		LoggingManager.info("First name: " + data.getFirstName() + ", Last name: " + data.getLastName() + ", Email: " + data.getEmail() + ", Password: " + data.getPassword());
		
		BasePage currentPage = createAccountPage.createAccount(data.getFirstName(), data.getLastName(), data.getEmail(), data.getPassword(), data.getPasswordConfirm());

		if (currentPage instanceof SignInPage) {
			
			logStep("User is redirected to the Sign In page.");
			LoggingManager.info("User is redirected to the Sign In page.");
			Assert.assertTrue(currentPage.isPageDisplayed(), "Sign In page is not displayed.");
		} else if (currentPage instanceof CreateAccountPage) {
			
			logStep("User is still on the Create Account page.");
			LoggingManager.info("User is still on the Create Account page.");
			
		    Set<String> errorMessages = createAccountPage.getErrorMessages();
			String expectedMessage = data.getExpectedResult();
			
			boolean isErrorMessageDisplayed = errorMessages.stream()
					.anyMatch(message -> message.contains(expectedMessage));
			
			Assert.assertTrue(isErrorMessageDisplayed, "Expected no error messages, but found: " + errorMessages.toString());
			
		}
		else if (currentPage instanceof HomePage) {
			
			logStep("User is redirected to the Home page.");
			LoggingManager.info("User is redirected to the Home page.");
			Assert.assertTrue(currentPage.isPageDisplayed(), "Home page is not displayed.");
		} else {
			
			logStep("Unexpected page after account creation attempt.");
			LoggingManager.info("Unexpected page after account creation attempt.");
			Assert.fail("Unexpected page after account creation attempt.");
		}
		
		logStep("Test case " + data.getTestCaseId() + " PASSED!");
	    LoggingManager.info("============= "+data.getTestCaseId() + " in LoginPageTest::testLoginFunctionality PASSED! ============");
	}
	
	
}
