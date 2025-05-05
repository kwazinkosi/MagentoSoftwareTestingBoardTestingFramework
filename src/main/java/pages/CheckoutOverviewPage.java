package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.locators.RelativeLocator;

import constants.WaitTime;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class CheckoutOverviewPage extends BasePage {

    
    @FindBy(xpath = "//td[@class='amount']//strong")
    private WebElement totalAmount;
  
    
    @FindBy(id = "cancel")
    private WebElement cancelButton;
    
    @FindBy(xpath = "//span[normalize-space()='Place Order']")
    private WebElement finishButton;

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    public String getPaymentSummary() {
        
    	StringBuilder paymentSummary = new StringBuilder();

        // Cart Subtotal is above Shipping
        WebElement shippingLabel = driver.findElement(By.xpath("//*[text()='Shipping']"));
        WebElement cartSubtotalLabel = driver.findElement(RelativeLocator.with(By.tagName("span")).above(shippingLabel));
        WebElement cartSubtotalValue = driver.findElement(RelativeLocator.with(By.tagName("span")).toRightOf(cartSubtotalLabel));
        
        // Order Total is below Shipping
        WebElement orderTotalLabel = driver.findElement(RelativeLocator.with(By.tagName("strong")).below(shippingLabel));
        WebElement orderTotalValue = driver.findElement(RelativeLocator.with(By.tagName("span")).toRightOf(orderTotalLabel));

        // Try `near()` to get value near label "Shipping"
        WebElement shippingValue = driver.findElement(RelativeLocator.with(By.tagName("span")).near(shippingLabel));

        paymentSummary.append("Cart Subtotal: ").append(cartSubtotalValue.getText()).append("\n");
        paymentSummary.append("Shipping: ").append(shippingValue.getText()).append("\n");
        paymentSummary.append("Order Total: ").append(orderTotalValue.getText()).append("\n");

        return paymentSummary.toString();
    }

    public double getTotal() {
        return parseCurrency(getText(totalAmount));
    }

    public CheckoutCompletePage finishCheckout() {
    	
		try {
			clickElement(finishButton);
			customWait.until(d -> d.getCurrentUrl().contains("checkout/onepage/success"), WaitTime.NORMAL);
			return new CheckoutCompletePage(driver);
		} catch (TimeoutException e) {
			System.out.println("Checkout page did not load successfully.");
		}
		return null;
    }

    private double parseCurrency(String valueText) {
        try {
            String numericValue = valueText.replaceAll("[^\\d.]", "");
            return NumberFormat.getNumberInstance(Locale.US)
                              .parse(numericValue)
                              .doubleValue();
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse currency value: " + valueText, e);
        }
    }
}