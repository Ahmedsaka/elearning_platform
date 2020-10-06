package io.medalytics.elearning_platform.service.impl;

import io.medalytics.elearning_platform.dao.CourseDao;
import io.medalytics.elearning_platform.model.AdminUser;
import io.medalytics.elearning_platform.model.Course;
import io.medalytics.elearning_platform.model.request.CourseRequest;
import io.medalytics.elearning_platform.service.AdminUserService;
import io.medalytics.elearning_platform.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseDao courseDao;
    private AdminUserService adminUserService;

    @Autowired
    public CourseServiceImpl(CourseDao courseDao, AdminUserService adminUserService) {
        this.courseDao = courseDao;
        this.adminUserService = adminUserService;
    }

    @Override
    public Optional<Course> createCourse(CourseRequest request) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//        AdminUser user = adminUserService.findUserByEmail((String) authentication.getPrincipal()).get();

        return Optional.ofNullable(
                Course.builder()
                .courseName(request.getCourseName())
                        .instructorName("")
//                .instructorName(String.format("%s %s", user.getFirstName(), user.getLastName()))
//                .locationId("")
//                .courseContentLocation("")
                .build()
        );
    }

    @Override
    public Optional<List<Course>> findCoursesByInstructorName(String instructorName) {
        return Optional.ofNullable(courseDao.findCoursesByInstructorName(instructorName));
    }

    @Override
    public Optional<List<Course>> findByCoursesByName(String courseName) {
        return Optional.ofNullable(courseDao.findCoursesByCourseName(courseName));
    }

    @Override
    public Optional<List<Course>> findAll() {
        return Optional.ofNullable(courseDao.findAll());
    }
}
