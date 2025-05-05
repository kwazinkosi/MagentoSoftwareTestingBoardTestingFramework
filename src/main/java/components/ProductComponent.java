package components;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import constants.WaitTime;
import pages.BasePage;
import pages.ProductDetailsPage;

public class ProductComponent extends BasePage {

    
    @FindBy(xpath = ".//h1[@class='page-title']//span")
    private WebElement productNameElement;

    @FindBy(id = "tab-label-description-title")
    private WebElement productDescriptionTab; // click to view description
    
    @FindBy(xpath = ".//div[contains(@class, 'description')]//p")
    private List<WebElement> productDescriptionElement;

    @FindBy(xpath = ".//div[@class='product-info-price']//span[@class ='price']")
    private WebElement productPriceElement;

    @FindBy(xpath = ".//button[@title = 'Add to Cart']")
    private WebElement addProductBtn; // only exists once a product has been added to cart

    @FindBy(xpath = ".//div[@class ='product media']//img") 
    private List<WebElement> imageLink;


    public ProductComponent(WebDriver driver, WebElement rootElement) {
        super(driver);
        // Initialize elements within the rootElement context
        PageFactory.initElements(rootElement, this);
    }

    public void addProductToCart() {
        try {
            customWait.until(ExpectedConditions.elementToBeClickable(addProductBtn), WaitTime.NORMAL);
            addProductBtn.click();
        } catch (Exception e) {
            System.out.println("Exception occurred while adding product to cart: " + e.getMessage());
        }
    }

    public boolean isAddCartButtonVisible() {
        return isElementDisplayed(addProductBtn);
    }

    public String getProductPrice() {
        return getText(productPriceElement);
    }

    public String getProductName() {
        return getText(productNameElement);
    }

    public String getProductDescription() {
        
		// first click the description tab
		customWait.until(ExpectedConditions.elementToBeClickable(productDescriptionTab), WaitTime.NORMAL);
		productDescriptionTab.click();

		// wait for the description to load
		customWait.until(ExpectedConditions.visibilityOfAllElements(productDescriptionElement), WaitTime.NORMAL);
        StringBuilder description = new StringBuilder();
		for (WebElement element : productDescriptionElement) {
			description.append(getText(element)).append("\n");
		}
		return description.toString();
    }

    public boolean isProductImageDisplayed() {
        return isElementDisplayed(imageLink.get(0));
    }
	

    public ProductDetailsPage viewDetails() {
        try {
//            clickElement(imageLink);
            return new ProductDetailsPage(driver);
        } catch (Exception e) {
            System.out.println("Exception occurred while viewing product details: " + e.getMessage());
            return null;
        }
    }
}