package org.tddpetclinic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.tddpetclinic.entity.Pet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
public class PetRepositoryTest {

    @Autowired
    private PetRepository petRepository;

    @Test
    public void saveNewPetTest(){
        Pet pet = new Pet();
        pet.setName("Вольт");
        pet.setAge(2);

        pet = petRepository.save(pet);
        assertNotNull(pet.getId());
        assertEquals("Вольт", pet.getName());
        assertEquals(2, pet.getAge());
    }

    @Test
    public void saveExistPetTest(){
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Вольт");
        pet.setAge(2);

        pet = petRepository.save(pet);
        assertEquals(1L, pet.getId());
        assertEquals("Вольт", pet.getName());
        assertEquals(2, pet.getAge());
    }
}
