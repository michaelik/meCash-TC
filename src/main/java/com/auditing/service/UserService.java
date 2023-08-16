package com.auditing.service;

import com.auditing.payload.request.SignInRequest;
import com.auditing.payload.request.SignUpRequest;
import com.auditing.payload.response.SignInResponse;
import com.auditing.payload.response.UserDetailResponse;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    void signUp(SignUpRequest request);

    SignInResponse signIn(SignInRequest request);

    UserDetailResponse getUserDetail();
}
