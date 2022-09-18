package bank.account.DTO;

import bank.account.enums.Currency;
import bank.account.models.Account;

public class BalanceResponseDTO {
    private Currency currency;

    private double balance;

    private Account account;

    public BalanceResponseDTO(
            Currency currency,
            double balance,
            Account account
    ) {
        this.currency = currency;
        this.balance = balance;
        this.account = account;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public double getBalance() {
        return this.balance;
    }

    public void addBalance(double amount) {
        this.balance += amount;
    }

    public void subtractBalance(double amount) {
        this.balance -= amount;
    }
}
