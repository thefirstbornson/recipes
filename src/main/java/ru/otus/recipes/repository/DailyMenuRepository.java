package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.recipes.domain.DailyMenu;

@Repository
public interface DailyMenuRepository extends JpaRepository<DailyMenu, Long> {
}

