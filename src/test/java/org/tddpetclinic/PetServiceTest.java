package org.tddpetclinic;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.tddpetclinic.controller.PetService;
import org.tddpetclinic.dto.PetDto;
import org.tddpetclinic.dto.PetResponseDto;
import org.tddpetclinic.entity.Pet;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PetServiceTest {
    public static final String PET_NAME = "Вольт";
    public static final int PET_AGE = 2;

    @MockBean
    private PetRepository petRepository;

    @Autowired
    private PetService petService;

    @Test
    public void saveNewTest(){
        Pet pet = new Pet();
        pet.setName(PET_NAME);
        pet.setAge(PET_AGE);

        Pet petSaved = new Pet();
        petSaved.setId(1L);
        petSaved.setName(PET_NAME);
        petSaved.setAge(PET_AGE);
        when(petRepository.save(pet)).thenReturn(petSaved);

        PetDto petDto = new PetDto();
        petDto.setName(PET_NAME);
        petDto.setAge(PET_AGE);

        PetResponseDto petResponseDto = petService.save(petDto);
        assertEquals(1L, petResponseDto.getId());
        assertEquals(PET_NAME, petResponseDto.getName());
        assertEquals(PET_AGE, petResponseDto.getAge());
    }

    @Test
    public void savePetDataTest(){
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName(PET_NAME);
        pet.setAge(PET_AGE);

        Pet petSaved = new Pet();
        petSaved.setId(1L);
        petSaved.setName(PET_NAME);
        petSaved.setAge(PET_AGE);
        when(petRepository.save(pet)).thenReturn(petSaved);

        PetDto petDto = new PetDto();
        petDto.setName(PET_NAME);
        petDto.setAge(PET_AGE);

        PetResponseDto petResponseDto = petService.save(1L, petDto);
        assertEquals(1L, petResponseDto.getId());
        assertEquals(PET_NAME, petResponseDto.getName());
        assertEquals(PET_AGE, petResponseDto.getAge());
    }


    @Test
    public void searchPetDataTest(){
        Long petId = 1L;

        Pet pet = new Pet();
        pet.setId(petId);
        pet.setName(PET_NAME);
        pet.setAge(PET_AGE);
        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));

        PetResponseDto petResponseDto = petService.search(petId);

        assertEquals(petId, petResponseDto.getId());
        assertEquals(PET_NAME, petResponseDto.getName());
        assertEquals(PET_AGE, petResponseDto.getAge());
    }
}
