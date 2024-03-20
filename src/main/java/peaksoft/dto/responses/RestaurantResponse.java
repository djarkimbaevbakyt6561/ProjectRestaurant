package peaksoft.dto.responses;

import lombok.Builder;

import java.util.List;

@Builder
public record RestaurantResponse(
        Long id,
        String name,
        String location,
        String restType,
        Byte service,
        Byte numberOfEmployees,
        List<MenuItemResponse> menuItems) {
}
