package peaksoft.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import peaksoft.validations.validations.WaiterDateOfBirthValidation;
import peaksoft.validations.validations.PasswordValidation;
import peaksoft.validations.validations.PhoneNumberValidation;

public record SignUpWaiterRequest(
        @NotBlank(message = "First name is blank") @NotNull(message = "First name must not be null")
        String firstName,
        @NotBlank(message = "Last name is blank") @NotNull(message = "Last name must not be null")
        String lastName,
        @Email
        String email,
        @PasswordValidation
        String password,
        @PhoneNumberValidation
        String phoneNumber,
        @WaiterDateOfBirthValidation
        String dateOfBirth,
        @NotNull(message = "Experience must not be null")
        Byte experience,
        @NotNull
        Long restaurantId

) {

}
