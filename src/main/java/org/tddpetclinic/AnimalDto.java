package org.tddpetclinic;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AnimalDto {
    @NotBlank
    protected String name;
    protected Integer age;

}
