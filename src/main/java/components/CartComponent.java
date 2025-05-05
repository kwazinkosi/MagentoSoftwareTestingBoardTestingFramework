package components;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import constants.WaitTime;
import pages.CartPage;
import pages.base.CustomWait;

public class CartComponent {

	private final WebDriver driver;
	private final CustomWait customWait;

	@FindBy(id = "//a[@class='action showcart']")
	private WebElement cartIcon;

	@FindBy(xpath = "//span[@class='counter-number']")
	private WebElement cartBadge;
	
	

	public CartComponent(WebDriver driver) {
		this.driver = driver;
		this.customWait = new CustomWait(driver);
		PageFactory.initElements(driver, this); // Add this line
	}

	public CartPage navigateToCart() {

		customWait.until(ExpectedConditions.elementToBeClickable(cartIcon), WaitTime.NORMAL);

		try {
			cartIcon.click();
			WebElement goToCartButton = driver.findElement(By.xpath("//span[normalize-space()='View and Edit Cart']"));
			if (goToCartButton.isDisplayed()) {
				goToCartButton.click();
			}
			customWait.waitForPageLoad(WaitTime.NORMAL);
			return new CartPage(driver);
		} catch (NoSuchElementException e) {
			throw new RuntimeException("Cart icon not found");
		}
	}

	public int getCurrentCartCount() {

		try {
			return Integer.parseInt(cartBadge.getText());
		} catch (NoSuchElementException e) {

		}
		return 0;
	}

	public boolean isBadgeDisplayed() {
		return cartIcon.isDisplayed();
	}
}