package com.auditing.service;

import com.auditing.payload.request.SignInRequest;
import com.auditing.payload.request.SignUpRequest;
import com.auditing.payload.response.SignInResponse;
import org.springframework.stereotype.Component;

@Component
public interface AuthenticationService {
    void signUp(SignUpRequest request);

    SignInResponse signIn(SignInRequest request);
}
