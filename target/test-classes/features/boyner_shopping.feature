Feature: Boyner Shopping Cart Test
  As a user
  I want to add items to my shopping cart
  So that I can purchase them later

  @cart
  Scenario: Add women's coat to cart
    Given I am on the Boyner homepage
    When I navigate to women coats section
    And I select the first available coat
    And I select a size if required
    And I add the item to cart
    Then I should see the item in my cart

  @social
  Scenario: Check social media links
    Given I am on the Boyner homepage
    When I scroll to the footer
    Then I should see social media icons
    And I should be able to click on Facebook icon
    And I should be able to click on Twitter icon
    And I should be able to click on Instagram icon
    And I should be able to click on YouTube icon

  @BoynerShopping @ChildrenProducts
  Scenario: Add children's product to cart from Boyner
    Given I am on the Boyner homepage
    When I navigate to children's section
    And I select a random product from children's section
    And I select size if available
    Then I should be able to add the product to cart
    And I should see the product in my cart

  @BoynerShopping @ChildrenCoat
  Scenario: Add children's coat to cart from Boyner
    Given I am on the Boyner homepage
    When I navigate to the specific product
    And I select size if available
    Then I should be able to add the product to cart
    And I should see the product in my cart
