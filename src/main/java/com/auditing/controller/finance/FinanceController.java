package com.auditing.controller.finance;

import com.auditing.payload.response.UserDetailResponse;
import com.auditing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Validated
public class FinanceController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/user-detail", produces = "application/json")
    public ResponseEntity<UserDetailResponse> getUserDetail(){
        return new ResponseEntity<>(userService.getUserDetail(), HttpStatus.OK);
    }
}
