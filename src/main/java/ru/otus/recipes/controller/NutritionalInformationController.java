package ru.otus.recipes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.recipes.domain.Measurement;
import ru.otus.recipes.domain.NutritionalInformation;
import ru.otus.recipes.service.MeasurementService;
import ru.otus.recipes.service.NutritionalInformationService;

@RestController
@RequestMapping("/nutritional-information")
public class NutritionalInformationController extends AbstractController<NutritionalInformation, NutritionalInformationService> {
    public NutritionalInformationController(NutritionalInformationService service) {
        super(service);
    }
}