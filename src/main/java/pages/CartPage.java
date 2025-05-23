package pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import constants.WaitTime;

public class CartPage extends BasePage {

	@FindBy(css = ".cart-container")
	private WebElement cartContainer;

	@FindBy(xpath = ".//table[@id ='shopping-cart-table']//tbody")
	private List<WebElement> cartItems;

	@FindBy(xpath = "//table[@id ='shopping-cart-table']//tbody//strong[@class ='product-item-name']")
	private List<WebElement> productNames;

	@FindBy(xpath = "//button[@data-role='proceed-to-checkout']")
	private WebElement checkoutButton;

	@FindBy(xpath = "//table[@id ='shopping-cart-table']//tbody//a[@title ='Remove item']")
	private List<WebElement> removeButtons;

	public CartPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean containsProduct(String productName) {

		return productNames.stream().map(WebElement::getText).collect(Collectors.toList()).contains(productName);
	}

	public int getCartItemCount() {

		try {
			WebElement orderCountt = driver
					.findElement(By.xpath("//span[@class ='counter qty']//span[@class='counter-number']"));
			if (orderCountt.isDisplayed()) {
				return Integer.parseInt(orderCountt.getText());
			} else {
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
	}

	public CartPage removeProduct(String productName) {

		try {// cartItems is a list of WebElements, so we need to find the index of the
				// product name in the list
			int index = productNames.stream().filter(p -> p.getText().equalsIgnoreCase(productName))
					.map(productNames::indexOf).findFirst()
					.orElseThrow(() -> new RuntimeException("Product not found in cart: " + productName));

			WebElement removeButton = removeButtons.get(index);
			customWait.waitForVisibility(removeButton);
			clickElement(removeButton);
			customWait.waitForPageLoad(WaitTime.SLOW);
			System.out.println("Product removed from cart: " + productName);
			cartItems.remove(index); // Remove from list to avoid stale element reference
			return this;

		} catch (Exception e) {
			System.out.println("Product not found in cart: " + productName);
			return this;
		}
	}

	public BasePage proceedToCheckout() {

		try {
			scrollToElement(checkoutButton);
			customWait.waitForVisibility(checkoutButton);
			if (!checkoutButton.isDisplayed()) {
				return this;
			}
			clickElement(checkoutButton);
			return new CheckoutPage(driver);
		} catch (Exception e) {
			throw new RuntimeException("Checkout button not found");
		}
	}

	public CartPage clearCart() {
		if (isCartEmpty()) {
			System.out.println("Cart is already empty");
			return this;
		}
		try {
			while (true) {
				List<WebElement> removeButtons = driver
						.findElements(By.xpath("//div[@class ='actions-toolbar']//a[@title ='Remove item']"));
				if (removeButtons.isEmpty()) {
					break; // Exit the loop if no more remove buttons are found
				}
				WebElement removeButton = removeButtons.get(0); // Always click the first button
				customWait.waitForVisibility(removeButton);
				clickElement(removeButton);
				customWait.waitForPageLoad(WaitTime.NORMAL); // Wait for the cart to update
			}
			return this;
		} catch (Exception e) {
			System.out.println("Error clearing cart: " + e.getMessage());
			return this;
		}
	}

	public boolean isCartEmpty() {
		return getCartItemCount() == 0;
	}
}