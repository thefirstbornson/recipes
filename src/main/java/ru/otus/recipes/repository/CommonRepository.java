package ru.otus.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.otus.recipes.domain.AbstractEntity;

import java.util.List;

@NoRepositoryBean
public interface CommonRepository<E extends AbstractEntity> extends JpaRepository<E, Long> {
    List<E> findByIdIn(List<Long> ids);
}
