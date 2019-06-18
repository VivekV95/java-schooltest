package com.lambdaschool.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.service.CourseService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CourseController.class, secure = false)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    private ArrayList<Course> courseList;

    @Before
    public void setUp() throws Exception {
        courseList = new ArrayList<>();
        /*
        VALUES (1, 'Data Science', 1),
           (2, 'JavaScript', 1),
           (3, 'Node.js',  1),
           (4, 'Java Back End', 2),
           (5, 'Mobile IOS', 2),
           (6, 'Mobile Android',  3);
         */
        Instructor instructor1 = new Instructor("Sally");
        instructor1.setInstructid(1);
        Instructor instructor2 = new Instructor("Lucy");
        instructor2.setInstructid(2);
        Instructor instructor3 = new Instructor("Charlie");
        instructor2.setInstructid(3);

        Course course1 = new Course("Data Science", instructor1);
        course1.setCourseid(1);
        Course course2 = new Course("JavaScript", instructor1);
        course1.setCourseid(2);
        Course course3 = new Course("Node.js", instructor1);
        course1.setCourseid(3);
        Course course4 = new Course("Java Back End", instructor2);
        course1.setCourseid(4);
        Course course5 = new Course("Mobile IOS", instructor2);
        course1.setCourseid(5);
        Course course6 = new Course("Mobile Android", instructor3);
        course1.setCourseid(6);

        courseList.add(course1);
        courseList.add(course2);
        courseList.add(course3);
        courseList.add(course4);
        courseList.add(course5);
        courseList.add(course6);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void listAllCourses() throws Exception{
        String apiUrl = "/courses/courses";
        Mockito.when(courseService.findAll()).thenReturn(courseList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(courseList);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void addNewCourse() throws Exception {
        String apiUrl = "/courses/course/add";
        Course course = new Course("Calculus", new Instructor("Jim"));

        Mockito.when(courseService.save(any(Course.class))).thenReturn(course);

        ObjectMapper mapper = new ObjectMapper();
        String courseString = mapper.writeValueAsString(course);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(courseString);

        mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }
}