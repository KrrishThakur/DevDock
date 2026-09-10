package com.devdock.controller;

import com.devdock.dto.ChangePasswordRequest;
import com.devdock.dto.UpdateProfileRequest;
import com.devdock.dto.UserDTO;
import com.devdock.security.UserPrincipal;
import com.devdock.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Users", description = "User profile and settings management")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user profile")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(userService.getCurrentUser(userPrincipal));
    }

    @PutMapping("/me")
    @Operation(summary = "Update current user profile")
    public ResponseEntity<UserDTO> updateProfile(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                @Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(userService.updateProfile(userPrincipal, request));
    }

    @PutMapping("/me/password")
    @Operation(summary = "Change password")
    public ResponseEntity<Map<String, String>> changePassword(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                              @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(userPrincipal, request);
        return ResponseEntity.ok(Collections.singletonMap("message", "Password changed successfully"));
    }
}
