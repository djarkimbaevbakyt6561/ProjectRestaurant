package peaksoft.dto.responses;

import lombok.Builder;

@Builder
public record SubcategoryResponse(
        Long id,
        String name,
        Long categoryId) {
}
