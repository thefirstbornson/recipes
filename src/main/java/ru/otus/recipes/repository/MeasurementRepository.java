package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.recipes.domain.Measurement;

public interface MeasurementRepository extends JpaRepository<Measurement,Long> {
}
