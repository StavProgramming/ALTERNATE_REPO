Feature: Group A - Standard Checkout

  Scenario Outline: Checkout flow for Standard Users
    Given I am on the PrestaShop home page
    And I am a "<CustomerType>" user
    When I select a product with quantity <Quantity>
    And I proceed to checkout
    And I fill in the shipping address for "<Country>"
    And I select the "<Carrier>" shipping method
    And I apply a coupon code "<Coupon>"
    Then the final price should be calculated correctly based on:
      | CustomerType | <CustomerType> |
      | Carrier      | <Carrier>      |
      | Coupon       | <Coupon>       |
      | total        | <ExpectedTotal>|

    Examples:
      | CustomerType | Quantity | Country       | Carrier  | Coupon  | ExpectedTotal |
      | Registered   | 1        | Domestic      | Pickup   | SAVE10  | $17.01        |
      | Guest        | 1        | International | Standard | Applied | $17.01        |