package me.quanghuy.nihongolms.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private String displayName;

    private String avatarUrl;

    private UserRole role;

    private boolean active = true;
}
