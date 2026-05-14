package me.quanghuy.nihongolms.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequest {
    private String currentPassword;

    private String newPassword;

    private String confirmPassword;
}
