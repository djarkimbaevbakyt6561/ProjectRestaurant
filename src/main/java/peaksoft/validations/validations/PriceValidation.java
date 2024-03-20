package peaksoft.validations.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import peaksoft.validations.validators.PriceValidator;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = PriceValidator.class
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PriceValidation {
    String message() default "{Price are null or less than zero}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
