package com.auditing.service;

import com.auditing.payload.request.TransferRequest;
import com.auditing.payload.response.TransactionHistoryResponse;

import java.util.List;

public interface TransactionService {
    void makeTransfer(Integer userId, TransferRequest request);

    List<TransactionHistoryResponse> getTransactionHistory(Integer userId);
}
