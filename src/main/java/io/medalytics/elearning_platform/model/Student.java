package io.medalytics.elearning_platform.model;

import io.medalytics.elearning_platform.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student extends BaseModel {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
}
