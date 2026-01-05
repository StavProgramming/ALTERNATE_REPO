Feature: Group A - Standard Pricing Flow
  # Tests ACTS IDs: 2, 4, 5, 6, 8, 9, 13, 14
  
  Scenario Outline: Verify pricing for Guest and Registered users
    Given User is on PrestaShop Home Page
    And User logs in with "<email>" and "<password>" if not visitor
    When User adds "<qty>" units of "Hummingbird printed t-shirt" to cart
    And User proceeds to checkout with country "<country>"
    And User applies coupon "<coupon>"
    And User selects carrier "<carrier>"
    Then The final price should reflect fixed discount and bulk rules for "<email>"
    And Carrier "<carrier>" should be successfully selected

    Examples:
      | email                     | password          | qty | country        | carrier           | coupon |
      | visitor                   | N/A               | 1   | France         | Standard Shipping | None   |
      | standard_user@testing.com | Std_P@ss_2026!    | 3   | United Kingdom | Standard Shipping | SAVE10 |