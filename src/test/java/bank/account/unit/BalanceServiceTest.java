package bank.account.unit;

import bank.account.DTO.BalanceResponseDTO;
import bank.account.DTO.BankStatementRequest;
import bank.account.DTO.parseable.TransactionDTO;
import bank.account.DemoApplicationTests;
import bank.account.enums.Currency;
import bank.account.models.Account;
import bank.account.models.Customer;
import bank.account.models.Transaction;
import bank.account.repositories.CustomerRepository;
import bank.account.repositories.TransactionCrudRepository;
import bank.account.services.BalanceService;
import bank.account.utils.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class BalanceServiceTest extends DemoApplicationTests {
    @Autowired
    TestUtil preparationService;

    @Autowired
    BalanceService balanceService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TransactionCrudRepository transactionCrudRepository;

    @Test
    public void testIncomingTransactionsCalculation() throws Exception {
        List<Account> accounts = this.preparationService.createTestAccounts();

        assertNotNull(accounts.get(0).getId());

        List<TransactionDTO> transactionDTOS1 = this.getTransactionDTOs(accounts.get(0), accounts.get(0).getCustomer().getId(), Currency.EUR.toString());
        List<TransactionDTO> transactionDTOS2 = this.getTransactionDTOs(accounts.get(1), accounts.get(1).getCustomer().getId(), Currency.EUR.toString());

        List<Transaction> transactions1 = balanceService.importTransactions(transactionDTOS1);
        List<Transaction> transactions2 = balanceService.importTransactions(transactionDTOS2);

        double amount1 = this.calculateBalance(transactionDTOS1, accounts.get(0).getCustomer());
        double amount2 = this.calculateBalance(transactionDTOS2, accounts.get(1).getCustomer());

        assertEquals(transactionDTOS1.size(), transactions1.size());
        assertEquals(transactionDTOS2.size(), transactions2.size());

        List<BalanceResponseDTO> balances1 = balanceService.calculateBalances(new BankStatementRequest(Arrays.asList(accounts.get(0).getId()), null, null));
        List<BalanceResponseDTO> balances2 = balanceService.calculateBalances(new BankStatementRequest(Arrays.asList(accounts.get(1).getId()), null, null));

        assertEquals(balances1.size(), 1);
        assertEquals(balances2.size(), 1);

        assertEquals(amount1, balances1.get(0).getBalance());
        assertEquals(amount2, balances2.get(0).getBalance());
    }

    @Test
    public void testBalanceCalculations() {
        Customer customerBeneficiaryO = this.preparationService.createCustomer("beneficiaryTest", "test@beneficiaries.com", "iGeTmOnEy");
        Customer customerO = this.preparationService.createCustomer("balanceTest", "test@balances.com", "balanceTest123");
        Account account = this.preparationService.createAccountForCustomer(customerO);

        Customer customer = this.customerRepository.save(customerO);
        Customer customerBeneficiary = this.customerRepository.save(customerBeneficiaryO);

        assertNotNull(account.getId());

        TransactionDTO transaction1 = this.createTransactionDTO(account, customerBeneficiary.getId(), Currency.EUR.toString(), "2022-08-01T12:00:00", 1000);
        TransactionDTO transaction2 = this.createTransactionDTO(account, customerBeneficiary.getId(), Currency.EUR.toString(), "2022-08-03T12:00:00", 200);
        TransactionDTO transaction3 = this.createTransactionDTO(account, customer.getId(), Currency.USD.toString(), "2022-08-05T12:00:00", 500);
        TransactionDTO transaction4 = this.createTransactionDTO(account, customer.getId(), Currency.USD.toString(), "2022-08-07T12:00:00", 10000);
        TransactionDTO transaction5 = this.createTransactionDTO(account, customer.getId(), Currency.EUR.toString(), "2022-08-09T12:00:00", 4000);

        List<TransactionDTO> transactionDTOS = new ArrayList<TransactionDTO>();
        transactionDTOS.add(transaction1);
        transactionDTOS.add(transaction2);
        transactionDTOS.add(transaction3);
        transactionDTOS.add(transaction4);
        transactionDTOS.add(transaction5);

        List<Transaction> transactions1 = balanceService.importTransactions(transactionDTOS);

        assertEquals(5, transactions1.size());

        double expectedBalanceEUR = 2800;
        double expectedBalanceUSD = 10500;

        List<BalanceResponseDTO> balances = balanceService.calculateBalances(new BankStatementRequest(Arrays.asList(account.getId()), null, null));

        assertEquals(2, balances.size());

        for (BalanceResponseDTO balanceResponseDTO: balances) {
            if (balanceResponseDTO.getCurrency().equals(Currency.EUR)) {
                assertEquals(balanceResponseDTO.getBalance(), expectedBalanceEUR);
            } else if (balanceResponseDTO.getCurrency().equals(Currency.USD)) {
                assertEquals(balanceResponseDTO.getBalance(), expectedBalanceUSD);
            }
        }
    }

    private List<TransactionDTO> getTransactionDTOs(Account account, Long beneficiaryId, String currency) {
        List<TransactionDTO> transactions = new ArrayList<>();

        transactions.add(this.createTransactionDTO(account, beneficiaryId, currency, "2022-09-01T12:00:00"));
        transactions.add(this.createTransactionDTO(account, beneficiaryId, currency, "2022-09-03T12:00:00"));
        transactions.add(this.createTransactionDTO(account, beneficiaryId, currency, "2022-09-05T12:00:00"));

        return transactions;
    }

    public TransactionDTO createTransactionDTO(Account account, Long beneficiaryId, String currency, String date) {
        return new TransactionDTO(
                account.getId(),
                date,
                beneficiaryId,
                "Test comment3",
                this.generateRandomAmount(),
                currency
        );
    }

    public TransactionDTO createTransactionDTO(Account account, Long beneficiaryId, String currency, String date, double amount) {
        return new TransactionDTO(
                account.getId(),
                date,
                beneficiaryId,
                "Test comment3",
                amount,
                currency
        );
    }

    public double calculateBalance(List<TransactionDTO> transactionDTOS, Customer customer) {
        double amount = 0;
        for (TransactionDTO transactionDTO: transactionDTOS) {
            if (Objects.equals(transactionDTO.getCustomer(), customer.getId())) {
                amount += transactionDTO.getAmount();
            } else {
                amount -= transactionDTO.getAmount();
            }
        }

        return amount;
    }

    private double generateRandomAmount() {
        int min = 200;
        int max = 5000;
        return Math.random()*(max - min + 1) + min;
    }

}
