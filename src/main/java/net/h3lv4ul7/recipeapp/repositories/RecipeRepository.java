package net.h3lv4ul7.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.h3lv4ul7.recipeapp.domain.Recipe;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long>{

}
