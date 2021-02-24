package org.tddpetclinic.controller;


import org.springframework.stereotype.Service;
import org.tddpetclinic.PetRepository;
import org.tddpetclinic.dto.PetDto;
import org.tddpetclinic.dto.PetResponseDto;
import org.tddpetclinic.entity.Pet;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public PetResponseDto save(PetDto petDto) {
        Pet pet = new Pet();
        pet.setName(petDto.getName());
        pet.setAge(petDto.getAge());
        Pet savedPet = petRepository.save(pet);

        PetResponseDto petResponseDto = new PetResponseDto();
        petResponseDto.setId(savedPet.getId());
        petResponseDto.setName(savedPet.getName());
        petResponseDto.setAge(savedPet.getAge());
        return petResponseDto;
    }
}
