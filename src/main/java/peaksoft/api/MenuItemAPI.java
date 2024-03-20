package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.MenuItemRequest;
import peaksoft.dto.responses.MenuItemResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.services.MenuItemService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menuItems")
public class MenuItemAPI {
    private final MenuItemService menuItemService;

    @Operation(description = "Get by id")
    @GetMapping("/{menuItemId}")
    public MenuItemResponse findById(@PathVariable Long menuItemId) {
        return menuItemService.findById(menuItemId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @Operation(description = "Save")
    @PostMapping("/{restaurantId}")
    public SimpleResponse save(@RequestBody @Valid MenuItemRequest menuItemRequest) {
        return menuItemService.save(menuItemRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @Operation(description = "Update")
    @PutMapping("/{menuItemId}")
    public SimpleResponse update(@PathVariable Long menuItemId, @RequestBody @Valid MenuItemRequest menuItemRequest) {
        return menuItemService.update(menuItemId, menuItemRequest);
    }

    @Operation(description = "Global search of Menu items")
    @GetMapping("/globalSearch")
    public List<MenuItemResponse> globalSearchAndFilterByIsVegetarianAndSortByPrice(@RequestParam String value, @RequestParam String ascOrDesc, @RequestParam boolean isVegetarian) {
        return menuItemService.globalSearchAndFilterByIsVegetarianAndSortByPrice(value, ascOrDesc, isVegetarian);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @DeleteMapping("/{menuItemId}")
    public SimpleResponse delete(@PathVariable Long menuItemId) {
        return menuItemService.delete(menuItemId);
    }
}
