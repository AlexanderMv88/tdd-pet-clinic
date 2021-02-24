package org.tddpetclinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tddpetclinic.dto.PetDto;
import org.tddpetclinic.dto.PetResponseDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<PetDto> regiter(@RequestBody @Valid PetDto petDto){
        PetResponseDto animalResponseDto = petService.save(petDto);
        return new ResponseEntity<>(animalResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDto> changePetData(@PathVariable Long id,
                                                @RequestBody @Valid PetDto petDto) {
        PetResponseDto petResponseDto = petService.save(id, petDto);
        return new ResponseEntity<>(petResponseDto, HttpStatus.OK);
    }
}
