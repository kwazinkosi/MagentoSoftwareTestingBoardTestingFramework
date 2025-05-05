package components;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import constants.WaitTime;
import pages.base.CustomWait;

public class NavbarComponent {

//	@FindBy(xpath = "//li[contains(@class, 'level0') and contains(@class, 'level-top')]")
//	private List<WebElement> navItems;
	
//	@FindBy(xpath = ".//li[contains(@class, 'level1')]//a//span")
//	private List<WebElement> subMenuItems;
//	
//	@FindBy(xpath = ".//ul[contains(@class, 'level2')]")
//	private List<WebElement> subSubMenuItems;

	private final WebDriver driver;
	private final CustomWait customWait;

	private static final String LEVEL0_UL = "//li[contains(@class, 'level0') and contains(@class, 'level-top')]";
	private static final String LEVEL1_UL = ".//li[contains(@class, 'level1')]//a//span";
	private static final String LEVEL2_UL = ".//li[contains(@class, 'level2')]//a//span";

	public NavbarComponent(WebDriver driver) {
		this.driver = driver;
		this.customWait = new CustomWait(driver);
		PageFactory.initElements(driver, this);
	}

	public WebElement getNavItemByName(String name) {
		
		try {
			customWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(LEVEL0_UL)), WaitTime.NORMAL);
			List<WebElement> navItems = driver.findElements(By.xpath(LEVEL0_UL));
			return navItems.stream().filter(item -> item.getText().trim().equalsIgnoreCase(name)).findFirst()
					.orElseThrow(() -> new RuntimeException("No such menu item: " + name));
		} catch (Exception e) {
			throw new RuntimeException("No such menu item: " + name);
		}
	}

	public void hoverOverNavItem(String name) {

		WebElement item = getNavItemByName(name);
		new Actions(driver).moveToElement(item).perform();
	}

	public List<WebElement> getSubMenuItemsByNavName(String name) {

		try {
			customWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(LEVEL1_UL)), WaitTime.NORMAL);
			List<WebElement> subMenuItems = driver.findElements(By.xpath(LEVEL1_UL));
			subMenuItems.removeIf(item -> item.getText().trim().isEmpty());
			return subMenuItems;
		} catch (Exception e) {
			throw new RuntimeException("No such submenu item: " + name);
		}
	}

	public List<WebElement> getSubSubMenuItemsBySubName(String subMenuName) {
		

		try {
			customWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(LEVEL2_UL)), WaitTime.NORMAL);
			
			List<WebElement> subMenuItems = driver.findElements(By.xpath(LEVEL2_UL));
			return subMenuItems;
		} catch (Exception e) {
			throw new RuntimeException("No such sub-submenu item: " + subMenuName);
		}
	}

	public void clickNavItem(String name) {

		WebElement item = getNavItemByName(name);
		customWait.until(ExpectedConditions.elementToBeClickable(item), WaitTime.NORMAL);
		item.click();
	}

	
}
