package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.recipes.domain.Measurement;

import java.util.List;

public interface MeasurementRepository extends  CommonRepository<Measurement> {
}
