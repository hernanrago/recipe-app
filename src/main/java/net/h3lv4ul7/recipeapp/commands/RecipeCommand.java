package net.h3lv4ul7.recipeapp.commands;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.h3lv4ul7.recipeapp.domain.Difficulty;

@Setter
@Getter
@NoArgsConstructor
public class RecipeCommand {
	
	private Long id;
	
	@NotBlank
	@Size(min = 3, max = 255)
	private String description;
	
	@Min(1)
	@Max(999)
	private Integer prepTime;
	
	@Min(1)
	@Max(200)
	private Integer cookTime;
	
	@Min(1)
	@Max(100)
	private Integer servings;
	
	@URL
	private String url;
	
	private String source;
	
	@NotBlank
	private String direction;
	
	private Difficulty difficulty;
	private Set<IngredientCommand> ingredients = new HashSet<>();
	private NoteCommand note;
	private Set<CategoryCommand> categories = new HashSet<>();
	private Byte[] image;
}
