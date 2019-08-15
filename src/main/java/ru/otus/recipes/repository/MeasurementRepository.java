package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.recipes.domain.Measurement;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement,Long> {
    List<Measurement> findByIdIn(List<Long> ids);
}
