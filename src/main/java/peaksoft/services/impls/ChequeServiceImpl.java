package peaksoft.services.impls;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.ChequeRequest;
import peaksoft.dto.responses.*;
import peaksoft.entities.Cheque;
import peaksoft.entities.MenuItem;
import peaksoft.entities.User;
import peaksoft.enums.Role;
import peaksoft.exceptions.ForbiddenException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositories.ChequeRepository;
import peaksoft.repositories.MenuItemRepository;
import peaksoft.repositories.UserRepository;
import peaksoft.services.ChequeService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public ChequeResponse findById(Long chequeId) {
        Cheque cheque = chequeRepository.findById(chequeId)
                .orElseThrow(() -> new NotFoundException("Cheque with id " + chequeId + " not found!"));
        List<MenuItemResponse> menuItemResponses = cheque.getMenuItems().stream()
                .map(menuItem -> MenuItemResponse.builder()
                        .id(menuItem.getId())
                        .name(menuItem.getName())
                        .price(menuItem.getPrice())
                        .isVegetarian(menuItem.isVegetarian())
                        .description(menuItem.getDescription())
                        .image(menuItem.getImage())
                        .build())
                .toList();

        return ChequeResponse.fromEntity(cheque, menuItemResponses, cheque.getUser().getRestaurant().getService());
    }

    @Override
    public SimpleResponse save(ChequeRequest chequeRequest) {
        User user = userRepository.findById(chequeRequest.userId()).orElseThrow(() ->
                new NotFoundException("User with id: " + chequeRequest.userId() + " not found!"));
        List<MenuItem> menuItems = new ArrayList<>();
        for (Long menuItemId : chequeRequest.menuItemIds()) {
            MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() ->
                    new NotFoundException("Menu item with id: " + menuItemId + " not found!"));
            menuItems.add(menuItem);
        }
        Cheque cheque = new Cheque();
        cheque.setPriceAverage(getPriceAverage(menuItems));
        cheque.setUser(user);
        cheque.setMenuItems(menuItems);
        chequeRepository.save(cheque);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully saved!").build();
    }

    private static BigDecimal getPriceAverage(List<MenuItem> menuItems) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (MenuItem menuItem : menuItems) {
            totalPrice = totalPrice.add(menuItem.getPrice());
        }
        BigDecimal priceAverage = BigDecimal.ZERO;
        if (!menuItems.isEmpty()) {
            priceAverage = totalPrice.divide(BigDecimal.valueOf(menuItems.size()), 2, RoundingMode.HALF_UP);
        }
        return priceAverage;
    }

    @Override
    @Transactional
    public SimpleResponse update(Long chequeId, ChequeRequest chequeRequest, Principal principal) {
        Cheque cheque = chequeRepository.findById(chequeId)
                .orElseThrow(() -> new NotFoundException("Cheque with id " + chequeId + " not found!"));
        User loginUser = userRepository.getByEmail(principal.getName());

        if (loginUser.getRole().equals(Role.ADMIN) || loginUser.getId().equals(cheque.getUser().getId())) {
            List<MenuItem> menuItems = new ArrayList<>();
            for (Long menuItemId : chequeRequest.menuItemIds()) {
                MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() ->
                        new NotFoundException("Menu item with id: " + menuItemId + " not found!"));
                menuItems.add(menuItem);
            }
            cheque.setPriceAverage(getPriceAverage(menuItems));
            cheque.setMenuItems(menuItems);
        } else {
            throw new ForbiddenException("User can't update cheque if he doesn't owner or role not equals Admin");
        }

        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully updated!").build();
    }

    @Override
    public SimpleResponse delete(Long chequeId, Principal principal) {
        Cheque cheque = chequeRepository.findById(chequeId)
                .orElseThrow(() -> new NotFoundException("Cheque with id " + chequeId + " not found!"));
        User loginUser = userRepository.getByEmail(principal.getName());

        if (loginUser.getRole().equals(Role.ADMIN) || loginUser.getId().equals(cheque.getUser().getId())) {
            chequeRepository.deleteById(chequeId);
        } else {
            throw new ForbiddenException("User can't delete cheque if he doesn't owner or role not equals Admin");
        }
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully deleted!").build();
    }

    @Override
    public TotalCountChequesWaiterResponse getTotalCountOfChequesWaiterForOneDay(Long waiterId) {
        LocalDate today = LocalDate.now();
        Long totalCount = chequeRepository.countByUserIdAndCreatedAt(waiterId, today);
        return new TotalCountChequesWaiterResponse(totalCount);
    }

    @Override
    public AvgTotalPriceChequesRestaurantResponse getAvgTotalPriceChequesOfRestaurant(Long restaurantId) {
        LocalDate today = LocalDate.now();
        BigDecimal avgPrice = chequeRepository.findAvgTotalPriceByUserRestaurantId(restaurantId, today);
        if (avgPrice == null) {
            throw new NotFoundException("No cheques found for restaurant with id " + restaurantId);
        }
        return new AvgTotalPriceChequesRestaurantResponse(avgPrice);
    }
}
