package org.tddpetclinic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = PetClinicController.class)
public class PetClinicControllerTest {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private MockMvc petClinicController;

    @Test
    public void registerTest() throws Exception {
        AnimalDto animalDto = new AnimalDto();
        animalDto.setAge(2);
        animalDto.setName("Вольт");

        AnimalResponseDto animalResponseDto = new AnimalResponseDto();
        animalResponseDto.setId(1L);
        animalResponseDto.setAge(2);
        animalResponseDto.setName("Вольт");

        petClinicController.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(animalDto))
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(animalResponseDto)));
    }

    @Test
    public void registerNameValidationTest() throws Exception {
        AnimalDto animalDto = new AnimalDto();
        animalDto.setAge(2);

        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("Введите кличку");
        MockHttpServletResponse response = petClinicController.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(animalDto))
        ).andDo(print())
                .andExpect(status().isBadRequest()).andReturn().getResponse();

        String contentAsString = response.getContentAsString(StandardCharsets.UTF_8);
        assertEquals(objectMapper.writeValueAsString(errorDto), contentAsString);
    }


}