package io.medalytics.elearning_platform.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Course extends BaseModel {
    private String name;
    private String instructorName;
    private Date createdDate;
    private String locationId;
}
