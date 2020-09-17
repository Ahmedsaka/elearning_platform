package io.medalytics.elearning_platform.model.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AdminUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String roleName;
    private String dateOfBirth;
}
