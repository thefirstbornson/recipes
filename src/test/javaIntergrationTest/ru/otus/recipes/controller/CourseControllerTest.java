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

import ru.otus.recipes.dto.CourseDto;
import ru.otus.recipes.exception.EntityMapperException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.service.CourseService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers  = CourseController.class)
class CourseControllerTest {

    private static final String NAME = "testCourse";
    private static final String UPDATED_NAME = "anotherTestCourse";
    private static final String URL_TEMPLATE = "/courses";
    private static final String EXPECTED_CONTENT ="{\"id\":1,\"course\":\"testCourse\"}";
    private static final String EXPECTED_CONTENT_AFTER_UPDATE ="{\"id\":1,\"course\":\"anotherTestCourse\"}";
    private static final String EXPECTED_CONTENT_AFTER_DELETE ="Removal was successful";
    private static final String ERROR_DELETE_ENTITY_MESSAGE = "Can not delete entity with id ";
    private static final String ERROR_GET_ENTITY_MESSAGE = "Can not find entity with id ";
    private static final String ERROR_SAVE_ENTITY_MESSAGE = "Can not save entity";
    private static final String ERROR_UPDATE_ENTITY_MESSAGE = "Can not update entity";
    private static final long NONEXISTENT_DTO_ID = 0;
    private static final long DTO_ID = 1;
    private CourseDto dto;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CourseService service;

    @BeforeEach
    void setUp(){
        dto = new CourseDto(NAME);
    }

    @Test
    @DisplayName("Сохранение course")
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
    @DisplayName("Возврат ошибки сохранения course при неправильном маппинге")
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
    @DisplayName("Обновление course")
    void updateCourse() throws Exception {
        dto.setId(DTO_ID);
        dto.setCourse(UPDATED_NAME);
        String jsonToSave = objectMapper.writeValueAsString(dto);
        given(service.update(any())).willReturn(dto);
        mockMvc.perform(put(URL_TEMPLATE+"/"+DTO_ID)
                .contentType(APPLICATION_JSON)
                .content(jsonToSave))
                .andExpect(content().string(EXPECTED_CONTENT_AFTER_UPDATE))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Возврат ошибки обновления course при неправильном маппинге")
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
    @DisplayName("Получение course")
    void getCourse() throws Exception {
        dto.setId(DTO_ID);
        given(service.findById(any())).willReturn(dto);
        mockMvc.perform(get(URL_TEMPLATE+"/"+DTO_ID))
                .andExpect(content().string(EXPECTED_CONTENT))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Возврат ошибки получения course при ненайденном course id: "+NONEXISTENT_DTO_ID)
    void getCourseExpectedException() throws Exception {
        dto.setId(NONEXISTENT_DTO_ID);
        given(service.findById(any())).willThrow(new EntityNotFoundException("TestException"));
        mockMvc.perform(get(URL_TEMPLATE+"/"+NONEXISTENT_DTO_ID))
               .andExpect(status().reason(containsString(ERROR_GET_ENTITY_MESSAGE + NONEXISTENT_DTO_ID)))
               .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Удаление course")
    void deleteCourse() throws Exception {
        dto.setId(DTO_ID);
        mockMvc.perform(delete(URL_TEMPLATE+"/"+DTO_ID))
                .andExpect(content().string(EXPECTED_CONTENT_AFTER_DELETE))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Возврат ошибки удаления course при ненайденном course id: "+NONEXISTENT_DTO_ID)
    void deleteCourseExpectedException() throws Exception {
        dto.setId(NONEXISTENT_DTO_ID);
        doThrow(new EntityNotFoundException("TestException")).when(service).deleteById(any());
        mockMvc.perform(delete(URL_TEMPLATE+"/"+NONEXISTENT_DTO_ID))
                .andExpect(status().reason(containsString(ERROR_DELETE_ENTITY_MESSAGE + NONEXISTENT_DTO_ID)))
                .andExpect(status().isOk());
    }

}