package com.auditing.controller.finance;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Validated
public class FinanceController {

    @GetMapping("/authenticated")
    public String allAccess() {
        return "User's authenticated.";
    }
}
