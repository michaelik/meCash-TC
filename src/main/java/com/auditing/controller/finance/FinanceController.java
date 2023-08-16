package com.auditing.controller.finance;

import com.auditing.payload.request.FundAccountRequest;
import com.auditing.payload.request.TransferRequest;
import com.auditing.payload.response.AccountDetailResponse;
import com.auditing.payload.response.FundAccountResponse;
import com.auditing.payload.response.TransactionHistoryResponse;
import com.auditing.payload.response.TransferResponse;
import com.auditing.payload.response.UserDetailResponse;
import com.auditing.service.AccountService;
import com.auditing.service.TransactionService;
import com.auditing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Validated
public class FinanceController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping(path = "/user-detail/{id}", consumes = {MediaType.ALL_VALUE}, produces = "application/json")
    public ResponseEntity<UserDetailResponse> getUserDetail(
            @PathVariable("id") Integer userId)
    {
        return new ResponseEntity<>(userService.getUserDetail(userId), HttpStatus.OK);
    }

    @PostMapping(path = "/fund-account/{id}", consumes = {MediaType.ALL_VALUE}, produces = "application/json")
    public ResponseEntity<FundAccountResponse> fundAccount (
            @PathVariable("id") Integer userId,
            @RequestBody @Valid FundAccountRequest request){
        accountService.fundAccount(userId, request);
        FundAccountResponse fundAccountResponse = FundAccountResponse.builder()
                .createdAt(LocalDateTime.now())
                .status("Success")
                .build();
        return new ResponseEntity<>(fundAccountResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/account-balance/{id}", consumes = {MediaType.ALL_VALUE}, produces = "application/json")
    public ResponseEntity<AccountDetailResponse> getAccountBalance(
            @PathVariable("id") Integer userId){
        return new ResponseEntity<>(accountService.getAccountBalance(userId), HttpStatus.OK);
    }

    @PostMapping(path = "/transfer/{id}", consumes = {MediaType.ALL_VALUE}, produces = "application/json")
    public ResponseEntity<TransferResponse> makeTransfer (
            @PathVariable("id") Integer userId,
            @Valid @RequestBody TransferRequest request) {
        transactionService.makeTransfer(userId, request);
        TransferResponse transferResponse = TransferResponse.builder()
                .timeframes(LocalDateTime.now())
                .status("Success")
                .build();
        return  new ResponseEntity<>(transferResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/transaction-history/{id}", produces = "application/json")
    public ResponseEntity<List<TransactionHistoryResponse>> getAllTransactionHistory(
            @PathVariable("id") Integer userId){
        return new ResponseEntity<>(transactionService.getTransactionHistory(userId), HttpStatus.OK);
    }
}
