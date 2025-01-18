package utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {

    @Before
    public void setUp() {
        // Initialize the WebDriver
        WebDriver driver = DriverManager.getDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();
        
        // Take screenshot if scenario fails
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot");
            } catch (Exception e) {
                System.out.println("Failed to take screenshot: " + e.getMessage());
            }
        }
        
        try {
            // Close browser window
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            System.out.println("Error closing browser: " + e.getMessage());
        } finally {
            // Reset driver instance
            DriverManager.resetDriver();
        }
    }
}
