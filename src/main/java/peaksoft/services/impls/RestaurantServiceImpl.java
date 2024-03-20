package peaksoft.services.impls;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.RestaurantRequest;
import peaksoft.dto.responses.*;
import peaksoft.entities.Restaurant;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositories.RestaurantRepository;
import peaksoft.services.RestaurantService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Override
    public RestaurantResponse findById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant with id " + restaurantId + " not found!"));

        List<MenuItemResponse> menuItemResponses = restaurant.getMenuItems().stream()
                .map(menuItem -> MenuItemResponse.builder()
                        .id(menuItem.getId())
                        .name(menuItem.getName())
                        .price(menuItem.getPrice())
                        .isVegetarian(menuItem.isVegetarian())
                        .description(menuItem.getDescription())
                        .subcategories(menuItem.getSubcategories().stream()
                                .map(subcategory -> SubcategoryResponse.builder()
                                        .categoryId(subcategory.getCategory().getId())
                                        .id(subcategory.getId())
                                        .name(subcategory.getName())
                                        .build())
                                .collect(Collectors.toList()))
                        .stopList(StopListResponse.builder()
                                .date(menuItem.getStopList().getDate())
                                .reason(menuItem.getStopList().getReason())
                                .id(menuItem.getStopList().getId())
                                .build())
                        .image(menuItem.getImage())
                        .build())
                .collect(Collectors.toList());

        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .service(restaurant.getService())
                .name(restaurant.getName())
                .location(restaurant.getLocation())
                .numberOfEmployees(restaurant.getNumberOfEmployees())
                .menuItems(menuItemResponses)
                .build();
    }


    @Override
    public List<RestaurantResponse> findAll() {
        return null;
    }

    @Override
    public SimpleResponse save(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRequest.build();
        restaurant.setNumberOfEmployees((byte) 0);
        restaurantRepository.save(restaurant);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully saved!").build();
    }

    @Override
    @Transactional
    public SimpleResponse update(Long restaurantId, RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFoundException("Restaurant with id " + restaurantId + " not found!"));
        restaurant.setRestType(restaurantRequest.restType());
        restaurant.setService(restaurantRequest.service());
        restaurant.setName(restaurantRequest.name());
        restaurant.setLocation(restaurantRequest.location());
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully updated!").build();
    }

    @Override
    public SimpleResponse delete(Long restaurantId) {
        restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFoundException("Social media with id " + restaurantId + " not found!"));
        restaurantRepository.deleteById(restaurantId);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully deleted!").build();
    }
}
