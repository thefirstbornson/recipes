package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.recipes.domain.Menu;

@Repository
public interface MenuRepository extends CommonRepository<Menu> {
}

