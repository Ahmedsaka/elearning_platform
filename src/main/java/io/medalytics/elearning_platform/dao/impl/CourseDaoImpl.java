package io.medalytics.elearning_platform.dao.impl;

import io.medalytics.elearning_platform.dao.AbstractBaseDao;
import io.medalytics.elearning_platform.dao.CourseDao;
import io.medalytics.elearning_platform.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class CourseDaoImpl extends AbstractBaseDao<Course> implements CourseDao {
    private SimpleJdbcCall findCoursesByInstructorName, findCoursesByCourseName, createCourseStudent;

    @Autowired
    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        this.create = new SimpleJdbcCall(jdbcTemplate).withProcedureName("sp_create_course").withReturnValue();
        this.createCourseStudent = new SimpleJdbcCall(jdbcTemplate).withProcedureName("sp_create_course_student").withReturnValue();
        this.find = new SimpleJdbcCall(jdbcTemplate).withProcedureName("sp_find_course_by_id")
                .returningResultSet(SINGLE_RESULT, BeanPropertyRowMapper.newInstance(Course.class));
        this.findCoursesByInstructorName = new SimpleJdbcCall(jdbcTemplate).withProcedureName("sp_find_course_by_instructor_name")
                .returningResultSet(MULTIPLE_RESULT, BeanPropertyRowMapper.newInstance(Course.class));
        this.findCoursesByCourseName = new SimpleJdbcCall(jdbcTemplate).withProcedureName("sp_find_course_by_name")
                .returningResultSet(MULTIPLE_RESULT, BeanPropertyRowMapper.newInstance(Course.class));
        this.findAll = new SimpleJdbcCall(jdbcTemplate).withProcedureName("sp_find_all_courses")
                .returningResultSet(MULTIPLE_RESULT, BeanPropertyRowMapper.newInstance(Course.class));
    }

    @Override
    public List<Course> findCoursesByInstructorName(String instructorName) throws DataAccessException {
        SqlParameterSource in = new MapSqlParameterSource().addValue("instructor_name", instructorName);
        Map<String, Object> m = findCoursesByInstructorName.execute(in);
        List<Course> result = (List<Course>) m.get(MULTIPLE_RESULT);
        return result;
    }

    @Override
    public List<Course> findCoursesByCourseName(String courseName) throws DataAccessException {
        SqlParameterSource in = new MapSqlParameterSource().addValue("course_name", courseName);
        Map<String, Object> m = findCoursesByCourseName.execute(in);
        List<Course> result = (List<Course>) m.get(MULTIPLE_RESULT);
        return result;
    }

    @Override
    public Long addCourseStudent(Long course_id, Long student_id) throws DataAccessException {
        SqlParameterSource in =  new MapSqlParameterSource()
                .addValue("course_id", course_id)
                .addValue("student_id", student_id);
        Map<String, Object> m = createCourseStudent.execute(in);
        return (long)m.get("id");
    }


}
