package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import constants.WaitTime;

public class CheckoutCompletePage extends BasePage {

    @FindBy(xpath = "//span[@class='base']")
    private WebElement successMessage;
    
    @FindBy(xpath = "//a[@class='action primary continue']")
    private WebElement continueShoppingButton;

    public static final String SUCCESS_MESSAGE = "Thank you for your purchase!";
    public static final String COMPLETION_URL_FRAGMENT = "/checkout/onepage/success/";

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageDisplayed() {
        
    	return customWait.until(d -> 
            d.getCurrentUrl().contains(COMPLETION_URL_FRAGMENT) &&
            getPageTitle().equalsIgnoreCase("Success Page"),
            WaitTime.NORMAL
        );
    }

    public LandingPage navigateBackToHome() {
        
    	executeWithLogging(continueShoppingButton, "Navigate back to home", () -> {
            clickElement(continueShoppingButton);
            waitForPageLoad();
        });
        return new LandingPage(driver);
    }

    public String getSuccessMessage() {
        return getText(successMessage);
    }

    public boolean isOrderSuccessful() {
        return getSuccessMessage().equals(SUCCESS_MESSAGE);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}