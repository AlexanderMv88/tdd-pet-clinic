package org.tddpetclinic.controller;


import org.springframework.stereotype.Service;
import org.tddpetclinic.PetRepository;
import org.tddpetclinic.dto.PetDto;
import org.tddpetclinic.dto.PetResponseDto;
import org.tddpetclinic.entity.Pet;

import java.util.Optional;

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
        pet.setHistory(petDto.getHistory());
        Pet savedPet = petRepository.save(pet);

        return mapResponseDto(savedPet);
    }

    public PetResponseDto save(Long petId, PetDto petDto) {
        Pet pet = new Pet();
        pet.setId(petId);
        pet.setName(petDto.getName());
        pet.setAge(petDto.getAge());
        pet.setHistory(petDto.getHistory());
        Pet savedPet = petRepository.save(pet);

        return mapResponseDto(savedPet);
    }

    private PetResponseDto mapResponseDto(Pet savedPet) {
        PetResponseDto petResponseDto = new PetResponseDto();
        petResponseDto.setId(savedPet.getId());
        petResponseDto.setName(savedPet.getName());
        petResponseDto.setAge(savedPet.getAge());
        petResponseDto.setHistory(savedPet.getHistory());
        return petResponseDto;
    }

    public PetResponseDto search(Long petId) throws RuntimeException {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Ошибка"));
        return mapResponseDto(pet);
    }
}
