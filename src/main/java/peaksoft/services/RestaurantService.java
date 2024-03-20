package peaksoft.services;

import peaksoft.dto.requests.RestaurantRequest;
import peaksoft.dto.responses.*;

import java.util.List;


public interface RestaurantService {
    RestaurantResponse findById(Long restaurantId);
    List<RestaurantResponse> findAll();

    SimpleResponse save(RestaurantRequest restaurantRequest);

    SimpleResponse update(Long restaurantId, RestaurantRequest restaurantRequest);

    SimpleResponse delete(Long restaurantId);
}
