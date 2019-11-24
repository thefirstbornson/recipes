package ru.otus.recipes.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.recipes.domain.Ingredient;
import ru.otus.recipes.domain.IngredientNutritionalInformation;
import ru.otus.recipes.domain.NutritionalInformation;
import ru.otus.recipes.dto.IngredientDto;
import ru.otus.recipes.repository.NutritionalInformationRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"spring.h2.console.enabled=true"})
@TestPropertySource("classpath:application-test.properties")
@Transactional
class IngredientMapperTest {

    private static final Long NUTRITIONAL_INFORMATION_ID =1L;
    private static final Integer NUTRITIONAL_INFORMATION_AMOUNT =10;
    private Ingredient ingredient;
    private  IngredientDto ingredientDto;

    @Autowired
    private IngredientMapper ingredientMapper;
    @Autowired
    NutritionalInformationRepository nutritionalInformationRepository;

    @BeforeEach
    void setUp(){
       NutritionalInformation nutritionalInformation = nutritionalInformationRepository.findById(NUTRITIONAL_INFORMATION_ID).get();
       IngredientNutritionalInformation ingredientNutritionalInformation = new IngredientNutritionalInformation(nutritionalInformation, 10);
       ingredient = new Ingredient("ingredientName", Collections.singletonList(ingredientNutritionalInformation));
       Map<Long,Integer> nutritionalInformationByAmountMap = new HashMap<>();
       nutritionalInformationByAmountMap.put(NUTRITIONAL_INFORMATION_ID,NUTRITIONAL_INFORMATION_AMOUNT);
       ingredientDto =new IngredientDto("ingredientName", nutritionalInformationByAmountMap);

    }
    @Test
    void toEntity() {
        Ingredient IngredientFromDto = ingredientMapper.toEntity(ingredientDto);
        assertEquals(IngredientFromDto.getIngredientNutritionalInformations().stream()
                .map(IngredientNutritionalInformation::getAmount).findFirst().get(), NUTRITIONAL_INFORMATION_AMOUNT);
    }

    @Test
    void toDto() {
        IngredientDto IngredientDtoFromEntity = ingredientMapper.toDto(ingredient);
        assertEquals(IngredientDtoFromEntity.getNutritionalIdsndAmountMap().get(NUTRITIONAL_INFORMATION_ID),NUTRITIONAL_INFORMATION_AMOUNT);
    }
}