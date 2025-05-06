package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchPage extends BasePage {

	@FindBy(xpath ="//a[@class ='product-item-link']")
	private List<WebElement> products;
	
	@FindBy(xpath = "//div[@class='message notice']//div")
	private WebElement noResultsMessage;
	
	
	public SearchPage(WebDriver driver) {
		super(driver);
	}

	
	public List<WebElement> getProducts() {
		return products;
	}
	
	public ProductDetailsPage clickOnProduct(WebElement product) {
		clickElement(product);
		return new ProductDetailsPage(driver);
	}
	
	public String getNoResultsMessage() {
		return getText(noResultsMessage);
	}

	public boolean isNoResultsMessageDisplayed() {
		return isElementDisplayed(noResultsMessage);
	}
	
	public boolean isProductDisplayed(String productName) {
		if (products.isEmpty()) {
			return false;
		}
		return true;
	}
}
