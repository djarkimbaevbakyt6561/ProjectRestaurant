package peaksoft.dto.requests;

import jakarta.validation.constraints.NotNull;
import peaksoft.entities.MenuItem;
import peaksoft.validations.validations.PriceValidation;

import java.math.BigDecimal;

public record MenuItemRequest(
        @NotNull(message = "Name is null")
        String name,
        @NotNull(message = "Image is null")
        String image,
        @PriceValidation
        BigDecimal price,
        @NotNull(message = "Description is null")
        String description,
        @NotNull(message = "isVegetarian is null")
        boolean isVegetarian,
        @NotNull(message = "Restaurant id is null")
        Long restaurantId
) {
    public MenuItem build() {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(this.name);
        menuItem.setImage(this.image);
        menuItem.setPrice(this.price);
        menuItem.setDescription(this.description);
        menuItem.setVegetarian(this.isVegetarian);
        return menuItem;
    }
}
