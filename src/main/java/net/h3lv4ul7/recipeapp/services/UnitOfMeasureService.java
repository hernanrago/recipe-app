package net.h3lv4ul7.recipeapp.services;

import java.util.Set;

import net.h3lv4ul7.recipeapp.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {

	Set<UnitOfMeasureCommand> listAllUnitsOfMeasures();

}
