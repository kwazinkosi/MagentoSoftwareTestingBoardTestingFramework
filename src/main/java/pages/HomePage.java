package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import constants.WaitTime;


public class HomePage extends BasePage
{

	@FindBy(xpath = "//div[@id='block-collapsible-nav']//li")
	private List<WebElement> menuItems; // 10 sidenav items
	
	@FindBy(xpath = "//li[@class='nav item current']//strong")
	private WebElement currentMenuItem; // current sidenav item
	
	@FindBy(xpath = "//span[@class='toolbar-number']")
	private WebElement orderCount; // order count 
	
	@FindBy(xpath = "//div[@class='panel header']//span[@class='logged-in']")
	private WebElement loggedInUser; // logged in user name
	
	
	public HomePage(WebDriver driver) {
        super(driver);
    }

	public List<WebElement> getMenuItems() {
		return menuItems;
	}
	
	public WebElement getCurrentMenuItem() {
		return currentMenuItem;
	}
	
	public String getCurrentMenuItemText() {
		return getText(currentMenuItem);
	}
	
	public HomePage clickMenuItem(String menuItem) {
		
		// First go to account
		viewCustomerMenu("My Account");
		// Validate the menu item
		if (menuItem == null || menuItem.isEmpty()) {
			throw new IllegalArgumentException("Menu item cannot be null or empty");
		}
		for (WebElement item : menuItems) {
			if (item.getText().equalsIgnoreCase(menuItem)) {
				clickElement(item);
				break;
			}
		}
		return this;
	}
	
	public void viewCustomerMenu(String menuItem) {
		
		try {
			WebElement loggedInUserr = driver.findElement(By.xpath("//div[@class='panel header']//span[@class='logged-in'][contains(normalize-space(), 'Welcome')]"));
			clickElement(loggedInUserr);
			List<WebElement> menuItems = driver.findElements(By.xpath("//div[@aria-hidden='false']//ul[@class='header links']//li//a"));
			
			menuItems.stream()
			.filter(item -> item.getText().equalsIgnoreCase(menuItem))
			.findFirst()
			.ifPresent(WebElement::click);
			
		} catch (Exception e) {
			System.out.println("Account menu not found: " + e.getMessage());
		}
		
	}
	
	public String getOrderCount() {
		
		if (orderCount.isDisplayed()) {
			return orderCount.getText();
		} else {
			return "0";
		}
	}
	
	public LandingPage logout() {
		
		try {
			
			clickElement(loggedInUser);
			viewCustomerMenu("Sign Out");
			customWait.waitForPageLoad(WaitTime.NORMAL);
			return new LandingPage(driver);
		} catch (Exception e) {
			System.out.println("Logout menu not found: " + e.getMessage());
			return null;
		}
	}
	public String getLoggedInUser() {
		WebElement loggedInUserr = driver.findElement(By.xpath("//div[@class='panel header']//span[@class='logged-in'][contains(normalize-space(), 'Welcome')]"));
		return getText(loggedInUserr);
	}
	
	@Override
	public boolean isPageDisplayed() {
		return isElementDisplayed(loggedInUser);
	}

	public HomePage navigateToHomePage() {
		browserActions.navigateTo("index.html");
		return this;
	}
}
