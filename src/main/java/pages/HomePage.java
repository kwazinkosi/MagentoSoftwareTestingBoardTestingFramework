package pages;

import java.util.List;

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
	
	public String getOrderCount() {
		
		if (orderCount.isDisplayed()) {
			return orderCount.getText();
		} else {
			return "0";
		}
	}
	
	@Override
	public boolean isPageDisplayed() {
		return customWait.until(d -> d.getCurrentUrl().contains("customer/account/index/"), WaitTime.NORMAL);
	}

	public HomePage navigateToHomePage() {
		browserActions.navigateTo("index.html");
		return this;
	}
}
