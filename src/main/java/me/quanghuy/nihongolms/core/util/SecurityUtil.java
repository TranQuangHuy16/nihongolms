package me.quanghuy.nihongolms.core.util;

import me.quanghuy.nihongolms.security.CustomUserDetails;

public class SecurityUtil {
    public static CustomUserDetails getCurrentUser() {
        var authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("Người dùng chưa đăng nhập");
        }

        return (CustomUserDetails) authentication.getPrincipal();
    }
}
