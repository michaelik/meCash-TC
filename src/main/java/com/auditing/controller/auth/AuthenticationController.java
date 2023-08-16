package com.auditing.controller.auth;

import com.auditing.jwt.JWTUtilService;
import com.auditing.payload.request.SignInRequest;
import com.auditing.payload.request.SignUpRequest;
import com.auditing.payload.response.SignInResponse;
import com.auditing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
@Validated
public class AuthenticationController {

    @Autowired
    private UserService authenticationService;
    @Autowired
    private JWTUtilService jwtUtilService;

    @PostMapping(path = "/register", consumes = {MediaType.ALL_VALUE}, produces = "application/json")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignUpRequest request) {
        authenticationService.signUp(request);
        String jwtToken = jwtUtilService.issueToken(request.getEmail(), "ROLE_USER");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
    }

    @PostMapping(path = "/login", consumes = {MediaType.ALL_VALUE}, produces = "application/json")
    public ResponseEntity<?> login(@RequestBody @Valid SignInRequest request) {
        SignInResponse response = authenticationService.signIn(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, response.getToken())
                .body(response);
    }
}
