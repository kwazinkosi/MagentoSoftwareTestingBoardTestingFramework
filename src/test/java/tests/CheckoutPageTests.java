package tests;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.BasePage;
import pages.CartPage;
import pages.CheckoutPage;
import pages.ProductDetailsPage;
import pages.SearchPage;
import tests.base.BaseTest;

public class CheckoutPageTests extends BaseTest {

	private CartPage cartPage;
	private CheckoutPage checkoutPage;
	private ProductDetailsPage productDetailsPage;
	private static final String PRODUCT_1 = "Didi Sport Watch"; //$92.00
	private static final String PRODUCT_2 = "Argus All-Weather Tank"; //$22.00

	@BeforeMethod
	public void setUp() {

		cartPage = new CartPage(driver);
		checkoutPage = new CheckoutPage(driver);
		if (!cartPage.isCartEmpty()) {
			cartPage =homePage.getCart().navigateToCart().clearCart();
		} else {
			SearchPage searchPage = homePage.getSearchBar().searchItem(PRODUCT_1);
			List<WebElement> products = searchPage.getProducts();
			if (!products.isEmpty()) {
				// Assuming the first product is added to the cart
				WebElement product = products.stream().filter(p -> p.getText().contains(PRODUCT_1)).findFirst()
						.orElseThrow(() -> new RuntimeException("Product not found in search results"));
				productDetailsPage = searchPage.clickOnProduct(product).addItemToCart();
				productDetailsPage = new ProductDetailsPage(driver);
				searchPage = cartPage.getSearchBar().searchItem(PRODUCT_2);
				product = products.stream().filter(p -> p.getText().contains(PRODUCT_2)).findFirst()
						.orElseThrow(() -> new RuntimeException("Product not found in search results"));
				productDetailsPage = searchPage.clickOnProduct(product).addItemToCart();
				cartPage = searchPage.getCart().navigateToCart(); // Navigate to cart after adding products
				checkoutPage = (CheckoutPage) cartPage.proceedToCheckout();
			} else {
				throw new RuntimeException("Product not found in search results");
			}
		}
	}

	@Test(priority = 0, description = "Verify that the checkout page is displayed correctly")
	public void testCheckoutPageDisplayed() {

		logStep("Testing checkout page display");
		// Step 1: Verify checkout page is displayed
		Assert.assertTrue(checkoutPage.isPageDisplayed(), "Checkout page should be displayed");
		logStep("Checkout page is displayed successfully");
	}

	@Test(priority = 1, description = "Verify that the checkout page allows proceeding to the overview page")
	public void testCheckoutPageProceedToOverview() {

		logStep("Testing checkout page proceed to overview");
		// Step 1: Proceed to overview page
		BasePage overviewPage = checkoutPage.continueToOverview();

		// Step 2: Verify that the overview page is displayed
		Assert.assertTrue(overviewPage.isPageDisplayed(), "Overview page should be displayed");
		logStep("Successfully proceeded to overview page");
	}
}