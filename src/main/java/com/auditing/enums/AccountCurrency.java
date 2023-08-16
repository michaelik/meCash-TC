package com.auditing.enums;

import javax.validation.constraints.Pattern;

public enum AccountCurrency {
    A("A"),
    B("B");

    @Pattern(regexp = "[AB]")
    private final String currency;

    AccountCurrency(String currency){
        this.currency = currency;
    }
}
