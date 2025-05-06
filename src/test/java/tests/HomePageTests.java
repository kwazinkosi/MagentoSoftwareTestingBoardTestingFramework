package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.LandingPage;
import tests.base.BaseTest;

public class HomePageTests extends BaseTest {
	
	@BeforeMethod
	public void setup() {
		
	}
	
	@Test(priority = 0, enabled = true, description = "Verify that the Home page is displayed correctly")
	public void testHomePageDisplayed() {
		
		logStep("verifying that the Home page is displayed correctly.");
		Assert.assertTrue(homePage.isPageDisplayed(), "Home page is not displayed.");
		logStep("Home page is displayed correctly.");
	}

	
	@Test(priority = 1, enabled = true, description = "Verify that the user can click on a menu item and navigate to the correct page")
	public void testClickMenuItem() {
		
		String menuItem = "My Orders";
		logStep("Clicking on the menu item: " + menuItem);
		homePage.clickMenuItem(menuItem);
		Assert.assertEquals(homePage.getCurrentMenuItemText(), menuItem, "Menu item text does not match.");
		logStep("Clicked on the menu item: " + menuItem);
	}
	
	@Test(priority = 2, enabled = true, description = "Verify that the order count is displayed correctly")
	public void testOrderCountDisplayed() {

		logStep("Verifying that the order count is displayed correctly.");
		// returns "1 Item"
		int orderCount = Integer.parseInt(homePage.getOrderCount().split(" ")[0]);
		Assert.assertNotNull(orderCount, "Order count is not displayed.");
		Assert.assertTrue(orderCount > 0, "Order count is not greater than 0.");
		logStep("Order count is displayed correctly: " + orderCount);
	}
	
	@Test(priority = 3, enabled = true, description = "Verify that the user can log out successfully")
	public void testLogout() {
        
        logStep("Logging out of the application.");
        LandingPage landingPage = homePage.signOut();
        Assert.assertTrue(landingPage.isLandingPageDisplayed(), "Home page is not displayed after logout.");
        logStep("Logged out of the application successfully.");
    }
	
}		