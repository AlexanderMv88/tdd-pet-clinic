package org.tddpetclinic.dto;

import lombok.Data;
import org.tddpetclinic.controller.validation.SentencesAmount;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PetDto {
    @NotBlank(message = "Введите кличку")
    @Size(min = 2, max = 20, message = "Имя питомца должно быть не короче {min} и не длинее {max} символов")
    protected String name;
    @NotNull(message = "Введите возраст")
    protected Integer age;
    @SentencesAmount(min=1, max = 5)
    protected String history;
}
