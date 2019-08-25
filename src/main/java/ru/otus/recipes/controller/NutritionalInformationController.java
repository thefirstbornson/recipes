package ru.otus.recipes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.recipes.domain.NutritionalInformation;
import ru.otus.recipes.dto.NutritionalInformationDto;
import ru.otus.recipes.service.NutritionalInformationService;

@RestController
@RequestMapping("/nutritional-information")
public class NutritionalInformationController extends AbstractController<NutritionalInformation,
        NutritionalInformationService, NutritionalInformationDto> {
    public NutritionalInformationController(NutritionalInformationService service) {
        super(service, NutritionalInformation.class);
    }
}