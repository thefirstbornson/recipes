package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.recipes.domain.NutritionalInformation;

public interface NutritionalInformationRepository extends JpaRepository<NutritionalInformation,Long> {
}
