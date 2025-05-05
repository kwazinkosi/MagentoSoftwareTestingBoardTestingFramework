package tests;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.CartPage;
import pages.CheckoutCompletePage;
import pages.CheckoutOverviewPage;
import pages.CheckoutPage;
import pages.ProductDetailsPage;
import pages.SearchPage;
import tests.base.BaseTest;

public class CheckoutOverviewPageTests extends BaseTest {

	private CartPage cartPage;
	private CheckoutPage checkoutPage;
	private CheckoutOverviewPage checkoutOverviewPage;
	private ProductDetailsPage productDetailsPage;
	private static final String PRODUCT_1 = "Didi Sport Watch"; //$92.00
	private static final String PRODUCT_2 = "Argus All-Weather Tank"; //$22.00

	private static final String EXPECTED_CART_SUBTOTAL = "114.00";
	private static final String EXPECTED_SHIPPING = "10.00";
	private static final String EXPECTED_ORDER_TOTAL = "124.00";
	private static final String EXPECTED_PAYMENT_SUMMARY = "Cart Subtotal: $114.00\nShipping: $10.00\nOrder Total: $124.00\n";
	@BeforeMethod
	public void setUp() {

		cartPage = new CartPage(driver);
		checkoutPage = new CheckoutPage(driver);
		if (!cartPage.isCartEmpty()) {
			cartPage = productDetailsPage.getCart().navigateToCart();
			checkoutPage = (CheckoutPage) cartPage.proceedToCheckout();
			checkoutOverviewPage = (CheckoutOverviewPage) checkoutPage.continueToOverview();
		} else {
			SearchPage searchPage = cartPage.getSearchBar().searchItem(PRODUCT_1);
			List<WebElement> products = searchPage.getProducts();
			if (!products.isEmpty()) {
				// Assuming the first product is added to the cart
				productDetailsPage = searchPage.clickOnProduct(products.get(0)).addItemToCart();
				productDetailsPage = new ProductDetailsPage(driver);
				searchPage = cartPage.getSearchBar().searchItem(PRODUCT_2);
				productDetailsPage = searchPage.clickOnProduct(products.get(0)).addItemToCart();
				cartPage = productDetailsPage.getCart().navigateToCart(); // Navigate to cart after adding products
				checkoutPage = (CheckoutPage) cartPage.proceedToCheckout();
				checkoutOverviewPage = (CheckoutOverviewPage) checkoutPage.continueToOverview();
			} else {
				throw new RuntimeException("Product not found in search results");
			}
		}
	}
	

	@Test(priority = 0, description = "Verify payment summary is displayed correctly")
	public void testPaymentSummary() {
		logStep("Testing payment summary");

		String paymentSummary = checkoutOverviewPage.getPaymentSummary();
		logStep("Payment Summary: " + paymentSummary);

		Assert.assertNotNull(paymentSummary, "Payment summary should not be null");
		Assert.assertTrue(paymentSummary.contains("Cart Subtotal:" + EXPECTED_CART_SUBTOTAL), "Payment summary should contain cart subtotal");
		Assert.assertTrue(paymentSummary.contains("Shipping:"+ EXPECTED_SHIPPING), "Payment summary should contain shipping");
		Assert.assertTrue(paymentSummary.contains("Order Total:" + EXPECTED_ORDER_TOTAL), "Payment summary should contain order total");
		Assert.assertEquals(paymentSummary, EXPECTED_PAYMENT_SUMMARY, "Payment summary should match expected value");
		
	}
	
	@Test(priority = 1, description = "Verify the total amount is displayed correctly")
	public void testTotalAmount() {
		logStep("Testing total amount");

		double totalAmount = checkoutOverviewPage.getTotal();
		logStep("Total Amount: " + totalAmount);

		Assert.assertEquals(totalAmount, Double.parseDouble(EXPECTED_ORDER_TOTAL), "Total amount should match expected value");
	}
	
	@Test(priority = 2, description = "Verify completion of checkout process")
	public void testCheckoutCompletion() {
		
		logStep("Testing checkout completion");

		CheckoutCompletePage checkoutCompletePage = checkoutOverviewPage.finishCheckout();
		logStep("Checkout completed successfully");

		Assert.assertTrue(checkoutCompletePage.isOrderSuccessful(), "Order should be successful");
	}
	
	
}

