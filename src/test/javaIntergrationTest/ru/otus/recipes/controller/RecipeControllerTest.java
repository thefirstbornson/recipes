package ru.otus.recipes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.MappingException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.exception.EntityMapperException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.service.RecipeService;

import java.util.*;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers  = RecipeController.class)
class RecipeControllerTest {

    private static final String NAME = "testRecipe";
    private static final String DESCRIPTION = "testDescription";
    private static final String INSTRUCTIONS = "testInstructions";
    private static final Integer COOK_TIME = 30;
    private static final Integer LEVEL_ID = 1;
    private static final Integer CUISINE_ID = 1;
    private static final Integer RATING = 1;
    private static final String IMAGE_PATH = "";
    private static final List<Long> COURSE_ID_LIST = Arrays.asList(2L,1L);
    private static final List<Long> FOOD_CATEGORY_ID_LIST = Arrays.asList(1L,2L);
    private static final List<Long> MEAL_ID_LIST = Arrays.asList(2L,1L);
    private static final Long AMOUNT = 200L;
    private static final Long MEASUREMENT_ID = 1L;
    private static final String UPDATED_NAME = "anotherTestRecipe";
    private static final String URL_TEMPLATE = "/recipes";
    private static final String EXPECTED_CONTENT ="{\"id\":1,\"name\":\"testRecipe\",\"description\":\"testDescription\"," +
            "\"instructions\":\"testInstructions\",\"cooktime\":30,\"levelId\":1,\"cuisineId\":1,\"rating\":1,\"imagepath\":\"\"," +
            "\"ingredientIdAndMeasurementIdAmountMap\":{\"1\":{\"amount\":200,\"measurement_id\":1}," +
            "\"2\":{\"amount\":200,\"measurement_id\":1}},\"courseIdList\":[2,1],\"foodCategoryIdList\":[1,2],\"mealIdList\":[2,1]}";
    private static final String EXPECTED_CONTENT_AFTER_UPDATE ="{\"id\":1,\"name\":\"anotherTestRecipe\",\"description\":\"testDescription\"," +
            "\"instructions\":\"testInstructions\",\"cooktime\":30,\"levelId\":1,\"cuisineId\":1,\"rating\":1,\"imagepath\":\"\"," +
            "\"ingredientIdAndMeasurementIdAmountMap\":{\"1\":{\"amount\":200,\"measurement_id\":1}," +
            "\"2\":{\"amount\":200,\"measurement_id\":1}},\"courseIdList\":[2,1],\"foodCategoryIdList\":[1,2],\"mealIdList\":[2,1]}";
    private static final String EXPECTED_CONTENT_AFTER_DELETE ="Removal was successful";
    private static final String ERROR_DELETE_ENTITY_MESSAGE = "Can not delete entity with id ";
    private static final String ERROR_GET_ENTITY_MESSAGE = "Can not find entity with id ";
    private static final String ERROR_SAVE_ENTITY_MESSAGE = "Can not save entity";
    private static final String ERROR_UPDATE_ENTITY_MESSAGE = "Can not update entity";
    private static final long NONEXISTENT_DTO_ID = 0;
    private static final long DTO_ID = 1;
    private RecipeDto dto;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RecipeService service;

    @BeforeEach
    void setUp(){
        Map<String, Map<String,Long>> ingredientIdAndMeasurementIdAmountMap = new HashMap<>();
        ingredientIdAndMeasurementIdAmountMap.put(String.valueOf(1),  new HashMap<>(){{
            put( "measurement_id", MEASUREMENT_ID );
            put("amount", AMOUNT);
        }});
        ingredientIdAndMeasurementIdAmountMap.put(String.valueOf(2),  new HashMap<>(){{
            put( "measurement_id", MEASUREMENT_ID );
            put("amount", AMOUNT);
        }})
        ;
        dto = new RecipeDto(DTO_ID,NAME, DESCRIPTION,INSTRUCTIONS, COOK_TIME, LEVEL_ID, CUISINE_ID,RATING, IMAGE_PATH,
                ingredientIdAndMeasurementIdAmountMap, COURSE_ID_LIST,FOOD_CATEGORY_ID_LIST,MEAL_ID_LIST);
    }

    @Test
    @DisplayName("Сохранение recipe")
    void saveCourse() throws Exception {
        String jsonToSave = objectMapper.writeValueAsString(dto);
        dto.setId(DTO_ID);
        given(service.save(any())).willReturn(dto);
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(APPLICATION_JSON)
                .content(jsonToSave))
                .andExpect(content().string(EXPECTED_CONTENT))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Возврат ошибки сохранения recipe при неправильном маппинге")
    void saveCourseExpectedException() throws Exception {
        String jsonToSave = objectMapper.writeValueAsString(dto);
        given(service.save(any())).willThrow(new EntityMapperException("TestException", new MappingException(Collections.singletonList(new ErrorMessage("TestError")))));
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(APPLICATION_JSON)
                .content(jsonToSave))
                .andExpect(status().reason(containsString(ERROR_SAVE_ENTITY_MESSAGE)))
                .andExpect(status().isNotImplemented());
    }


    @Test
    @DisplayName("Обновление recipe")
    void updateCourse() throws Exception {
        dto.setId(DTO_ID);
        dto.setName(UPDATED_NAME);
        String jsonToSave = objectMapper.writeValueAsString(dto);
        given(service.update(any())).willReturn(dto);
        mockMvc.perform(put(URL_TEMPLATE+"/"+DTO_ID)
                .contentType(APPLICATION_JSON)
                .content(jsonToSave))
                .andExpect(content().string(EXPECTED_CONTENT_AFTER_UPDATE))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Возврат ошибки обновления recipe при неправильном маппинге")
    void updateCourseExpectedException() throws Exception {
        String jsonToSave = objectMapper.writeValueAsString(dto);
        given(service.update(any())).willThrow(new EntityMapperException("TestException", new MappingException(Collections.singletonList(new ErrorMessage("TestError")))));
        mockMvc.perform(put(URL_TEMPLATE+"/"+DTO_ID)
                .contentType(APPLICATION_JSON)
                .content(jsonToSave))
                .andExpect(status().reason(containsString(ERROR_UPDATE_ENTITY_MESSAGE)))
                .andExpect(status().isNotImplemented());
    }

    @Test
    @DisplayName("Получение recipe")
    void getCourse() throws Exception {
        dto.setId(DTO_ID);
        given(service.findById(any())).willReturn(dto);
        mockMvc.perform(get(URL_TEMPLATE+"/"+DTO_ID))
                .andExpect(content().string(EXPECTED_CONTENT))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Возврат ошибки получения recipe при ненайденном recipe id: "+NONEXISTENT_DTO_ID)
    void getCourseExpectedException() throws Exception {
        dto.setId(NONEXISTENT_DTO_ID);
        given(service.findById(any())).willThrow(new EntityNotFoundException("TestException"));
        mockMvc.perform(get(URL_TEMPLATE+"/"+NONEXISTENT_DTO_ID))
               .andExpect(status().reason(containsString(ERROR_GET_ENTITY_MESSAGE + NONEXISTENT_DTO_ID)))
               .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Удаление recipe")
    void deleteCourse() throws Exception {
        dto.setId(DTO_ID);
        mockMvc.perform(delete(URL_TEMPLATE+"/"+DTO_ID))
                .andExpect(content().string(EXPECTED_CONTENT_AFTER_DELETE))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Возврат ошибки удаления recipe при ненайденном recipe id: "+NONEXISTENT_DTO_ID)
    void deleteCourseExpectedException() throws Exception {
        dto.setId(NONEXISTENT_DTO_ID);
        doThrow(new EntityNotFoundException("TestException")).when(service).deleteById(any());
        mockMvc.perform(delete(URL_TEMPLATE+"/"+NONEXISTENT_DTO_ID))
                .andExpect(status().reason(containsString(ERROR_DELETE_ENTITY_MESSAGE + NONEXISTENT_DTO_ID)))
                .andExpect(status().isOk());
    }
}