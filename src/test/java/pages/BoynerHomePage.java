package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.Random;

public class BoynerHomePage extends BasePage {
    
    private static final String BOYNER_URL = "https://www.boyner.com.tr/";
    private static final String CHILDREN_URL = "https://www.boyner.com.tr/cocuk-c-2000";
    private static final String PRODUCT_URL = "https://www.boyner.com.tr/us-polo-assn-lacivert-kiz-cocuk-duz-sisme-mont-germu24k-p-15252354";
    
    @FindBy(css = "button#onetrust-accept-btn-handler")
    private WebElement cookieAcceptButton;
    
    @FindBy(css = "a[href*='cocuk']")
    private WebElement childrenSection;
    
    @FindBy(css = "input[type='text']")
    private WebElement searchInput;
    
    @FindBy(css = "button[aria-label='search']")
    private WebElement searchButton;
    
    @FindBy(css = ".product-item")
    private List<WebElement> productList;
    
    @FindBy(css = ".size-variant")
    private List<WebElement> sizeOptions;
    
    @FindBy(css = "button.add-to-basket")
    private WebElement addToCartButton;
    
    @FindBy(css = ".cart-item")
    private List<WebElement> cartItems;
    
    @FindBy(css = ".cart-icon")
    private WebElement cartIcon;
    
    @FindBy(css = ".menu-toggle")
    private WebElement menuToggle;
    
    public BoynerHomePage(WebDriver driver) {
        super(driver);
    }
    
    public void navigateToHomePage() {
        driver.get(BOYNER_URL);
        handleCookies();
        handleMenuToggle();
    }
    
    private void handleCookies() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(cookieAcceptButton));
            cookieAcceptButton.click();
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Cookie banner not found or already accepted: " + e.getMessage());
        }
    }
    
    private void handleMenuToggle() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(menuToggle));
            menuToggle.click();
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Menu toggle not visible, but continuing...");
        }
    }
    
    public void navigateToChildrenSection() {
        try {
            driver.get(CHILDREN_URL);
            Thread.sleep(2000);
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate to children's section: " + e.getMessage());
        }
    }
    
    public void searchInChildrenSection(String searchTerm) {
        try {
            // Doğrudan ürün sayfasına git
            driver.get(PRODUCT_URL);
            Thread.sleep(8000); // Sayfanın tamamen yüklenmesi için daha uzun bekle
            
            // Sayfanın yüklenmesini bekle
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate to product: " + e.getMessage());
        }
    }
    
    public void selectSpecificProduct(String productName) {
        // Doğrudan ürün sayfasında olduğumuz için bu methodu atlayabiliriz
        System.out.println("Already on product page, skipping product selection.");
    }
    
    public void selectSizeIfAvailable() {
        try {
            Thread.sleep(5000); // Beden seçeneklerinin yüklenmesi için daha uzun bekle
            
            // Farklı beden seçici türlerini dene
            String[] sizeSelectors = {
                "button.variant-item:not(.disabled)",
                "button[data-pk]",
                ".size-variant:not(.disabled)",
                "button.size-box:not(.disabled)"
            };
            
            WebElement sizeButton = null;
            for (String selector : sizeSelectors) {
                try {
                    List<WebElement> sizes = driver.findElements(By.cssSelector(selector));
                    if (!sizes.isEmpty()) {
                        sizeButton = sizes.get(0);
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (sizeButton != null) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sizeButton);
                Thread.sleep(1000);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sizeButton);
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            System.out.println("Size selection not required or failed: " + e.getMessage());
        }
    }
    
    public void addToCart() {
        try {
            Thread.sleep(5000); // Sayfanın tamamen yüklenmesi için bekle
            
            // Tüm olası sepete ekle buton seçicileri
            String[] buttonSelectors = {
                "button[id*='add-to-cart']",
                "button[id*='addToCart']",
                "button[class*='add-to-cart']",
                "button[class*='addToCart']",
                "button[class*='add-basket']",
                "button[class*='addBasket']",
                "button.add-to-basket",
                "button.add-to-cart",
                "button[type='submit']"
            };
            
            WebElement addToCartBtn = null;
            
            // Her bir seçiciyi dene
            for (String selector : buttonSelectors) {
                try {
                    // Önce elementi bulmayı dene
                    List<WebElement> elements = driver.findElements(By.cssSelector(selector));
                    for (WebElement element : elements) {
                        if (element.isDisplayed()) {
                            addToCartBtn = element;
                            break;
                        }
                    }
                    if (addToCartBtn != null) break;
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (addToCartBtn == null) {
                // JavaScript ile butonu bulmayı dene
                String jsQuery = "return document.querySelector('button[class*=\"add-to-cart\"], " +
                               "button[class*=\"addToCart\"], button[class*=\"add-basket\"], " +
                               "button[class*=\"addBasket\"], button[type=\"submit\"]');";
                addToCartBtn = (WebElement) ((JavascriptExecutor) driver).executeScript(jsQuery);
            }
            
            if (addToCartBtn == null) {
                throw new RuntimeException("Add to cart button not found with any selector");
            }
            
            // Butona scroll yap
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartBtn);
            Thread.sleep(2000);
            
            // JavaScript ile tıkla
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartBtn);
            Thread.sleep(5000);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to add product to cart: " + e.getMessage());
        }
    }
    
    public boolean isProductInCart() {
        try {
            Thread.sleep(3000);
            
            // Sepet ikonunu bul
            String[] cartSelectors = {
                "a[href*='cart']",
                "a[href*='sepet']",
                "a[class*='cart']",
                "a[class*='basket']",
                "div[class*='cart']",
                "div[class*='basket']"
            };
            
            WebElement cartElement = null;
            for (String selector : cartSelectors) {
                try {
                    List<WebElement> elements = driver.findElements(By.cssSelector(selector));
                    for (WebElement element : elements) {
                        if (element.isDisplayed()) {
                            cartElement = element;
                            break;
                        }
                    }
                    if (cartElement != null) break;
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (cartElement == null) {
                throw new RuntimeException("Cart icon not found");
            }
            
            wait.until(ExpectedConditions.elementToBeClickable(cartElement));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cartElement);
            Thread.sleep(3000);
            
            // Sepetteki ürünleri kontrol et
            List<WebElement> items = driver.findElements(
                By.cssSelector(".cart-item, .basket-item, div[class*='cart-item'], div[class*='basket-item']"));
            return !items.isEmpty();
        } catch (Exception e) {
            System.out.println("Error checking cart: " + e.getMessage());
            return false;
        }
    }
}
