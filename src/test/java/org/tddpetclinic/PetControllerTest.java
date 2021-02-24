package org.tddpetclinic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.tddpetclinic.controller.PetController;
import org.tddpetclinic.controller.PetService;
import org.tddpetclinic.dto.ErrorDto;
import org.tddpetclinic.dto.PetDto;
import org.tddpetclinic.dto.PetResponseDto;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = PetController.class)
public class PetControllerTest {

    public static final String PET_URL = "/pet";

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private PetService petService;

    @Autowired
    private MockMvc petClinicController;

    @Test
    public void registerTest() throws Exception {
        PetDto petDto = new PetDto();
        petDto.setAge(2);
        petDto.setName("Вольт");

        PetResponseDto animalResponseDto = new PetResponseDto();
        animalResponseDto.setId(1L);
        animalResponseDto.setAge(2);
        animalResponseDto.setName("Вольт");

        when(petService.save(petDto)).thenReturn(animalResponseDto);

        petClinicController.perform(post(PET_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(petDto))
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(animalResponseDto)));
    }

    @Test
    public void registerNameValidationTest() throws Exception {
        PetDto petDto = new PetDto();
        petDto.setAge(2);

        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("Введите кличку");
        MockHttpServletResponse response = petClinicController.perform(post(PET_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(petDto))
        ).andDo(print())
                .andExpect(status().isBadRequest()).andReturn().getResponse();

        String contentAsString = response.getContentAsString(StandardCharsets.UTF_8);
        assertEquals(objectMapper.writeValueAsString(errorDto), contentAsString);
    }

    @Test
    public void registerAgeValidationTest() throws Exception {
        PetDto petDto = new PetDto();
        petDto.setName("Вольт");

        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("Введите возраст");
        MockHttpServletResponse response = petClinicController.perform(post(PET_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(petDto))
        ).andDo(print())
                .andExpect(status().isBadRequest()).andReturn().getResponse();

        String contentAsString = response.getContentAsString(StandardCharsets.UTF_8);
        assertEquals(objectMapper.writeValueAsString(errorDto), contentAsString);
    }
}