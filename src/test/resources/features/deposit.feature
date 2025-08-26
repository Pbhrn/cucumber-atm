Feature: Deposit

  Background:
    Given a customer with id 1 and pin 111 with balance 200 exists


  Scenario: deposit 200 to existing account
    Given I login to ATM with id 1 and pin 111
    When I deposit 200
    Then my account balance is 400
