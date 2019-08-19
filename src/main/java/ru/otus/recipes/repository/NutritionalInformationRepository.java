package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.recipes.domain.NutritionalInformation;

import java.util.List;

public interface NutritionalInformationRepository  extends  CommonRepository<NutritionalInformation> {

}
