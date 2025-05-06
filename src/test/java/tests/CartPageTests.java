package tests;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import components.CartComponent;
import pages.CartPage;
import pages.CheckoutPage;
import pages.ProductDetailsPage;
import pages.SearchPage;
import tests.base.BaseTest;

public class CartPageTests extends BaseTest {

	private CartPage cartPage;
	private CheckoutPage checkoutPage;
	private ProductDetailsPage productDetailsPage;
	private static final String PRODUCT_1 = "Didi Sport Watch"; // $92.00
	private static final String PRODUCT_2 = "Argus All-Weather Tank"; // $22.00

	@BeforeMethod
	public void setUp() {

//		cartPage = new CartPage(driver);
		checkoutPage = new CheckoutPage(driver);
		productDetailsPage = new ProductDetailsPage(driver);

		CartComponent cart = homePage.getCart();
		if (cart != null) {
			cartPage = cart.navigateToCart();
			if (cartPage != null) {
				cartPage.clearCart();
			}
		} else {
			cartPage = new CartPage(driver);
		}

		SearchPage searchPage = homePage.getSearchBar().searchItem(PRODUCT_1);
		List<WebElement> products = searchPage.getProducts();
		if (!products.isEmpty()) {
			// Assuming the first product is added to the cart
			WebElement product = products.stream().filter(p -> p.getText().contains(PRODUCT_1)).findFirst()
					.orElseThrow(() -> new RuntimeException("Product not found in search results"));
			productDetailsPage = searchPage.clickOnProduct(product).addItemToCart();
			productDetailsPage = new ProductDetailsPage(driver);
			searchPage = homePage.getSearchBar().searchItem(PRODUCT_2);
			product = products.stream().filter(p -> p.getText().contains(PRODUCT_2)).findFirst()
					.orElseThrow(() -> new RuntimeException("Product not found in search results"));
			productDetailsPage = searchPage.clickOnProduct(product).addItemToCart();
			cartPage = productDetailsPage.getCart().navigateToCart(); // Navigate to cart after adding products
		} else {
			throw new RuntimeException("Product not found in search results");
		}

	}

	@AfterMethod
	public void tearDown() {
		// Clear the cart after each test
		if (!cartPage.isCartEmpty()) {
			cartPage.clearCart();
		}
	}

	@Test(priority = 0, description = "Verify removing a product from the cart works correctly")
	public void testRemoveProductFromCart() {
		logStep("Testing removal of product from cart");

		// Step 1: Get initial cart count
		int initialCount = cartPage.getCartItemCount();
		logStep("Initial cart count: " + initialCount);

		// Step 2: Remove product from cart

		cartPage.removeProduct(PRODUCT_1);
		logStep("Removed product: " + PRODUCT_1);

		// Step 3: Verify cart count and product removal
		Assert.assertEquals(cartPage.getCartItemCount(), initialCount - 1, "Cart count should decrease after removal");
		Assert.assertFalse(cartPage.containsProduct(PRODUCT_1), "Removed product should no longer be in cart");
		logStep("Cart count after removal: " + cartPage.getCartItemCount());

//		logStepWithScreenshot("Product removed from cart successfully");
	}

	@Test(priority = 1, description = "Verify the cart page contains the added product")
	public void testCartContainsAddedProduct() {
		logStep("Testing cart contains added product");
		String productName = PRODUCT_1; // Assuming this product was added

		// Step 2: Verify the cart contains the added product
		Assert.assertTrue(cartPage.containsProduct(productName), "Cart should contain the added product");
		logStep("Product is present in the cart");

//		logStepWithScreenshot("Cart contains added product successfully");
	}

	@Test(priority = 2, description = "Verify the cart page displays the correct item count")
	public void testCartItemCount() {
		logStep("Testing cart item count");

		// Step 1: Get the expected item count from the product details page
		int expectedCount = 2; // Assuming two items were added
		logStep("Expected item count: " + expectedCount);

		// Step 2: Verify the cart item count
		Assert.assertEquals(cartPage.getCartItemCount(), expectedCount, "Cart item count should match expected count");
		logStep("Cart item count is correct");

//		logStepWithScreenshot("Cart item count verified successfully");
	}

	@Test(priority = 3, description = "Verify the cart page allows clearing the cart")
	public void testClearCart() {
		logStep("Testing clear cart functionality");

		// Step 1: Clear the cart
		cartPage.clearCart();
		logStep("Cleared the cart");

		// Step 2: Verify the cart is empty
		Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty after clearing");
		logStep("Cart is empty after clearing");

//		logStepWithScreenshot("Cart cleared successfully");
	}

	@Test(priority = 3, description = "Verify the cart page allows proceeding to checkout")
	public void testProceedToCheckout() {

		logStep("Testing proceed to checkout");

		// Step 1: Click on the checkout button
		checkoutPage = (CheckoutPage) cartPage.proceedToCheckout();
		logStep("Clicked on checkout button");
		// Step 2: Verify the checkout page is displayed
		Assert.assertTrue(checkoutPage.isPageDisplayed(), "Checkout page should be displayed");
		logStep("Checkout page is displayed");
	}
}
