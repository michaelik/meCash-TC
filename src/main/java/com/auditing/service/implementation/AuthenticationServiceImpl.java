package com.auditing.service.implementation;

import com.auditing.enums.Gender;
import com.auditing.exception.DuplicateResourceException;
import com.auditing.jwt.JWTUtilService;
import com.auditing.model.User;
import com.auditing.payload.request.SignInRequest;
import com.auditing.payload.request.SignUpRequest;
import com.auditing.payload.response.SignInResponse;
import com.auditing.repository.AuthenticationRepository;
import com.auditing.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationRepository authenticationRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtilService jwtUtilService;

    @Override
    public void signUp(SignUpRequest request) {
        boolean userEmailExist = authenticationRepository
                .findByEmail(request.getEmail())
                .isPresent();
        if (userEmailExist) throw new DuplicateResourceException("Email Already Taken");
        Gender gender = Gender.valueOf(request.getGender());

        String password = passwordEncoder.encode(request.getPassword());
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(password)
                .age(request.getAge())
                .gender(gender)
                .build();

        authenticationRepository.save(user);
    }

    @Override
    public SignInResponse signIn(SignInRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User principal = (User) authentication.getPrincipal();
        List<String> roles = principal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        String token = jwtUtilService.issueToken(principal.getUsername(), roles);
        return new SignInResponse(
                token,
                principal.getId(),
                principal.getName(),
                principal.getEmail(),
                principal.getGender(),
                principal.getAge(),
                roles,
                principal.getUsername()
        );
    }

}
