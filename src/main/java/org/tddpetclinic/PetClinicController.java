package org.tddpetclinic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetClinicController {

    @PostMapping("/register")
    public ResponseEntity<AnimalDto> regiter(@RequestBody AnimalDto animalDto){
        return new ResponseEntity<>(animalDto, HttpStatus.CREATED);
    }
}
