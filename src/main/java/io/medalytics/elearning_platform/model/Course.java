package io.medalytics.elearning_platform.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Course extends BaseModel {
    private String courseName;
    private String instructorName;
//    private String locationId;
    private String courseContentLocation;
}
