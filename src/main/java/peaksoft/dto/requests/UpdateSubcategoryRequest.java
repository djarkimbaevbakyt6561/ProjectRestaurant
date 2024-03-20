package peaksoft.dto.requests;

import jakarta.validation.constraints.NotNull;

public record UpdateSubcategoryRequest(
        @NotNull
        String name
        ) {
}
