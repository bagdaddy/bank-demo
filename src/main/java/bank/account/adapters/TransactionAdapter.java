package bank.account.adapters;

import bank.account.DTO.parseable.TransactionDTO;
import bank.account.models.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionAdapter {
    public List<TransactionDTO> modelsToDTOs(List<Transaction> transactions) {
        List<TransactionDTO> transactionDTOS = new ArrayList<>();

        for(Transaction transaction : transactions) {
            transactionDTOS.add(this.modelToDTO(transaction));
        }

        return transactionDTOS;
    }

    public TransactionDTO modelToDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getAccount().getId(),
                transaction.getOperationDate().toString(),
                transaction.getCustomer().getId(),
                transaction.getComment(),
                transaction.getAmount(),
                transaction.getCurrency().toString()
        );
    }
}
