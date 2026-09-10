package com.devdock.service;

import com.devdock.dto.AuthRequest;
import com.devdock.dto.AuthResponse;
import com.devdock.dto.RegisterRequest;
import com.devdock.dto.UserDTO;
import com.devdock.entity.Role;
import com.devdock.entity.User;
import com.devdock.exception.BadRequestException;
import com.devdock.repository.UserRepository;
import com.devdock.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail().trim().toLowerCase())) {
            throw new BadRequestException("An account with email " + request.getEmail() + " already exists.");
        }

        User user = new User(
            request.getName().trim(),
            request.getEmail().trim().toLowerCase(),
            passwordEncoder.encode(request.getPassword()),
            Role.ROLE_USER
        );

        User savedUser = userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail().trim().toLowerCase(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        UserDTO userDTO = new UserDTO(
            savedUser.getId(),
            savedUser.getName(),
            savedUser.getEmail(),
            savedUser.getRole(),
            savedUser.getAvatarUrl(),
            savedUser.getBio(),
            savedUser.getCreatedAt()
        );

        return new AuthResponse(jwt, userDTO);
    }

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail().trim().toLowerCase(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        User user = userRepository.findByEmail(request.getEmail().trim().toLowerCase())
            .orElseThrow(() -> new BadRequestException("User not found"));

        UserDTO userDTO = new UserDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole(),
            user.getAvatarUrl(),
            user.getBio(),
            user.getCreatedAt()
        );

        return new AuthResponse(jwt, userDTO);
    }
}
