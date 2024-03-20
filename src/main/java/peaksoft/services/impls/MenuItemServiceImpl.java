package peaksoft.services.impls;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.MenuItemRequest;
import peaksoft.dto.responses.MenuItemResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.StopListResponse;
import peaksoft.dto.responses.SubcategoryResponse;
import peaksoft.entities.MenuItem;
import peaksoft.entities.StopList;
import peaksoft.entities.Subcategory;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositories.MenuItemRepository;
import peaksoft.services.MenuItemService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;

    @Override
    public MenuItemResponse findById(Long menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new NotFoundException("MenuItem with id " + menuItemId + " not found!"));

        return mapToMenuItemResponse(menuItem);
    }

    @Override
    public SimpleResponse save(MenuItemRequest menuItemRequest) {
        MenuItem menuItem = menuItemRequest.build();
        menuItemRepository.save(menuItem);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully saved!").build();
    }

    @Override
    @Transactional
    public SimpleResponse update(Long menuItemId, MenuItemRequest menuItemRequest) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new NotFoundException("MenuItem with id " + menuItemId + " not found!"));

        menuItem.setName(menuItemRequest.name());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());

        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully updated!").build();
    }

    @Override
    public List<MenuItemResponse> globalSearchAndFilterByIsVegetarianAndSortByPrice(String value, String ascOrDesc, boolean isVegetarian) {
        List<MenuItem> menuItems = menuItemRepository.findAllByNameContainingIgnoreCase(value);

        if (isVegetarian) {
            menuItems = menuItems.stream()
                    .filter(MenuItem::isVegetarian)
                    .collect(Collectors.toList());
        }

        if (ascOrDesc.equalsIgnoreCase("asc")) {
            menuItems.sort(Comparator.comparing(MenuItem::getPrice));
        } else if (ascOrDesc.equalsIgnoreCase("desc")) {
            menuItems.sort(Comparator.comparing(MenuItem::getPrice).reversed());
        }

        return menuItems.stream()
                .map(this::mapToMenuItemResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SimpleResponse delete(Long menuItemId) {
        menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new NotFoundException("MenuItem with id " + menuItemId + " not found!"));
        menuItemRepository.deleteById(menuItemId);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully deleted!").build();
    }

    private MenuItemResponse mapToMenuItemResponse(MenuItem menuItem) {
        return new MenuItemResponse(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getImage(),
                menuItem.getPrice(),
                menuItem.getDescription(),
                menuItem.isVegetarian(),
                menuItem.getStopList() != null ? mapToStopListResponse(menuItem.getStopList()) : null,
                menuItem.getSubcategories() != null ? mapToSubcategoryResponses(menuItem.getSubcategories()) : null
        );
    }

    private StopListResponse mapToStopListResponse(StopList stopList) {
        return new StopListResponse(stopList.getId(), stopList.getReason(), stopList.getDate());
    }

    private List<SubcategoryResponse> mapToSubcategoryResponses(List<Subcategory> subcategories) {
        return subcategories.stream()
                .map(subcategory -> new SubcategoryResponse(subcategory.getId(), subcategory.getName(), subcategory.getCategory().getId()))
                .collect(Collectors.toList());
    }
}
