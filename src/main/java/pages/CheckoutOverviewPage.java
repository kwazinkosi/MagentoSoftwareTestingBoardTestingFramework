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
        // First locate a stable element - like the Cart Subtotal label
        WebElement cartSubtotalLabel = driver.findElement(By.xpath("//th[text()='Cart Subtotal']"));
        WebElement cartSubtotalValue = driver.findElement(RelativeLocator.with(By.cssSelector("span")).toRightOf(cartSubtotalLabel));
        
        // For shipping, find the element that contains the text "Shipping"
        WebElement shippingValue = driver.findElement(RelativeLocator.with(By.cssSelector("span")).below(cartSubtotalValue));
        
        // For order total, look for the exact text match
        WebElement orderTotalLabel =  driver.findElement(RelativeLocator.with(By.cssSelector("th span.label")).below(cartSubtotalLabel));
        WebElement orderTotalValue = driver.findElement(RelativeLocator.with(By.cssSelector("span")).toRightOf(orderTotalLabel));
        
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