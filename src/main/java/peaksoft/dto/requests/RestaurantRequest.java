package peaksoft.dto.requests;

import jakarta.validation.constraints.NotNull;
import peaksoft.entities.Restaurant;

public record RestaurantRequest(
        @NotNull(message = "Name is null")
        String name,
        @NotNull(message = "Location is null")
        String location,
        @NotNull(message = "RestType is null")
        String restType,
        @NotNull(message = "Service is null")
        Byte service) {
    public Restaurant build(){
        Restaurant restaurant = new Restaurant();
        restaurant.setName(this.name);
        restaurant.setLocation(this.location);
        restaurant.setRestType(this.restType);
        restaurant.setService(this.service);
        return restaurant;
    }
}
