package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.RestaurantRequest;
import peaksoft.dto.responses.RestaurantResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.services.RestaurantService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantAPI {
    private final RestaurantService restaurantService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(description = "Get by id")
    @GetMapping("/{restaurantId}")
    public RestaurantResponse findById(@PathVariable Long restaurantId) {
        return restaurantService.findById(restaurantId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(description = "Get all")
    @GetMapping("/getAll")
    public List<RestaurantResponse> getAllEmployeesByRestaurantId() {
        return restaurantService.findAll();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(description = "Save ")
    @PostMapping("/save")
    public SimpleResponse save(@RequestBody @Valid RestaurantRequest restaurantRequest){
        return restaurantService.save(restaurantRequest);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(description = "Update")
    @PutMapping("/{restaurantId}")
    public SimpleResponse update(@PathVariable Long restaurantId, @RequestBody @Valid RestaurantRequest restaurantRequest) {
        return restaurantService.update(restaurantId, restaurantRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(description = "Delete")
    @DeleteMapping("/{restaurantId}")
    public SimpleResponse delete(@PathVariable Long restaurantId) {
        return restaurantService.delete(restaurantId);
    }
}
