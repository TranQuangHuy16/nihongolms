package me.quanghuy.nihongolms.dto.user;

import lombok.*;
import me.quanghuy.nihongolms.domain.user.UserRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String username;

    private String email;

    private String phoneNumber;

    private String displayName;

    private String avatarUrl;

    private UserRole role;

    private boolean active = true;
}
