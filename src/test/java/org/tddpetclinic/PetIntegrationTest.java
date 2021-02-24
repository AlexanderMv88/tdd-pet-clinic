package org.tddpetclinic;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private PetController petController;

    @Test
    public void registrationTest() {
        PetDto petDto1 = createPetDto("Кот", 9);
        PetResponseDto expectedResponseDto1 = getPetResponseDto(1L, "Кот", 9);

        ResponseEntity<PetResponseDto> registered1 = petController.regiter(petDto1);

        assertEquals(HttpStatus.CREATED, registered1.getStatusCode());
        assertEquals(expectedResponseDto1, registered1.getBody());


        PetDto petDto2 = createPetDto("Вольт", 2);
        PetResponseDto expectedResponseDto2 = getPetResponseDto(2L, "Вольт", 2);

        ResponseEntity<PetResponseDto> registered2 = petController.regiter(petDto2);

        assertEquals(HttpStatus.CREATED, registered2.getStatusCode());
        assertEquals(expectedResponseDto2, registered2.getBody());
    }

    @Test
    public void changePetDataTest() {
        //Создаю
        PetDto petDto1 = createPetDto("Барсик", 1);
        PetResponseDto expectedResponseDto1 = getPetResponseDto(1L, "Барсик", 1);

        ResponseEntity<PetResponseDto> registered1 = petController.regiter(petDto1);

        Long id = registered1.getBody().getId();

        PetDto petDto2 = createPetDto("Барс", 2);
        PetResponseDto expectedResponseDto2 = getPetResponseDto(id, "Барс", 2);

        ResponseEntity<PetResponseDto> registered2 = petController.changePetData(id, petDto2);

        assertEquals(HttpStatus.OK, registered2.getStatusCode());
        assertEquals(expectedResponseDto2, registered2.getBody());
    }


    private PetResponseDto getPetResponseDto(Long id, String name, Integer age) {
        PetResponseDto animalResponseDto = new PetResponseDto();
        animalResponseDto.setId(id);
        animalResponseDto.setAge(age);
        animalResponseDto.setName(name);
        return animalResponseDto;
    }


    private PetDto createPetDto(String name, Integer age) {
        PetDto petDto = new PetDto();
        petDto.setAge(age);
        petDto.setName(name);
        return petDto;
    }

}
