package net.h3lv4ul7.recipeapp.services.impl;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import net.h3lv4ul7.recipeapp.commands.UnitOfMeasureCommand;
import net.h3lv4ul7.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import net.h3lv4ul7.recipeapp.repositories.UnitOfMeasureRepository;
import net.h3lv4ul7.recipeapp.services.UnitOfMeasureService;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUnitsOfMeasures() {

        return StreamSupport.stream(unitOfMeasureRepository.findAll()
                .spliterator(), false)
                .map(unitOfMeasureToUnitOfMeasureCommand::convert)
                .collect(Collectors.toSet());
    }
}
