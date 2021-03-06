package org.tddpetclinic.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class PetResponseDto extends PetDto {
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PetResponseDto that = (PetResponseDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(history, that.history);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, history);
    }

    @Override
    public String toString() {
        return "PetResponseDto{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", history='" + history + '\'' +
                ", id=" + id +
                '}';
    }
}
