package peaksoft.services;

import peaksoft.dto.requests.MenuItemRequest;
import peaksoft.dto.responses.MenuItemResponse;
import peaksoft.dto.responses.SimpleResponse;

import java.util.List;

public interface MenuItemService {
    MenuItemResponse findById(Long menuItemId);

    SimpleResponse save(MenuItemRequest menuItemRequest);

    SimpleResponse update(Long menuItemId, MenuItemRequest menuItemRequest);
    List<MenuItemResponse> globalSearchAndFilterByIsVegetarianAndSortByPrice(String value, String ascOrDesc, boolean isVegetarian);

    SimpleResponse delete(Long menuItemId);
}
