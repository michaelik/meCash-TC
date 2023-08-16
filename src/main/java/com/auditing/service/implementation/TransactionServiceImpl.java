package com.auditing.service.implementation;

import com.auditing.enums.AccountCurrency;
import com.auditing.exception.IllegalTransferException;
import com.auditing.exception.InsufficientBalanceException;
import com.auditing.exception.TransactionFailedException;
import com.auditing.exception.UserNotFoundException;
import com.auditing.model.Account;
import com.auditing.model.Transaction;
import com.auditing.model.User;
import com.auditing.payload.request.TransferRequest;
import com.auditing.payload.response.TransactionHistoryResponse;
import com.auditing.repository.AccountRepository;
import com.auditing.repository.TransactionRepository;
import com.auditing.repository.UserRepository;
import com.auditing.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.auditing.enums.AccountCurrency.A;
import static com.auditing.enums.AccountCurrency.B;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @SneakyThrows
    @Override
    public void makeTransfer(Integer id, TransferRequest request) {
        Account recipientAccount = accountRepository
                .findAccountByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException(
                        "Account [%s] Not Found".formatted(request.getAccountNumber())
                ));

        User recipient = Optional.ofNullable(recipientAccount.getUser())
                .orElseThrow(()-> new UserNotFoundException(
                        "Recipient [%s] Not Found".formatted(recipientAccount.getUser())
                ));

        User debit = userRepository
                .findById(id)
                .orElseThrow(()-> new UserNotFoundException(
                        "User with id [%s] not Found".formatted(id)
                ));
        Account debitAccount = accountRepository.findAccountByUser(debit);

        if (recipient.equals(debit)){
            throw new IllegalTransferException("Can not transfer to self");
        }
        if (debitAccount.getBalance().compareTo(request.getAmount()) <= 0) {
            throw new InsufficientBalanceException("Insufficient Funds");
        }
        BigDecimal amount = currencyToCurrencyRate(request.getAmount(),
                debitAccount.getCurrency(),
                recipientAccount.getCurrency());
        saveTransactionDetail(debitAccount.getAccountNumber(), recipient.getName(),
                recipientAccount.getAccountNumber(),
                request.getAmount(), amount, debitAccount.getBalance(),
                debit, debitAccount, recipientAccount.getBalance(), recipient,
                recipientAccount);
        try {
            debitAccount.setBalance(debitAccount.getBalance().subtract(request.getAmount()));
            recipientAccount.setBalance(recipientAccount.getBalance().add(amount));
            accountRepository.save(debitAccount);
            accountRepository.save(recipientAccount);
        } catch (Exception e) {
            throw new TransactionFailedException("Transaction Failed");
        }
    }

    @Override
    public List<TransactionHistoryResponse> getTransactionHistory(Integer id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(()-> new UserNotFoundException(
                        "User with id [%s] not Found".formatted(id)
                ));
        Account debitAccount = accountRepository.findAccountByUser(user);
        List<Transaction> transactions = transactionRepository.findAllByAccount(debitAccount);
        List<TransactionHistoryResponse> response = new ArrayList<>();
        for(Transaction transaction : transactions){
            AccountCurrency currency = transaction.getAccount().getCurrency();
            response.add(TransactionHistoryResponse.builder()
                    .transactionId(transaction.getTransactionId())
                    .debitAccount(transaction.getDebitAccount())
                    .recipient(transaction.getRecipientName() + " | " +
                            transaction.getRecipientAccount())
                    .amount(currency+""+transaction.getAmount())
                    .balance(currency+""+transaction.getCurrentBalance())
                    .timestamp(transaction.getTimestamp())
                    .build());
        }
        return response;
    }

    private BigDecimal currencyToCurrencyRate(BigDecimal debitAmount,
                                              AccountCurrency debitCurrency,
                                              AccountCurrency recipientCurrency){
        BigDecimal amount = null;
        if (debitCurrency.equals(A) && recipientCurrency.equals(B))
        {
            amount =  debitAmount.multiply(BigDecimal.valueOf(1.3455), MathContext.DECIMAL32);
        } else if (debitCurrency.equals(B) && recipientCurrency.equals(A))
        {
            amount = debitAmount.divide(BigDecimal.valueOf(1.3455), MathContext.DECIMAL32);
        }
        return amount;
    }

    private void saveTransactionDetail(String debitAccountNumber,
                                       String recipientName,
                                       String recipientAccount,
                                       BigDecimal requestAmount,
                                       BigDecimal amount,
                                       BigDecimal debitBalance,
                                       User debitUser,
                                       Account debitAccount,
                                       BigDecimal creditBalance,
                                       User creditUser,
                                       Account creditAccount){
        Transaction debit = Transaction.builder()
                .debitAccount(debitAccountNumber)
                .recipientName(recipientName)
                .recipientAccount(recipientAccount)
                .amount(requestAmount)
                .currentBalance(debitBalance)
                .user(debitUser)
                .account(debitAccount)
                .build();
        Transaction credit = Transaction.builder()
                .debitAccount(debitAccountNumber)
                .recipientName(recipientName)
                .recipientAccount(recipientAccount)
                .amount(amount)
                .currentBalance(creditBalance)
                .user(creditUser)
                .account(creditAccount)
                .build();
        transactionRepository.save(debit);
        transactionRepository.save(credit);
    }
}
