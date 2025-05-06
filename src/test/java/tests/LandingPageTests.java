package tests;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import components.NavbarComponent;
import constants.WaitTime;
import pages.CreateAccountPage;
import pages.LandingPage;
import pages.SearchPage;
import pages.SignInPage;
import tests.base.BaseTest;
import utils.LoggingManager;
import utils.dataproviders.MagentoDataProviders;
import utils.dataproviders.models.NavbarTestData;
import utils.dataproviders.models.SearchTestData;

public class LandingPageTests extends BaseTest {

	private WebDriver driver;

	private LandingPage landingPage;

	@BeforeMethod
	@Override
	@Parameters("browser") // Parameter from XML
	public void setup(@Optional String browser) {

		if (browser != null && !browser.isEmpty()) {
			browserName = browser;
		} else {
//		    browserName = ConfigReader.getProperty("browser"); // Replace with config method if needed
		}
		System.out.println("Running tests on browser: " + browserName);
		LoggingManager.info("Running tests on browser: " + browserName);

		driver = driverFactory.initDriver(browserName, browserMode);
		logStep("Driver initialized for test setup.");
		logStep("Navigating to the landing page.");
		landingPage = new LandingPage(driver);
		landingPage.navigateTo(baseUrl);
	}

	@Test(priority = 0, enabled = true, description = "Verify that all navbar component links function correctly", dataProvider = "navbarData", dataProviderClass = MagentoDataProviders.class)
	public void testNavbarComponents(NavbarTestData data) {
		
		logStep("=========================Starting test case: " + data.getTestCaseId()+"=========================");
		LoggingManager.info("=========================Starting test case: " + data.getTestCaseId()+"=========================");
		// Navigate to the landing page before each test
		landingPage.navigateTo(baseUrl);

		logStep("Hovering over the main menu: " + data.getMainMenu());
		NavbarComponent navbar = landingPage.getNavbar();
		navbar.hoverOverNavItem(data.getMainMenu());

		if (data.getSubMenu() != null && !data.getSubMenu().isEmpty()) {
			
			List<WebElement> subMenuItems = navbar.getSubMenuItemsByNavName(data.getMainMenu());
			WebElement subMenu = subMenuItems.stream()
					.filter(item -> item.getText().trim().equalsIgnoreCase(data.getSubMenu())).findFirst()
					.orElseThrow(() -> new RuntimeException("Sub-menu not found: " + data.getSubMenu()));

			logStep("Hovering over the sub-menu: " + data.getSubMenu());
			LoggingManager.info("Hovering over the sub-menu: " + data.getSubMenu());
			new Actions(driver).moveToElement(subMenu).perform();

			if (data.getSubSubMenu() != null && !data.getSubSubMenu().isEmpty()) {

				logStep("Clicking on the sub-sub-menu: " + data.getSubSubMenu());
				LoggingManager.info("Clicking on the sub-sub-menu: " + data.getSubSubMenu());
				List<WebElement> subSubMenuItems = navbar.getSubSubMenuItemsBySubName(data.getSubMenu());
				WebElement subSubMenu = subSubMenuItems
						.stream().filter(item -> item.getText().trim().equalsIgnoreCase(data.getSubSubMenu()))
						.findFirst()
						.orElseThrow(() -> new RuntimeException("Sub-sub-menu not found: " + data.getSubSubMenu()));

				subSubMenu.click();
				// Wait for page to load after navigation
				landingPage.getCustomWait().until(ExpectedConditions.urlContains(data.getExpectedUrl().substring(1)),
						WaitTime.NORMAL);

				logStep("Verifying the URL after navigation.");
				
				Assert.assertTrue(driver.getCurrentUrl().endsWith(data.getExpectedUrl()),
						"URL does not match the expected value. Expected: " + data.getExpectedUrl() + ", Actual: "
								+ driver.getCurrentUrl());
				LoggingManager.info("Test case " + data.getTestCaseId() + ": completed successfully.");
				logStep("Test case " + data.getTestCaseId() + ": completed successfully.");
			}
			else
			{
				// For sub-menu items without sub-submenus
				logStep("Clicking on the sub-menu: " + data.getSubMenu());
				LoggingManager.info("Clicking on the sub-menu: " + data.getSubMenu());
                // Click on the sub-menu
				subMenu.click();
				// Wait for page to load after navigation
				landingPage.getCustomWait().until(ExpectedConditions.urlContains(data.getExpectedUrl().substring(1)),
						WaitTime.NORMAL);

				logStep("Verifying the URL after navigation.");
				Assert.assertTrue(driver.getCurrentUrl().endsWith(data.getExpectedUrl()),
						"URL does not match the expected value. Expected: " + data.getExpectedUrl() + ", Actual: "
								+ driver.getCurrentUrl());
				LoggingManager.info("Test case " + data.getTestCaseId() + ": completed successfully.");
				logStep("Test case " + data.getTestCaseId() + ": completed successfully.");
			}
		}
		else {
			// For main menu items without submenus (like What's New, Sale)
			logStep("Clicking on main menu item without submenu");
			LoggingManager.info("Clicking on main menu item without submenu");
			navbar.clickNavItem(data.getMainMenu());
			// Wait for page to load after navigation
			landingPage.getCustomWait().until(ExpectedConditions.urlContains(data.getExpectedUrl().substring(1)),
					WaitTime.NORMAL);
			logStep("Verifying the URL after navigation.");
			Assert.assertTrue(driver.getCurrentUrl().endsWith(data.getExpectedUrl()),
					"URL does not match the expected value. Expected: " + data.getExpectedUrl() + ", Actual: "
							+ driver.getCurrentUrl());
			LoggingManager.info("Test case " + data.getTestCaseId() + ": completed successfully.");
			logStep("Test case " + data.getTestCaseId() + ": completed successfully.");
		}
		
	}

	@Test(priority = 1, description = "Verify that the logo is displayed on the landing page" )
	public void testLogoIsDisplayed() {

		logStep("Verifying that the logo is displayed.");
		LoggingManager.info("Verifying that the logo is displayed.");
		Assert.assertTrue(landingPage.isLogoDisplayed(), "Logo is not displayed on the landing page.");
		logStep("Logo is displayed successfully.");
		LoggingManager.info("Logo is displayed successfully.");
	}

	@Test(priority = 2, description = "Verify that clicking the Sign In link navigates to the login page")
	public void testSignInLinkNavigation() {

		logStep("Clicking the Sign In link.");
		LoggingManager.info("Clicking the Sign In link.");
		SignInPage signInPage = landingPage.clickSignIn();
		logStep("Verifying that the Sign In page is displayed.");
		Assert.assertTrue(signInPage.isPageDisplayed(), "Sign In page is not displayed.");
		logStep("Sign In page is displayed successfully.");
		LoggingManager.info("Sign In page is displayed successfully.");
	}

	@Test(priority = 3, description = "Verify that clicking the Create Account link navigates to the account creation page")
	public void testCreateAccountLinkNavigation() {

		logStep("Clicking the Create Account link.");
		LoggingManager.info("Clicking the Create Account link.");
		CreateAccountPage createAccountPage = landingPage.clickCreateAccount();

		logStep("Verifying that the Create Account page is displayed.");
		Assert.assertTrue(createAccountPage.isPageDisplayed(), "Create Account page is not displayed.");
		logStep("Create Account page is displayed successfully.");
		LoggingManager.info("Create Account page is displayed successfully.");
	}

	@Test(priority = 4, description = "Verify that the search functionality works as expected", dataProvider = "searchData", dataProviderClass = MagentoDataProviders.class)
	public void testSearchFunctionality(SearchTestData data) {

		logStep("=========================testSearchFunctionality()::Starting test case: " + data.getTestCaseId()+"=========================");
		LoggingManager.info("=========================testSearchFunctionality():: Starting test case: " + data.getTestCaseId()+"=========================");
		logStep("Searching for the product: " + data.getSearchTerm());
		SearchPage searchPage = landingPage.searchForProduct(data.getSearchTerm());
		
		logStep("Verifying the search results.");
		if (data.getExpectedResult().equalsIgnoreCase("No results")) {
			
			Assert.assertTrue(searchPage.isNoResultsMessageDisplayed(), "No results message is not displayed.");
			logStep("No results message is displayed successfully.");
			LoggingManager.info("No results message is displayed successfully.");
		} else if (data.getExpectedResult().equalsIgnoreCase("Success")) {

			Assert.assertTrue(searchPage.isProductDisplayed(data.getSearchTerm()),
					"Product is not displayed in search results.");
			logStep("Product is displayed in search results successfully.");
			LoggingManager.info("Product is displayed in search results successfully.");
		} else {
			Assert.fail("Invalid expected result: " + data.getExpectedResult());
		}
		logStep("testSearchFunctionality():: Test case " + data.getTestCaseId() + ": completed successfully.");
		LoggingManager.info("testSearchFunctionality():: Test case " + data.getTestCaseId() + ": completed successfully.");
	}
}
