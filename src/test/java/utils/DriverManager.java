package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            options.addArguments("--remote-allow-origins=*");
            driver.set(new ChromeDriver(options));
        }
        return driver.get();
    }

    public static void resetDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver != null) {
            try {
                currentDriver.quit();
            } catch (Exception e) {
                System.out.println("Error quitting WebDriver: " + e.getMessage());
            }
            driver.remove();
        }
    }
}
