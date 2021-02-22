package org.tddpetclinic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PetClinicController {

    @PostMapping("/register")
    public ResponseEntity<AnimalDto> regiter(@RequestBody @Valid AnimalDto animalDto){
        AnimalResponseDto animalResponseDto = new AnimalResponseDto();
        animalResponseDto.setId(1L);
        animalResponseDto.setName(animalDto.getName());
        animalResponseDto.setAge(animalDto.getAge());
        return new ResponseEntity<>(animalResponseDto, HttpStatus.CREATED);
    }
}
