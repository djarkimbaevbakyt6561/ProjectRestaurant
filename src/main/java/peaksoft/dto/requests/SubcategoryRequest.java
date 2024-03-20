package peaksoft.dto.requests;

import jakarta.validation.constraints.NotNull;

public record SubcategoryRequest(
        @NotNull
        String name,
        @NotNull
        Long categoryId
) {

}
