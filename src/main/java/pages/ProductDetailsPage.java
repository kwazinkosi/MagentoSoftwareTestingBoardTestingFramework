package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import components.ProductComponent;
import constants.WaitTime;

public class ProductDetailsPage extends BasePage {

    // Page elements
    @FindBy(id = "product-addtocart-button")
    private WebElement addToCartButton;
    
    @FindBy(id = "maincontent")
    private WebElement productDetailsContainer;

    @FindBy(xpath = "//div[contains(@class, 'swatch-option') and contains(@class, 'text')]")
    private List<WebElement> productSize; // xs, s, m, l, xl
    
    @FindBy(xpath = "//div[contains(@class, 'swatch-option') and contains(@class, 'color')]")
    private List<WebElement> productColor; // red, blue, green, yellow, black, white
    
    @FindBy(xpath = "//div[@data-bind='html: $parent.prepareMessageForHtml(message.text)']")
    private WebElement addedToCartMessage;
    // Component
    private final ProductComponent productComponent; 

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
        this.productComponent = new ProductComponent(driver, productDetailsContainer);
        
    }
    
    // Page Actions

    public ProductDetailsPage addItemToCart() {
    	
		if (!productSize.isEmpty() && !productColor.isEmpty()) {
			clickElement(productSize.get(0)); // Select first size
			clickElement(productColor.get(0)); // Select first color
		} else if (!productSize.isEmpty()) {
			clickElement(productSize.get(0)); // Select first size
		} else if (!productColor.isEmpty()) {
			clickElement(productColor.get(0)); // Select first color
		}
        clickElement(addToCartButton);
        return this;
    }
    
    public boolean isAddToCartVisible() {
        return isElementDisplayed(addToCartButton);
    }
    
    public String getProductPrice() {
        return productComponent.getProductPrice();
    }

    public String getProductName() {
        return productComponent.getProductName();
    }

    public String getProductDescription() {
        return productComponent.getProductDescription();
    }
    
    public ProductComponent getProduct() {
    	return this.productComponent;
    }
    
    @Override
    public boolean isPageDisplayed() {
        
    	return customWait.until(d -> 
            isElementDisplayed(productDetailsContainer) && 
            d.getCurrentUrl().contains("inventory-item.html"),
            WaitTime.NORMAL
        );
    }
}