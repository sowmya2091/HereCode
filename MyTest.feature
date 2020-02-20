Feature: Documentation page
  I want to verify loading of documentation page

Scenario: Verify loading of documentation page
    Given I open a browser
    When I navigate to documentation page
    Then I validate Documentation page is loaded properly
    And The title should be "Documentation, Code Examples and API References - HERE Developer"
    