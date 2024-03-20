package peaksoft.dto.responses;

import peaksoft.entities.Cheque;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.List;

public record ChequeResponse(
        Long id,
        String waiterFullName,
        List<MenuItemResponse> items,
        BigDecimal priceAverage,
        ZonedDateTime createdAt,
        Byte service,
        BigDecimal totalPrice) {

    public static ChequeResponse fromEntity(Cheque cheque, List<MenuItemResponse> items, Byte service) {
        BigDecimal totalPrice = calculateTotalPrice(items);
        BigDecimal priceAverage = calculatePriceAverage(items, totalPrice);

        String fullName = cheque.getUser().getFirstName().concat(" " + cheque.getUser().getLastName());

        return new ChequeResponse(
                cheque.getId(),
                fullName,
                items,
                priceAverage,
                cheque.getCreatedAt(),
                service,
                totalPrice
        );
    }

    private static BigDecimal calculateTotalPrice(List<MenuItemResponse> items) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (MenuItemResponse menuItem : items) {
            totalPrice = totalPrice.add(menuItem.price());
        }
        return totalPrice;
    }

    private static BigDecimal calculatePriceAverage(List<MenuItemResponse> items, BigDecimal totalPrice) {
        BigDecimal priceAverage = BigDecimal.ZERO;
        if (!items.isEmpty()) {
            priceAverage = totalPrice.divide(BigDecimal.valueOf(items.size()), 2, RoundingMode.HALF_UP);
        }
        return priceAverage;
    }
}
