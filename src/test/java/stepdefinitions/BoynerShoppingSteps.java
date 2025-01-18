package stepdefinitions;

import io.cucumber.java.en.*;
import pages.BoynerHomePage;

public class BoynerShoppingSteps extends BaseSteps {
    
    private BoynerHomePage boynerHomePage;
    
    public BoynerShoppingSteps() {
        boynerHomePage = new BoynerHomePage(driver);
    }
    
    @Given("I am on the Boyner homepage")
    public void i_am_on_the_boyner_homepage() {
        boynerHomePage.navigateToHomePage();
    }
    
    @When("I navigate to the specific product")
    public void i_navigate_to_specific_product() {
        boynerHomePage.searchInChildrenSection("");
    }
    
    @When("I select size if available")
    public void i_select_size_if_available() {
        boynerHomePage.selectSizeIfAvailable();
    }
    
    @Then("I should be able to add the product to cart")
    public void i_should_be_able_to_add_product_to_cart() {
        boynerHomePage.addToCart();
    }
    
    @Then("I should see the product in my cart")
    public void i_should_see_product_in_cart() {
        boynerHomePage.isProductInCart();
    }
}
