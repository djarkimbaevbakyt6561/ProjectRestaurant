package peaksoft.validations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import peaksoft.validations.validations.PasswordValidation;

public class PasswordValidator  implements ConstraintValidator<PasswordValidation, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return !password.isBlank() && password.length() <= 4;
    }
}
