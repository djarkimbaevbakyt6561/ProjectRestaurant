package peaksoft.validations.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import peaksoft.validations.validators.WaiterDateOfBirthValidator;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = WaiterDateOfBirthValidator.class
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WaiterDateOfBirthValidation {
    String message() default "{You cannot enter because you are under 18 or more than 30 years old!}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
