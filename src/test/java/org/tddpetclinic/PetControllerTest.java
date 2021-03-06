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

import javax.validation.Validator;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = PetController.class)
public class PetControllerTest {

    public static final String PET_URL = "/pet";
    private static final String HISTORY = "Некая история болезни.";

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
        petDto.setHistory(HISTORY);

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
        petDto.setHistory(HISTORY);

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
        petDto.setHistory(HISTORY);

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

    @Test
    public void changePetDataTest() throws Exception {
        Long petId = 10L;

        PetDto petDto = new PetDto();
        petDto.setAge(4);
        petDto.setName("Барсик");
        petDto.setHistory(HISTORY);

        PetResponseDto animalResponseDto = new PetResponseDto();
        animalResponseDto.setId(petId);
        animalResponseDto.setAge(4);
        animalResponseDto.setName("Барсик");

        when(petService.save(petId, petDto)).thenReturn(animalResponseDto);

        String url = String.format("%s/%d", PET_URL, petId);

        petClinicController.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(petDto))
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(animalResponseDto)));
    }

    @Test
    public void getPetDataTest() throws Exception {
        Long petId = 10L;

        PetResponseDto animalResponseDto = new PetResponseDto();
        animalResponseDto.setId(petId);
        animalResponseDto.setAge(4);
        animalResponseDto.setName("Барсик");

        when(petService.search(petId)).thenReturn(animalResponseDto);
        String url = String.format("%s/%d", PET_URL, petId);

        petClinicController.perform(get(url))
        .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(animalResponseDto)));
    }

    @Test
    public void registerNameSizeValidationTest() throws Exception {
        String expectedSizeValidationMessage = "Имя питомца должно быть не короче 2 и не длинее 20 символов";
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(expectedSizeValidationMessage);

        PetDto petDtoWithShortName = new PetDto();
        petDtoWithShortName.setAge(2);
        petDtoWithShortName.setName("a");
        petDtoWithShortName.setHistory(HISTORY);
        MockHttpServletResponse shortNameErrorResponse = petClinicController.perform(post(PET_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(petDtoWithShortName))
        ).andDo(print())
                .andExpect(status().isBadRequest()).andReturn().getResponse();

        String shortErrorContent = shortNameErrorResponse.getContentAsString(StandardCharsets.UTF_8);
        assertEquals(objectMapper.writeValueAsString(errorDto), shortErrorContent);


        PetDto petDtoWithLongName = new PetDto();
        petDtoWithLongName.setAge(2);
        petDtoWithLongName.setName("Очень_очень_длиное_имя_питомца");
        petDtoWithLongName.setHistory(HISTORY);
        MockHttpServletResponse longNameErrorResponse = petClinicController.perform(post(PET_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(petDtoWithLongName))
        ).andDo(print())
                .andExpect(status().isBadRequest()).andReturn().getResponse();

        String longErrorContent = longNameErrorResponse.getContentAsString(StandardCharsets.UTF_8);
        assertEquals(objectMapper.writeValueAsString(errorDto), longErrorContent);
    }

    @Test
    public void registerHistorySentencesAmountValidationTest() throws Exception {
        String expectedSizeValidationMessage = "История болезни должна содержать от 1 до 5 предложений";
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(expectedSizeValidationMessage);

        PetDto petDtoWithNullHistory = new PetDto();
        petDtoWithNullHistory.setAge(2);
        petDtoWithNullHistory.setName("Tom");
        MockHttpServletResponse nullHistoryErrorResponse = petClinicController.perform(post(PET_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(petDtoWithNullHistory))
        ).andDo(print())
                .andExpect(status().isBadRequest()).andReturn().getResponse();

        String nullErrorContent = nullHistoryErrorResponse.getContentAsString(StandardCharsets.UTF_8);
        assertEquals(objectMapper.writeValueAsString(errorDto), nullErrorContent);


        PetDto petDtoWithShortHistory = new PetDto();
        petDtoWithShortHistory.setAge(2);
        petDtoWithShortHistory.setName("Tom");
        petDtoWithShortHistory.setHistory("");
        MockHttpServletResponse shortHistoryErrorResponse = petClinicController.perform(post(PET_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(petDtoWithShortHistory))
        ).andDo(print())
                .andExpect(status().isBadRequest()).andReturn().getResponse();

        String shortErrorContent = shortHistoryErrorResponse.getContentAsString(StandardCharsets.UTF_8);
        assertEquals(objectMapper.writeValueAsString(errorDto), shortErrorContent);


        PetDto petDtoWithLongHistory = new PetDto();
        petDtoWithLongHistory.setAge(2);
        petDtoWithLongHistory.setName("Tom");
        petDtoWithLongHistory.setHistory("История. Том. Заболел. Простыл. Чихает. Но скачет.");
        MockHttpServletResponse longHistoryErrorResponse = petClinicController.perform(post(PET_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(petDtoWithLongHistory))
        ).andDo(print())
                .andExpect(status().isBadRequest()).andReturn().getResponse();

        String longErrorContent = longHistoryErrorResponse.getContentAsString(StandardCharsets.UTF_8);
        assertEquals(objectMapper.writeValueAsString(errorDto), longErrorContent);
    }
}