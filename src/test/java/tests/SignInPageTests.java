package tests;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.List;

import org.testng.Assert;
import pages.SignInPage;
import tests.base.BaseTest;
import utils.LoggingManager;
import utils.dataproviders.MagentoDataProviders;
import utils.dataproviders.models.LoginTestData;
import pages.BasePage;
import pages.CreateAccountPage;
import pages.HomePage;
import pages.LandingPage;

public class SignInPageTests extends BaseTest {

	private SignInPage loginPage;
	private LandingPage landingPage;

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
		landingPage = new LandingPage(driver);
		landingPage.navigateTo(baseUrl);
		loginPage = landingPage.clickSignIn();
	}
	
	@Test(priority = 0, description = "Verify login page is displayed")
	public void testLoginPageDisplayed() {
		
		Assert.assertTrue(loginPage.isPageDisplayed(), "Login page not displayed");
		logStep("Login page is displayed.");
	}
	
	@Test(priority=1, description = "Verify account creation link")
	public void testAccountCreationLink() {
		
		CreateAccountPage createAccountPage = loginPage.clickCreateAccountButton();
		Assert.assertTrue(createAccountPage.isPageDisplayed());	
	}
	
	@Test(priority = 2, description = "Verify login functionality with different credentials", dataProvider = "loginData", dataProviderClass = MagentoDataProviders.class)
	public void testLoginFunctionality(LoginTestData data) {
		
		logStep("=========================Starting test case: " + data.getTestCaseId()+"=========================");
		LoggingManager.info("=========================Starting test case: " + data.getTestCaseId()+"=========================");
		BasePage currentPage = loginPage.login(data.getUsername(), data.getPassword());
		logStep("Checking if login was successful");
		LoggingManager.info("Checking if login was successful");
		if (currentPage != null && currentPage instanceof HomePage) {
			
			Assert.assertTrue(currentPage instanceof HomePage, "Login was not successful");
			Assert.assertTrue(currentPage.isPageDisplayed(), "Login page not displayed");
			logStep("Login was successful.");
			LoggingManager.info("Login was successful.");
		} 
		else if (currentPage != null && currentPage instanceof SignInPage) {

			List<String> errorMessages = loginPage.getErrorMessages();
			String expectedMessage = data.getExpectedResult();
			
			boolean isErrorMessageDisplayed = errorMessages.stream()
					.anyMatch(message -> message.contains(expectedMessage));
			
			logStep("Checking if error message is displayed");
			LoggingManager.info("Checking if error message is displayed");
			
			Assert.assertTrue(currentPage.isPageDisplayed(), "Login page not displayed");
			Assert.assertTrue(isErrorMessageDisplayed, "Error message not displayed");
			
			logStep("Login was not successful.");
			LoggingManager.info("Login was not successful.");
		}
		else {
			logStep("Login failed as expected.");
			LoggingManager.info("Login failed as expected.");
		}
		
	    logStep("Test case " + data.getTestCaseId() + " PASSED!");
	    LoggingManager.info(data.getTestCaseId() + " in LoginPageTest::testLoginFunctionality PASSED!");
	}
	
}