package peaksoft.validations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import peaksoft.validations.validations.PriceValidation;

import java.math.BigDecimal;

public class PriceValidator implements ConstraintValidator<PriceValidation, BigDecimal> {
    @Override
    public boolean isValid(BigDecimal price, ConstraintValidatorContext constraintValidatorContext) {
        return price != null && price.compareTo(BigDecimal.ZERO) >= 0;
    }
}
