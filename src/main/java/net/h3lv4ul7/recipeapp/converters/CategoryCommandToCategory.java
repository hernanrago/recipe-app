package net.h3lv4ul7.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.Synchronized;
import net.h3lv4ul7.recipeapp.commands.CategoryCommand;
import net.h3lv4ul7.recipeapp.domain.Category;

@Component
public class CategoryCommandToCategory  implements Converter<CategoryCommand, Category>{

	@Nullable
	@Override
	@Synchronized
	public Category convert(CategoryCommand source) {
		if(source == null) return null;
		
		final Category category = new Category();
		
		category.setId(source.getId());
		category.setDescription(source.getDescription());
		
		return category;		
	}
}
