package net.h3lv4ul7.recipeapp.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import net.h3lv4ul7.recipeapp.domain.Recipe;
import net.h3lv4ul7.recipeapp.repositories.RecipeRepository;
import net.h3lv4ul7.recipeapp.services.impl.ImageServiceImpl;

public class ImageServiceImplTest {

	ImageService imageService;
	
	MockMvc mockMvc;
	
	@Mock
	RecipeRepository recipeRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		imageService = new ImageServiceImpl(recipeRepository);
	}

	@Test
	public final void testSaveImageFile() throws Exception {
		Long id = 1L;

		MultipartFile multipartFile = new MockMultipartFile("imageFile", "testing.txt", "text/plain",
				"Spring FrameWork Guru".getBytes());

		Recipe recipe = new Recipe();
		recipe.setId(id);

		Optional<Recipe> recipeOptional = Optional.ofNullable(recipe);

		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

		ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

		imageService.saveImageFile(id, multipartFile);

		verify(recipeRepository, times(1)).save(argumentCaptor.capture());

		Recipe savedRecipe = argumentCaptor.getValue();

		assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
	}

}
