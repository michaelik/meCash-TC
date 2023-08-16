package com.auditing.controller.finance;

import com.auditing.payload.request.FundAccountRequest;
import com.auditing.payload.response.AccountDetailResponse;
import com.auditing.payload.response.FundAccountResponse;
import com.auditing.payload.response.UserDetailResponse;
import com.auditing.service.AccountService;
import com.auditing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Validated
public class FinanceController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @GetMapping(path = "/user-detail/{id}", produces = "application/json")
    public ResponseEntity<UserDetailResponse> getUserDetail(
            @PathVariable("id") Integer userId)
    {
        return new ResponseEntity<>(userService.getUserDetail(userId), HttpStatus.OK);
    }

    @PostMapping(path = "/fund-account/{id}")
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

    @GetMapping(path = "/account-balance/{id}", produces = "application/json")
    public ResponseEntity<AccountDetailResponse> getAccountBalance(
            @PathVariable("id") Integer userId){
        return new ResponseEntity<>(accountService.getAccountBalance(userId), HttpStatus.OK);
    }
}
