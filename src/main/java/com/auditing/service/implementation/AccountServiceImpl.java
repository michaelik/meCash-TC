package com.auditing.service.implementation;

import com.auditing.enums.AccountCurrency;
import com.auditing.model.Account;
import com.auditing.payload.request.FundAccountRequest;
import com.auditing.payload.response.AccountDetailResponse;
import com.auditing.repository.AccountRepository;
import com.auditing.repository.UserRepository;
import com.auditing.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public Account createAccount(AccountCurrency currency) {
        String generatedAN = generateTenDigitAccountNumber();
        if(accountRepository.existsByAccountNumber(generatedAN))
            generatedAN = generateTenDigitAccountNumber();
        return Account.builder()
                .accountNumber(generatedAN)
                .balance(BigDecimal.valueOf(0.00))
                .currency(currency)
                .build();
    }

    @Override
    public void fundAccount(FundAccountRequest request) {

    }

    @Override
    public AccountDetailResponse getAccountBalance() {
        return null;
    }

    private static String generateTenDigitAccountNumber(){
        return IntStream.generate(() -> new Random().nextInt(10))
                .limit(10)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }
}
