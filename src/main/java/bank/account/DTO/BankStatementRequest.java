package bank.account.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class BankStatementRequest {
    @NotBlank(message = "Account ids are mandatory")
    @JsonProperty("accounts")
    private List<Long> accounts;

    @JsonProperty("fromDate")
    private LocalDateTime fromDate = null;

    @JsonProperty("toDate")
    private LocalDateTime toDate = null;

    public BankStatementRequest(
            List<Long> accounts,
            String fromDate,
            String toDate
    ) {
        this.accounts = accounts;

        if (fromDate != null) {
            this.fromDate = LocalDate.parse(fromDate).atStartOfDay();

        }

        if (toDate != null) {
            this.toDate = LocalDate.parse(toDate).atStartOfDay();
        }
    }

    public List<Long> getAccounts() {
        return this.accounts;
    }

    public LocalDateTime getFromDate() {
        return this.fromDate;
    }

    public LocalDateTime getToDate() {
        return this.toDate;
    }
}
