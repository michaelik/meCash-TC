package com.auditing.payload.response;

import com.auditing.enums.Gender;
import com.auditing.model.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignInResponse {
    String token;
    Integer id;
    String name;
    String email;
    Gender gender;
    Integer age;
    List<String> roles;
    String username;
}
