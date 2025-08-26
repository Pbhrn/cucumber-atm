package ku.atm;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

public class StepDefATM {

    ATM atm;
    Bank bank;
    boolean validLogin;
    Exception lastError;

    @Before
    public void init() {
        bank = new Bank("KU Bank");
        atm = new ATM(bank);
        lastError = null;
    }

    @Given("a customer with id {int} and pin {int} exists")
    public void a_customer_with_id_and_pin_exists(int id, int pin) {
        bank.openAccount(new Customer(id, pin));
    }

    @Given("a customer with id {int} and pin {int} with balance {double} exists")
    public void a_customer_with_id_and_pin_with_balance_exists(int id, int pin, double balance) {
        bank.openAccount(new Customer(id, pin, balance));
    }

    @When("I login to ATM with id {int} and pin {int}")
    public void i_login_to_ATM_with_id_and_pin(int id, int pin) {
        validLogin = atm.validateCustomer(id, pin); // คาดว่าเมธอดนี้คืน true/false
    }

    @Then("I can login")
    public void i_can_login() {
        assertTrue(validLogin);
    }

    @Then("I cannot login")
    public void i_cannot_login() {
        assertFalse(validLogin);
    }

    @When("I try to login with id {int} and pin {int}")
    public void i_try_to_login_with_id_and_pin(int id, int pin) {
        try {
            validLogin = atm.validateCustomer(id, pin);
        } catch (Exception e) {
            lastError = e;
        }
    }

    @Then("login should fail with message {string}")
    public void login_should_fail_with_message(String msg) {
        // ถ้าระบบของคุณคืน false ไม่ได้ throw ก็ตรวจ false แทน
        if (lastError == null) {
            assertFalse(validLogin);
        } else {
            assertTrue(lastError.getMessage().toLowerCase().contains(msg.toLowerCase()));
        }
    }

    @When("I withdraw {double} from ATM")
    public void i_withdraw_from_atm(double amount) throws NotEnoughBalanceException {
        atm.withdraw(amount);
    }

    @When("I overdraw {double} from ATM")
    public void i_withdraw_from_atm_more_than_balance(double amount) {
        assertThrows(NotEnoughBalanceException.class, () -> atm.withdraw(amount));
    }

    @Then("my account balance is {double}")
    public void my_account_balance_is(double balance) {
        assertEquals(balance, atm.getBalance(), 1e-6);
    }

    @When("I transfer {double} to customer id {int}")
    public void i_transfer_to_customer_id(double amount, int toId) throws NotEnoughBalanceException {
        atm.transfer(toId, amount);
    }

    @Then("customer id {int} account balance is {double}")
    public void customer_id_account_balance_is(int id, double balance) {
        assertEquals(balance, bank.getCustomer(id).getAccount().getBalance(), 1e-6);
    }

    @When("I deposit {double} to ATM")
    public void i_deposit_to_atm(double amount) {
        atm.deposit(amount);  // ถ้าในโมเดลคุณต้องเรียกผ่าน account ให้ปรับเป็น getAccount().deposit(amount)
    }
}
