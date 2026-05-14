package me.quanghuy.nihongolms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.quanghuy.nihongolms.core.api.ApiResponse;
import me.quanghuy.nihongolms.dto.user.UserResponse;
import me.quanghuy.nihongolms.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
