package com.auditing.service;

import com.auditing.enums.AccountCurrency;
import com.auditing.model.Account;
import com.auditing.payload.request.FundAccountRequest;
import com.auditing.payload.response.AccountDetailResponse;

public interface AccountService {
    Account createAccount(AccountCurrency currency);

    void fundAccount(Integer userId, FundAccountRequest request);

    AccountDetailResponse getAccountBalance(Integer userId);
}
