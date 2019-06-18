package com.lambdaschool.school.service;

import com.lambdaschool.school.SchoolApplication;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Instructor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolApplication.class)
public class CourseServiceImplTest {

    @Autowired
    private CourseService courseService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findCourseById() {
        assertEquals("Data Science", courseService.findCourseById(1).getCoursename());
    }

    @Test (expected = EntityNotFoundException.class)
    public void deleteFound() {
        courseService.delete(2);
        courseService.findCourseById(2);
        assertEquals(5, courseService.findAll().size());
    }

    @Test (expected = EntityNotFoundException.class)
    public void deleteNotFound() {
        courseService.delete(100);
        assertEquals(6, courseService.findAll().size());
    }

    @Test
    public void save() {
        Instructor instructor = new Instructor("Bill");
        Course course = new Course("Physics", instructor);
        course.setCourseid(7);

        Course newCourse = courseService.save(course);
        assertNotNull(newCourse);

        Course foundCourse = courseService.findCourseById(newCourse.getCourseid());
        assertEquals(newCourse.getCoursename(), foundCourse.getCoursename());
        assertEquals(instructor.getInstructname(), foundCourse.getInstructor().getInstructname());
    }
}