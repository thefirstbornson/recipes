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
import ru.otus.recipes.exception.EntityExistsException;
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
    private static final String ERROR_ENTITY_NOT_FOUND_MESSAGE = "Can not find entity";
    private static final String ERROR_ENTITY_EXISTS_MESSAGE = "Can not save entity";
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
    void saveRecipe() throws Exception {
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
    @DisplayName("Обновление recipe")
    void updateRecipe() throws Exception {
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
    @DisplayName("Получение recipe")
    void getRecipe() throws Exception {
        dto.setId(DTO_ID);
        given(service.findById(any())).willReturn(dto);
        mockMvc.perform(get(URL_TEMPLATE+"/"+DTO_ID))
                .andExpect(content().string(EXPECTED_CONTENT))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Удаление recipe")
    void deleteRecipe() throws Exception {
        dto.setId(DTO_ID);
        mockMvc.perform(delete(URL_TEMPLATE+"/"+DTO_ID))
                .andExpect(content().string(containsString(EXPECTED_CONTENT_AFTER_DELETE)))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Возврат ошибки сохранения существующей entity")
    void saveEntityExpectedException() throws Exception {
        String jsonToSave = objectMapper.writeValueAsString(dto);
        given(service.save(any())).willThrow(new EntityExistsException(ERROR_ENTITY_EXISTS_MESSAGE));
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(APPLICATION_JSON)
                .content(jsonToSave))
                .andExpect(content().string(containsString(ERROR_ENTITY_EXISTS_MESSAGE)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Возврат ошибки обновления entity при ненайденном entity id")
    void updateEntityExpectedException() throws Exception {
        String jsonToSave = objectMapper.writeValueAsString(dto);
        given(service.update(any())).willThrow(new EntityNotFoundException(ERROR_ENTITY_NOT_FOUND_MESSAGE));
        mockMvc.perform(put(URL_TEMPLATE+"/"+DTO_ID)
                .contentType(APPLICATION_JSON)
                .content(jsonToSave))
                .andExpect(content().string(containsString(ERROR_ENTITY_NOT_FOUND_MESSAGE)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Возврат ошибки поиска entity при ненайденном entity id")
    void getEntityExpectedException() throws Exception {
        dto.setId(NONEXISTENT_DTO_ID);
        given(service.findById(any())).willThrow(new EntityNotFoundException(ERROR_ENTITY_NOT_FOUND_MESSAGE));
        mockMvc.perform(get(URL_TEMPLATE+"/"+NONEXISTENT_DTO_ID))
                .andExpect(content().string(containsString(ERROR_ENTITY_NOT_FOUND_MESSAGE)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Возврат ошибки удаления entiy при ненайденном entity id")
    void deleteEntityExpectedException() throws Exception {
        dto.setId(NONEXISTENT_DTO_ID);
        doThrow(new EntityNotFoundException(ERROR_ENTITY_NOT_FOUND_MESSAGE)).when(service).deleteById(any());
        mockMvc.perform(delete(URL_TEMPLATE+"/"+NONEXISTENT_DTO_ID))
                .andExpect(content().string(containsString(ERROR_ENTITY_NOT_FOUND_MESSAGE)))
                .andExpect(status().isNotFound());
    }
}