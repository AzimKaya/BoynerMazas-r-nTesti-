package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import java.util.List;

public class ProductDetailPage extends BasePage {
    
    @FindBy(css = "button.size-list-item:not(.disabled)")
    private List<WebElement> availableSizes;
    
    @FindBy(css = "button[data-test-id='add-to-cart']")
    private WebElement addToCartButton;
    
    @FindBy(css = "a.header-cart-button")
    private WebElement cartButton;
    
    @FindBy(css = ".add-to-cart-success")
    private WebElement addToCartSuccess;

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    public void selectFirstAvailableSize() {
        try {
            // Wait for page load
            JavascriptExecutor js = (JavascriptExecutor) driver;
            wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));
            
            // Additional wait for size options to load
            Thread.sleep(3000);
            
            // Wait for size options to be visible
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button.size-list-item:not(.disabled)")));
            
            if (!availableSizes.isEmpty()) {
                WebElement firstSize = availableSizes.get(0);
                js.executeScript("arguments[0].scrollIntoView(true);", firstSize);
                Thread.sleep(1000);
                js.executeScript("arguments[0].click();", firstSize);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            // Size selection might not be required for some products
            System.out.println("Size selection not available or not required");
        }
    }

    public void addToCart() {
        try {
            // Wait for add to cart button
            wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
            
            // Scroll to button and click
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
            Thread.sleep(1000);
            js.executeScript("arguments[0].click();", addToCartButton);
            
            // Wait for cart animation
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void goToCart() {
        try {
            // Wait for cart button to be clickable
            wait.until(ExpectedConditions.elementToBeClickable(cartButton));
            
            // Click cart button
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", cartButton);
            
            // Wait for navigation
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isItemAddedToCart() {
        try {
            wait.until(ExpectedConditions.visibilityOf(addToCartSuccess));
            return addToCartSuccess.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
