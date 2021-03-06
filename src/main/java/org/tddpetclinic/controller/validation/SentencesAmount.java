package org.tddpetclinic.controller.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SentencesAmountValidator.class)
public @interface SentencesAmount {

    String message() default  "История болезни должна содержать от {min} до {max} предложений";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int max();

    int min();

}
