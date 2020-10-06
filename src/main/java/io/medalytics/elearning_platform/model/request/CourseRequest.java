package io.medalytics.elearning_platform.model.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseRequest {
    private String courseName;
    private String instructorName;
    private String courseContent;
}
