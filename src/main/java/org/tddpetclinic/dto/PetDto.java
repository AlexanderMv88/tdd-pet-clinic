package org.tddpetclinic.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PetDto {
    @NotBlank(message = "Введите кличку")
    protected String name;
    @NotNull(message = "Введите возраст")
    protected Integer age;

}
