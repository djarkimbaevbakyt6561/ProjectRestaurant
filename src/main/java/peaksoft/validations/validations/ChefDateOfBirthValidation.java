package peaksoft.validations.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import peaksoft.validations.validators.ChefDateOfBirthValidator;
import peaksoft.validations.validators.WaiterDateOfBirthValidator;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = ChefDateOfBirthValidator.class
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ChefDateOfBirthValidation {
    String message() default "{You cannot enter because you are under 25 or more than 45 years old!}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
