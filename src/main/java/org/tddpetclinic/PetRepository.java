package org.tddpetclinic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tddpetclinic.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {

}
