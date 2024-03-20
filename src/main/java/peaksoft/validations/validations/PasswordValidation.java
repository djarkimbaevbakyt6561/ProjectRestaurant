package peaksoft.validations.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import peaksoft.validations.validators.PasswordValidator;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = PasswordValidator.class
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValidation {
    String message() default "{Password must contain letters and numbers and length <= 7 symbols}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
