Feature: Group B - VIP Checkout

  Scenario Outline: Checkout flow for VIP Users
    Given I am on the PrestaShop home page
    And I login as a "VIP" user
    When I select a product with quantity <Quantity>
    And I proceed to checkout
    And I fill in the shipping address for "<Country>"
    And I select the "<Carrier>" shipping method
    And I apply a coupon code "<Coupon>"
    Then the final price should include the VIP discount and be correct:
      | total | <ExpectedTotal> |

    Examples:
      | Quantity | Country       | Carrier  | Coupon  | ExpectedTotal |
      | 1        | International | Standard | None    | $20.32        |
      | 3        | Domestic      | Express  | Applied | $16.46        |