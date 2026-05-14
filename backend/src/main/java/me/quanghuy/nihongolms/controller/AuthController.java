package me.quanghuy.nihongolms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.quanghuy.nihongolms.core.api.ApiResponse;
import me.quanghuy.nihongolms.dto.auth.AuthResponse;
import me.quanghuy.nihongolms.dto.auth.LoginRequest;
import me.quanghuy.nihongolms.dto.auth.RegisterRequest;
import me.quanghuy.nihongolms.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Đăng ký, đăng nhập, quản lý JWT token")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(
            summary = "Đăng ký tài khoản mới",
            description = "Tạo user mới và tự động trả về JWT token"
    )
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<AuthResponse>builder()
                        .status(201)
                        .message("Registration successful")
                        .data(response)
                        .build());
    }

    @PostMapping("/login")
    @Operation(
            summary = "Đăng nhập",
            description = "Xác thực bằng username/password, trả về access token và refresh token"
    )
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
