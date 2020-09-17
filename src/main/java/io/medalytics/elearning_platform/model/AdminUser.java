package io.medalytics.elearning_platform.model;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter@Builder
public class AdminUser extends BaseModel {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date dateOfBirth;
}
