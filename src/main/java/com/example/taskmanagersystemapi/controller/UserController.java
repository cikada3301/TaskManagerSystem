package com.example.taskmanagersystemapi.controller;

import com.example.taskmanagersystemapi.dto.UserAuthenticationDto;
import com.example.taskmanagersystemapi.dto.UserRegistrationDto;
import com.example.taskmanagersystemapi.security.JwtUtil;
import com.example.taskmanagersystemapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Operation(
            description = "Endpoint for authentication",
            summary = "Request for authentication",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Bad request, if email and password are incorrect",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping("/authentication")
    public ResponseEntity<String> authenticate(@Valid @RequestBody UserAuthenticationDto userAuthenticationDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userAuthenticationDto.getEmail(), userAuthenticationDto.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(userAuthenticationDto.getEmail());

        if (userDetails == null) {
            throw new BadCredentialsException("Incorrect email or password");
        }

        return ResponseEntity.ok(jwtUtil.generateToken(userDetails));
    }

    @Operation(
            description = "Endpoint for registration",
            summary = "Request for registration and save new user",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Bad request, if email, username password are not valid",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping("/registration")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        userService.register(userRegistrationDto);

        return ResponseEntity.ok().build();
    }
}
