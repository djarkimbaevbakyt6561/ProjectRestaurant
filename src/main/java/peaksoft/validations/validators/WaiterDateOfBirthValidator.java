package peaksoft.validations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import peaksoft.validations.validations.WaiterDateOfBirthValidation;

import java.time.LocalDate;
import java.time.Period;

public class WaiterDateOfBirthValidator implements ConstraintValidator<WaiterDateOfBirthValidation, String> {
    @Override
    public boolean isValid(String dateOfBirthStr, ConstraintValidatorContext context) {
        if (dateOfBirthStr == null || dateOfBirthStr.isEmpty()) {
            return false; // Дата рождения отсутствует, возвращаем false
        }
        LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr);

        // Проверяем, что дата рождения не в будущем
        if (dateOfBirth.isAfter(LocalDate.now())) {
            return false; // Дата рождения в будущем, возвращаем false
        }

        // Определяем возраст пользователя
        Period age = Period.between(dateOfBirth, LocalDate.now());

        return age.getYears() >= 18 && age.getYears() <= 30;
    }
}
