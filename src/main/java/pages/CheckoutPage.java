package pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import constants.WaitTime;

public class CheckoutPage extends BasePage {

	@FindBy(className = "opc-block-summary")
	private WebElement summaryBlock;

	@FindBy(xpath = "//div[@class='shipping-address-item selected-item']")
	private WebElement shippingAddressBlock;

	@FindBy(xpath = "//button[@class='button action continue primary']")
	private WebElement continueButton;

	public CheckoutPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageDisplayed() {
		return customWait.until(
				d -> d.getTitle().contains("Checkout") && isElementDisplayed(summaryBlock)
						&& isElementDisplayed(shippingAddressBlock) && isElementDisplayed(continueButton),
				WaitTime.NORMAL);
	}


	public BasePage continueToOverview() {

		try {
			clickElement(continueButton);
			customWait.until(ExpectedConditions.urlContains("checkout/#payment"), WaitTime.NORMAL);
			return new CheckoutOverviewPage(driver);
		} catch (TimeoutException e) {
			return null;
		}
		catch (Exception e) {
			throw new RuntimeException("An unexpected error occurred: " + e.getMessage());
		}
	}


}