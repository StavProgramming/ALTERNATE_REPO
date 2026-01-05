Feature: Group B - VIP Automatic Discount Flow
  # Tests ACTS IDs: 10, 11, 12
  
  Scenario Outline: Verify automatic VIP discounts are applied
    Given User is on PrestaShop Home Page
    And User logs in with "vip_user@testing.com" and "V!p_Secure_789#"
    When User adds "<qty>" units of "Hummingbird printed t-shirt" to cart
    And User proceeds to checkout with country "<country>"
    Then The product price should show 20% specific discount
    And The total should include the 15% VIP group reduction

    Examples:
      | qty | country        | carrier           |
      | 1   | USA            | Click and Collect |
      | 3   | France         | Express Shipping  |