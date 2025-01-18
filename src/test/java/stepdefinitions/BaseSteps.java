package stepdefinitions;

import org.openqa.selenium.WebDriver;
import utils.DriverManager;

public class BaseSteps {
    protected WebDriver driver;

    public BaseSteps() {
        driver = DriverManager.getDriver();
    }
}
