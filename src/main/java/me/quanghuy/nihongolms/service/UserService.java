package me.quanghuy.nihongolms.service;

import lombok.RequiredArgsConstructor;
import me.quanghuy.nihongolms.core.util.SecurityUtil;
import me.quanghuy.nihongolms.domain.user.User;
import me.quanghuy.nihongolms.dto.user.UserResponse;
import me.quanghuy.nihongolms.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public UserResponse getMe() {
        User user = SecurityUtil.getCurrentUser().getUser();

        if (user == null) {
            throw new RuntimeException("Không tìm thấy thông tin người dùng");
        }

        return mapToResponse(user);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .displayName(user.getDisplayName())
                .avatarUrl(user.getAvatarUrl())
                .role(user.getRole())
                .active(user.isActive())
                .build();
    }
}
