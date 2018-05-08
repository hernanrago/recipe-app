package net.h3lv4ul7.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.Synchronized;
import net.h3lv4ul7.recipeapp.commands.CategoryCommand;
import net.h3lv4ul7.recipeapp.domain.Category;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

	@Synchronized
	@Nullable
	@Override
    public CategoryCommand convert(Category source) {     
    	if (source == null) return null;

        final CategoryCommand categoryCommand = new CategoryCommand();

        categoryCommand.setId(source.getId());
        categoryCommand.setDescription(source.getDescription());

        return categoryCommand;
    }
}
