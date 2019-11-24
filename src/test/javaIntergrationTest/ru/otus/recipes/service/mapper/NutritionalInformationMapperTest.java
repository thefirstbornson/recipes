package ru.otus.recipes.service.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.recipes.domain.NutritionalInformation;
import ru.otus.recipes.dto.NutritionalInformationDto;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"spring.h2.console.enabled=true"})
@TestPropertySource("classpath:application-test.properties")
@Transactional
class NutritionalInformationMapperTest {

    private final NutritionalInformation nutritionalInformation = new NutritionalInformation("nutritionalInformationName");
    private final NutritionalInformationDto nutritionalInformationDto = new NutritionalInformationDto("nutritionalInformationName");

    @Autowired
    private NutritionalInformationMapper nutritionalInformationMapper;

    @Test
    void toEntity() {
        NutritionalInformation nutritionalInformationFromDto = nutritionalInformationMapper.toEntity(nutritionalInformationDto);
        assertEquals(nutritionalInformationFromDto.getId(), this.nutritionalInformation.getId());
    }

    @Test
    void toDto() {
        NutritionalInformationDto nutritionalInformationDtoFromEntity = nutritionalInformationMapper.toDto(nutritionalInformation);
        assertEquals(nutritionalInformationDtoFromEntity.getNutrition(), nutritionalInformation.getNutrition());
    }
}