package net.h3lv4ul7.recipeapp.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import net.h3lv4ul7.recipeapp.domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long>{
	
	Optional<UnitOfMeasure> findByDescription(String description);
}
