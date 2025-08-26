Feature: Deposit

  Background:
    Given a bank with accounts:
      | id     | name   | balance |
      | 10001  | Alice  | 500     |

  Scenario: deposit 200 to existing account
    Given I login with id 10001
    When I deposit 200
    Then my balance should be 700
