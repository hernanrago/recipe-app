package net.h3lv4ul7.recipeapp.repositories;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.h3lv4ul7.recipeapp.domain.UnitOfMeasure;
import net.h3lv4ul7.recipeapp.repositories.UnitOfMeasureRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryITTest {

	@Autowired
	UnitOfMeasureRepository unitOfMeasureRepository;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void testFindByDescription() {
		Optional<UnitOfMeasure> optional = unitOfMeasureRepository.findByDescription("Teaspoon");

		assertEquals("Teaspoon", optional.get().getDescription());
	}

	@Test
	public final void testFindByDescriptionCup() {
		Optional<UnitOfMeasure> optional = unitOfMeasureRepository.findByDescription("Cup");

		assertEquals("Cup", optional.get().getDescription());
	}

}
