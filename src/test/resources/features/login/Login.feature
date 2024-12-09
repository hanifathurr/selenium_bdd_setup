Feature: Login function

  Scenario: User logs in successfully
    Given User is on the Login page
    When User logs in using username "standard_user" and password "secret_sauce"
    Then User redirected to the Product page