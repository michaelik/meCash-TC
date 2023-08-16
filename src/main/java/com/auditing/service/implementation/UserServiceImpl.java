package com.auditing.service.implementation;

import com.auditing.enums.AccountCurrency;
import com.auditing.enums.Gender;
import com.auditing.exception.DuplicateResourceException;
import com.auditing.jwt.JWTUtilService;
import com.auditing.model.Account;
import com.auditing.model.User;
import com.auditing.payload.request.SignInRequest;
import com.auditing.payload.request.SignUpRequest;
import com.auditing.payload.response.SignInResponse;
import com.auditing.payload.response.UserDetailResponse;
import com.auditing.repository.AccountRepository;
import com.auditing.repository.UserRepository;
import com.auditing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtilService jwtUtilService;

    @Override
    public void signUp(SignUpRequest request) {
        boolean userEmailExist = userRepository
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
        AccountCurrency accountCurrency = AccountCurrency.valueOf(request.getAccountCurrency());
        Account account = accountService.createAccount(accountCurrency);
        account.setUser(user);
        user.setAccount(account);
        userRepository.save(user);
        accountRepository.save(account);
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

    @Override
    public UserDetailResponse getUserDetail() {
        return null;
    }

}
