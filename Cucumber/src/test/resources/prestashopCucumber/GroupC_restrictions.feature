Feature: Group C - Restricted Shipping Flow
  # Tests ACTS IDs: 1, 3, 7, 10
  
  Scenario Outline: Verify carriers are hidden based on country constraints
    Given User is on PrestaShop Home Page
    And User adds "1" units of "Hummingbird printed t-shirt" to cart
    When User enters address for country "<country>"
    Then Carrier "<carrier>" should not be visible in delivery options

    Examples:
      | country        | carrier           |
      | USA            | Express Shipping  |
      | United Kingdom | Click and Collect |