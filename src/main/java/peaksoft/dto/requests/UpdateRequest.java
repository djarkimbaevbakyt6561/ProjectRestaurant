package peaksoft.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import peaksoft.validations.validations.PhoneNumberValidation;

public record UpdateRequest(
        @NotBlank(message = "First name is blank") @NotNull(message = "First name must not be null")
        String firstName,
        @NotBlank(message = "Last name is blank") @NotNull(message = "Last name must not be null")
        String lastName,
        @PhoneNumberValidation
        String phoneNumber,
        @NotNull(message = "Experience must not be null")
        Byte experience
) {
}
