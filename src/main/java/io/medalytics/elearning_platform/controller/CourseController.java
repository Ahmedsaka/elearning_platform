package io.medalytics.elearning_platform.controller;

import io.medalytics.elearning_platform.model.request.CourseRequest;
import io.medalytics.elearning_platform.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/course")
public class CourseController {

    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<?> createCourse() {
        return null;
    }

    @GetMapping(value = "/course-name", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findCoursesByCourseName(@RequestBody CourseRequest request) {
        return new ResponseEntity<>(
                courseService.findByCoursesByName(request.getCourseName()),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/course-instructor", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findCoursesByInstructorName(@RequestBody CourseRequest request) {
        return new ResponseEntity<>(
                courseService.findCoursesByInstructorName(request.getInstructorName()),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(
                courseService.findAll(),
                HttpStatus.OK
        );
    }
}
