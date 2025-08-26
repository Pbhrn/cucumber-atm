package ku.atm;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

public class DepositStepdefs {
    private Bank bank;
    private ATM atm;

    @Given("a bank with accounts:")
    public void a_bank_with_accounts(io.cucumber.datatable.DataTable table) {
        bank = new Bank();
        for (var row : table.asMaps()) {
            bank.addAccount(new Account(row.get("id"), row.get("name"),
                    Double.parseDouble(row.get("balance"))));
        }
        atm = new ATM(bank);
    }

    @Given("I login with id {int}")
    public void i_login_with_id(int id) {
        atm.login(String.valueOf(id));
    }

    @When("I deposit {int}")
    public void i_deposit(int amount) {
        atm.deposit(amount); // ปรับให้ตรงของจริง เช่น atm.getAccount().deposit(amount)
    }

    @Then("my balance should be {int}")
    public void my_balance_should_be(int expected) {
        assertEquals(expected, atm.getAccount().getBalance(), 0.001);
    }
}
