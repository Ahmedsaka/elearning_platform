package io.medalytics.elearning_platform.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@Builder
public class Role extends BaseModel {
    private String roleName;
}
