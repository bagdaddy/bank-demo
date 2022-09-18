package bank.account.DTO.parseable;

import com.opencsv.bean.CsvBindByName;

public class TransactionDTO {
    @CsvBindByName(column="account_id")
    private Long account;
    @CsvBindByName(column="customer_id")
    private Long customer;
    @CsvBindByName(column="date")
    private String dateTime;
    @CsvBindByName(column="comment")
    private String comment;
    @CsvBindByName(column="amount")
    private double amount;
    @CsvBindByName(column="currency")
    private String currency;

    public TransactionDTO() {}

    public TransactionDTO(
        Long account,
        String dateTime,
        Long customer,
        String comment,
        double amount,
        String currency
    ) {
        this.account = account;
        this.customer = customer;
        this.dateTime = dateTime;
        this.comment = comment;
        this.amount = amount;
        this.currency = currency;
    }

    public Long getAccount() {
        return this.account;
    }

    public Long getCustomer() {
        return this.customer;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public String getComment() {
        return this.comment;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getCurrency() {
        return this.currency;
    }

    public TransactionDTO setAccount(Long account) {
        this.account = account;
        return null;
    }

    public void setCustomer(Long customer) {
        this.customer = customer;
    }

    public void setDate(String date) {
        this.dateTime = date;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
