package com.auditing.payload.response;

import com.auditing.enums.AccountCurrency;
import com.auditing.enums.Gender;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailResponse {
    String name;

    String email;

    Integer age;

    Gender gender;

    String accountNumber;

    AccountCurrency accountCurrency;
}
