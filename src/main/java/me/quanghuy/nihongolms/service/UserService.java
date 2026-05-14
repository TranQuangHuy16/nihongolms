package me.quanghuy.nihongolms.service;

import lombok.RequiredArgsConstructor;
import me.quanghuy.nihongolms.core.util.SecurityUtil;
import me.quanghuy.nihongolms.domain.user.User;
import me.quanghuy.nihongolms.dto.user.ChangePasswordRequest;
import me.quanghuy.nihongolms.dto.user.UserResponse;
import me.quanghuy.nihongolms.dto.user.UserUpdateRequest;
import me.quanghuy.nihongolms.exception.ResourceNotFoundException;
import me.quanghuy.nihongolms.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getMe() {
        User user = userRepository.findById(this.getUserInfo().getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Không tìm thấy thông tin người dùng"));

        return mapToResponse(user);
    }

    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public UserResponse update(UserUpdateRequest request) {
        User user = userRepository.findById(this.getUserInfo().getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Không tìm thấy thông tin người dùng"));

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        if (request.getDisplayName() != null) {
            user.setDisplayName(request.getDisplayName());
        }

        if (request.getAvatarUrl() != null) {
            user.setAvatarUrl(request.getAvatarUrl());
        }

        return mapToResponse(userRepository.save(user));
    }

    public void changePassword(ChangePasswordRequest request) {
        User user = userRepository.findById(this.getUserInfo().getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Không tìm thấy thông tin người dùng"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Mật khẩu hiện tại không đúng");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Mật khẩu mới và xác nhận mật khẩu không khớp");
        }

        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    private User getUserInfo() {
        User user = SecurityUtil.getCurrentUser().getUser();

        return user;
    }


    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .displayName(user.getDisplayName())
                .avatarUrl(user.getAvatarUrl())
                .role(user.getRole())
                .active(user.isActive())
                .build();
    }
}
