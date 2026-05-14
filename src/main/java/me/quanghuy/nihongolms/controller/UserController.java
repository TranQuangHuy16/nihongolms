package me.quanghuy.nihongolms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.quanghuy.nihongolms.core.api.ApiResponse;
import me.quanghuy.nihongolms.dto.user.ChangePasswordRequest;
import me.quanghuy.nihongolms.dto.user.UserResponse;
import me.quanghuy.nihongolms.dto.user.UserUpdateRequest;
import me.quanghuy.nihongolms.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "Uer API")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    @Operation(
            summary = "Lấy thông tin user",
            description = "Lấy thống tin của user đang đăng nhập"
    )
    public ApiResponse<UserResponse> getMe() {
        return ApiResponse.success(userService.getMe());
    }

    @GetMapping()
    @Operation(
            summary = "Lấy thông tin user tất cả user",
            description = "Lấy thống tin của tất cả các user trong hệ thống"
    )
    public ApiResponse<List<UserResponse>> getAll() {
        return ApiResponse.success(userService.getAll());
    }

    @PutMapping()
    @Operation(
            summary = "Update thông tin của user",
            description = "Update thông tin của user đang đăng nhập"
    )
    private ApiResponse<UserResponse> update(@Valid @RequestBody UserUpdateRequest request) {
        return ApiResponse.success(userService.update(request));
    }

    @PostMapping("/change-password")
    @Operation(
            summary = "Đổi mật khẩu",
            description = "Đổi mật khẩu của user đang đăng nhập"
    )
    public ApiResponse<String> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
        return ApiResponse.success("Đổi mật khẩu thành công");
    }
}
