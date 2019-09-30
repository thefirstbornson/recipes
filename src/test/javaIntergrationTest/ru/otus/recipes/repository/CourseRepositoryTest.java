package ru.otus.recipes.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.RecipeTestConfiguration;
import ru.otus.recipes.domain.Course;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan({"ru.otus.recipes.repository"})
@TestPropertySource("classpath:application-test.properties")
class CourseRepositoryTest {
    private static final String COURSE_NAME= "аперитив";

    @Autowired
    CourseRepository courseRepository;
    Course course;

    @BeforeEach
    public void setUp(){
        course = new Course(0,COURSE_NAME);
    }

    @Test
    public void saveCourseTest(){
        courseRepository.save(course);
        assertTrue(course.getId()>0);
    }
}