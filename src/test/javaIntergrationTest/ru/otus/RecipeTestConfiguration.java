package ru.otus;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.otus.recipes.service.mapper.CourseMapper;

@TestConfiguration
public class RecipeTestConfiguration {
        @Bean
        public CourseMapper courseMapper() {
            return new CourseMapper();
        }

}
