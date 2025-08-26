Feature: login
    As a customer
    I want to login to ATM
    so that I can do transactions

Background:
    Given a customer with id 1 and pin 111 exists

Scenario: Correct id and pin
    When I login to ATM with id 1 and pin 111
    Then I can login

Scenario: Incorrect pin
    When I login to ATM with id 1 and pin 222
    Then I cannot login

Background:
    Given a bank with accounts:
      | id     | name   | balance |
      | 10001  | Alice  | 500     |
      | 10002  | Bob    | 300     |

Scenario: Login with non-existing id
    When I login with id 99999
    Then login should fail with message "account not found"
