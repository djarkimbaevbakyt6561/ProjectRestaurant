package peaksoft.dto.requests;

import java.time.LocalDate;

public record StopListRequest(
        String reason,
        LocalDate date,
        Long menuItemId) {
}
