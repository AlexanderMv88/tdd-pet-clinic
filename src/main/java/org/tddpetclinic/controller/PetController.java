package org.tddpetclinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.tddpetclinic.dto.PetDto;
import org.tddpetclinic.dto.PetResponseDto;

import javax.validation.Valid;

@RestController
public class PetController {

    @PostMapping("/register")
    public ResponseEntity<PetDto> regiter(@RequestBody @Valid PetDto petDto){
        PetResponseDto animalResponseDto = new PetResponseDto();
        animalResponseDto.setId(1L);
        animalResponseDto.setName(petDto.getName());
        animalResponseDto.setAge(petDto.getAge());
        return new ResponseEntity<>(animalResponseDto, HttpStatus.CREATED);
    }
}
