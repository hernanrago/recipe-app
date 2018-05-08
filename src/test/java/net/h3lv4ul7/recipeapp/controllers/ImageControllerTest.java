package net.h3lv4ul7.recipeapp.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import net.h3lv4ul7.recipeapp.commands.RecipeCommand;
import net.h3lv4ul7.recipeapp.services.ImageService;
import net.h3lv4ul7.recipeapp.services.RecipeService;

public class ImageControllerTest {

	@Mock
	ImageService imageService;

	@Mock
	RecipeService recipeService;

	ImageController imageController;

	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		imageController = new ImageController(imageService, recipeService);

		mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
	}

	@Test
	public void getImageForm() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);

		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

		mockMvc.perform(get("/recipe/1/image")).andExpect(status().isOk()).andExpect(model().attributeExists("recipe"));

		verify(recipeService, times(1)).findCommandById(anyLong());
	}

	@Test
	public final void testHandleImagePost() throws Exception {
		MockMultipartFile mockMultipartFile = new MockMultipartFile("imageFile", "testing.txt", "text/plain",
				"Spring FrameWork Guru".getBytes());

		this.mockMvc.perform(multipart("/recipe/1/image").file(mockMultipartFile))
				.andExpect(status().is3xxRedirection()).andExpect(header().string("Location", "/recipe/1/show"));

		verify(imageService, times(1)).saveImageFile(anyLong(), any());
	}

	@Test
	public final void testRenderImageFromDb() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();

		recipeCommand.setId(1L);

		String s = "Fake image text";

		Byte[] bytesBoxed = new Byte[s.getBytes().length];

		int i = 0;

		for (byte primByte : s.getBytes()) {
			bytesBoxed[i++] = primByte;
		}

		recipeCommand.setImage(bytesBoxed);

		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

		MockHttpServletResponse servletResponse = mockMvc.perform(get("/recipe/1/recipeimage"))
				.andExpect(status().isOk()).andReturn().getResponse();

		byte[] responseBytes = servletResponse.getContentAsByteArray();

		assertEquals(s.getBytes().length, responseBytes.length);
	}
}
