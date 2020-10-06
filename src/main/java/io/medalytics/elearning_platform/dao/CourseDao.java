package io.medalytics.elearning_platform.dao;

import io.medalytics.elearning_platform.model.Course;

import java.util.List;

public interface CourseDao extends BaseDao<Course> {
    List<Course> findCoursesByInstructorName(String instructorName);
    List<Course> findCoursesByCourseName(String courseName);
    Long addCourseStudent(Long course_id, Long student_id);
}
