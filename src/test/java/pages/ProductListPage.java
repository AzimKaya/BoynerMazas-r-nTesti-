package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import java.util.List;
import java.util.Random;

public class ProductListPage extends BasePage {

    private Random random = new Random();

    public ProductListPage(WebDriver driver) {
        super(driver);
    }

    public void selectFirstAvailableProduct() {
        try {
            // Wait for page load
            JavascriptExecutor js = (JavascriptExecutor) driver;
            wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));
            
            // Additional wait for products to load
            Thread.sleep(5000);
            
            // Try different selectors
            String[] selectors = {
                "div[data-id]",
                ".product-card",
                ".product-item",
                ".product-list-item",
                "div[class*='product']"
            };
            
            List<WebElement> products = null;
            for (String selector : selectors) {
                try {
                    // Scroll down a bit to trigger lazy loading
                    js.executeScript("window.scrollBy(0, 500)");
                    Thread.sleep(2000);
                    
                    // Try to find products with current selector
                    products = driver.findElements(By.cssSelector(selector));
                    if (!products.isEmpty()) {
                        System.out.println("Found products with selector: " + selector);
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Failed with selector: " + selector);
                    continue;
                }
            }
            
            if (products != null && !products.isEmpty()) {
                // Select a random product
                int randomIndex = random.nextInt(products.size());
                WebElement selectedProduct = products.get(randomIndex);
                
                // Wait for the product to be clickable
                wait.until(ExpectedConditions.elementToBeClickable(selectedProduct));
                
                // Scroll to product
                js.executeScript("arguments[0].scrollIntoView(true);", selectedProduct);
                Thread.sleep(2000);
                
                // Try different approaches to click the product
                boolean clicked = false;
                
                // Try 1: Find and click any link
                try {
                    List<WebElement> links = selectedProduct.findElements(By.tagName("a"));
                    if (!links.isEmpty()) {
                        for (WebElement link : links) {
                            String href = link.getAttribute("href");
                            if (href != null && !href.isEmpty()) {
                                driver.get(href);
                                clicked = true;
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Failed to click links");
                }
                
                // Try 2: JavaScript click on product
                if (!clicked) {
                    try {
                        js.executeScript("arguments[0].click();", selectedProduct);
                        clicked = true;
                    } catch (Exception e) {
                        System.out.println("Failed JavaScript click");
                    }
                }
                
                // Try 3: Find any clickable element
                if (!clicked) {
                    List<WebElement> clickableElements = selectedProduct.findElements(By.cssSelector("*"));
                    for (WebElement element : clickableElements) {
                        try {
                            if (element.isDisplayed() && element.isEnabled()) {
                                element.click();
                                clicked = true;
                                break;
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                }
                
                if (!clicked) {
                    throw new RuntimeException("Could not click the product with any method");
                }
                
                // Wait for navigation
                Thread.sleep(3000);
            } else {
                throw new RuntimeException("No products found on the page with any selector");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to select product: " + e.getMessage());
        }
    }
}
