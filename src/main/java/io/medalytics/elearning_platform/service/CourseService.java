package io.medalytics.elearning_platform.service;

import io.medalytics.elearning_platform.model.Course;
import io.medalytics.elearning_platform.model.request.CourseRequest;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    Optional<Course> createCourse(CourseRequest request);
    Optional<List<Course>> findCoursesByInstructorName(String instructorName);
    Optional<List<Course>> findByCoursesByName(String courseName);
    Optional<List<Course>> findAll();
}
