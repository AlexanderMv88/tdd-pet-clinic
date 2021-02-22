package org.tddpetclinic;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AnimalDto {
    @NotBlank(message = "Введите кличку")
    protected String name;
    @NotNull(message = "Введите возраст")
    protected Integer age;

}
