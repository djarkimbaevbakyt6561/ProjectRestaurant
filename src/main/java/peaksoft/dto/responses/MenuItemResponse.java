package peaksoft.dto.responses;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record MenuItemResponse(
        Long id,
        String name,
        String image,
        BigDecimal price,
        String description,
        boolean isVegetarian,
        StopListResponse stopList,
        List<SubcategoryResponse> subcategories
) {
}
