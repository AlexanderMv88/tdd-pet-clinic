package org.tddpetclinic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.tddpetclinic.controller.PetController;
import org.tddpetclinic.dto.PetDto;
import org.tddpetclinic.dto.PetResponseDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PetIntegrationTest {
    //TODO: думать о тестировании через TestRestTemplate restTemplate = new TestRestTemplate();
    @Autowired
    private PetController petController;

    public static final String HISTORY = "Некая история болезни";

    @Test
    public void registrationTest() {
        PetDto petDto1 = createPetDto("Кот", 9);
        PetResponseDto expectedResponseDto1 = getPetResponseDto(1L, "Кот", 9);

        ResponseEntity<PetResponseDto> registered1 = petController.register(petDto1);

        assertEquals(HttpStatus.CREATED, registered1.getStatusCode());
        assertEquals(expectedResponseDto1, registered1.getBody());


        PetDto petDto2 = createPetDto("Вольт", 2);
        PetResponseDto expectedResponseDto2 = getPetResponseDto(2L, "Вольт", 2);

        ResponseEntity<PetResponseDto> registered2 = petController.register(petDto2);

        assertEquals(HttpStatus.CREATED, registered2.getStatusCode());
        assertEquals(expectedResponseDto2, registered2.getBody());
    }

    @Test
    public void changePetDataTest() {
        //Создаю
        PetDto petDto1 = createPetDto("Барсик", 1);
        PetResponseDto expectedResponseDto1 = getPetResponseDto(1L, "Барсик", 1);

        ResponseEntity<PetResponseDto> registered1 = petController.register(petDto1);

        Long id = registered1.getBody().getId();

        PetDto petDto2 = createPetDto("Барс", 2);
        PetResponseDto expectedResponseDto2 = getPetResponseDto(id, "Барс", 2);

        ResponseEntity<PetResponseDto> registered2 = petController.changePetData(id, petDto2);

        assertEquals(HttpStatus.OK, registered2.getStatusCode());
        assertEquals(expectedResponseDto2, registered2.getBody());
    }

    @Test
    public void findPetDataTest() {
        PetDto petDto = createPetDto("Котэ", 7);

        ResponseEntity<PetResponseDto> registered = petController.register(petDto);
        Long id = registered.getBody().getId();
        PetResponseDto expectedResponseDto = getPetResponseDto(id, "Котэ", 7);

        ResponseEntity<PetResponseDto> response = petController.getPetData(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponseDto, response.getBody());
    }


    private PetResponseDto getPetResponseDto(Long id, String name, Integer age) {
        PetResponseDto animalResponseDto = new PetResponseDto();
        animalResponseDto.setId(id);
        animalResponseDto.setAge(age);
        animalResponseDto.setName(name);
        animalResponseDto.setHistory(HISTORY);
        return animalResponseDto;
    }


    private PetDto createPetDto(String name, Integer age) {
        PetDto petDto = new PetDto();
        petDto.setAge(age);
        petDto.setName(name);
        petDto.setHistory(HISTORY);
        return petDto;
    }

}
