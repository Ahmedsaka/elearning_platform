package io.medalytics.elearning_platform.dao;

import io.medalytics.elearning_platform.DataSourceConfig;
import io.medalytics.elearning_platform.dao.impl.AdminUserDaoImpl;
import io.medalytics.elearning_platform.dao.impl.CourseDaoImpl;
import io.medalytics.elearning_platform.dao.impl.RoleDaoImpl;
import io.medalytics.elearning_platform.model.Course;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {CourseDaoImpl.class, DataSourceConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CourseDaoTest {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void init(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, "course", "course_student");
    }

    @Test
    void testCreateCourse() {
        Course course = Course.builder()
                .courseName("Building great applications in java")
                .instructorName("admin admin")
                .courseContentLocation("")
                .build();

        Course testCourse = courseDao.create(course);

        assertThat(testCourse.getCourseName()).isEqualTo(course.getCourseName());
        assertThat(testCourse.getCourseContentLocation()).isEqualTo(course.getCourseContentLocation());
        assertThat(testCourse.getInstructorName()).isEqualTo(course.getInstructorName());
    }

    @Test
    void testFindCoursesByCourseName() {
        List<Course> courses = courseDao.findCoursesByCourseName("java");
        assertThat(courses.size()).isGreaterThan(0);
    }

    @Test
    void testFindCoursesByInstructorName() {
        List<Course> courses = courseDao.findCoursesByInstructorName("admin admin");
        assertThat(courses.size()).isGreaterThan(0);
    }

    @Test
    void testFindAll() {
        List<Course> courses = courseDao.findAll();
        assertThat(courses.size()).isGreaterThan(0);
    }
}