package com.example.testtask.auth;

import com.example.testtask.conf.JwtService;
import com.example.testtask.exception.NotUniqueException;
import com.example.testtask.model.User;
import com.example.testtask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.testtask.util.DataValid.EMAIL_NOT_UNIQ;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);
        if (user != null) {
            throw new NotUniqueException(
                    String.format(EMAIL_NOT_UNIQ, user.getEmail())
            );
        }

        user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
        String token = jwtService.generateToken(
                modelMapper.map(user, CustomUserDetails.class)
        );
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(
                modelMapper.map(
                        userRepository.findByEmail(request.getEmail()),
                        CustomUserDetails.class
                )
        );
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
