package me.quanghuy.nihongolms.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest {
    private String email;

    private String phoneNumber;

    private String displayName;

    private String avatarUrl;
}
