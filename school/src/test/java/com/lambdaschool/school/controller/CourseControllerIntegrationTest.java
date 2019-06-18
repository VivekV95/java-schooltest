package com.lambdaschool.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.school.model.Course;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.number.OrderingComparison.lessThan;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @After
    public void tearDown() throws Exception {
    }

    // GET /courses/courses
    @Test
    public void whenMeasuredResponseTime() {
        given().when().get("/courses/courses").then().time(lessThan(5000L));
    }

    @Test
    public void addNewCourse() throws Exception
    {
        Course cs = new Course("My New Course", null);
        cs.setCourseid(17);

        ObjectMapper mapper = new ObjectMapper();
        String stringCS = mapper.writeValueAsString(cs);

        given().contentType("application/json")
                .body(stringCS).when()
                .post("/courses/course/add").then().statusCode(201);

    }
}
