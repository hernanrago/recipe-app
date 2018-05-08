package net.h3lv4ul7.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.Synchronized;
import net.h3lv4ul7.recipeapp.commands.UnitOfMeasureCommand;
import net.h3lv4ul7.recipeapp.domain.UnitOfMeasure;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure>{

	@Nullable
    @Override
    @Synchronized
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if (source == null) return null;

        final UnitOfMeasure uom = new UnitOfMeasure();
        
        uom.setId(source.getId());
        uom.setDescription(source.getDescription());
        
        return uom;
    }
}
