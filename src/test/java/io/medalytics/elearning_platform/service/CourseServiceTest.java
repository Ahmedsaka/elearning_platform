package io.medalytics.elearning_platform.service;

import io.medalytics.elearning_platform.dao.CourseDao;
import io.medalytics.elearning_platform.dao.impl.CourseDaoImpl;
import io.medalytics.elearning_platform.model.Course;
import io.medalytics.elearning_platform.service.impl.AdminUserServiceImpl;
import io.medalytics.elearning_platform.service.impl.CourseServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @InjectMocks
    private CourseServiceImpl courseService;
    @Mock
    private CourseDaoImpl courseDao;
    @Mock
    private AdminUserServiceImpl adminUserService;

    @BeforeAll
    void init () {
        this.courseService = new CourseServiceImpl(courseDao, adminUserService);
    }

    @Test
    void testCreateCourse() {

    }

    @Test
    void testFindCoursesByInstructorName() {
        Course course = Course.builder()
                .courseName("Building great applications in java")
                .instructorName("admin admin")
                .courseContentLocation("")
                .build();

        when(courseDao.findCoursesByInstructorName(anyString())).thenReturn(Collections.singletonList(course));
        assertThat(courseService.findCoursesByInstructorName("admin").get()).isNotEmpty();
    }

    @Test
    void testFindCoursesByCourseName() {
        Course course = Course.builder()
                .courseName("Building great applications in java")
                .instructorName("admin admin")
                .courseContentLocation("")
                .build();

        when(courseDao.findCoursesByCourseName(anyString())).thenReturn(Collections.singletonList(course));
        assertThat(courseService.findByCoursesByName("admin").get()).isNotEmpty();
    }

}