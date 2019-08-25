package ru.otus.recipes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.recipes.domain.Measurement;
import ru.otus.recipes.dto.MeasurementDto;
import ru.otus.recipes.service.MeasurementService;

@RestController
@RequestMapping("/measurements")
public class MeasurementController extends AbstractController<Measurement, MeasurementService, MeasurementDto> {
    public MeasurementController(MeasurementService service) {
        super(service, Measurement.class);
    }
}