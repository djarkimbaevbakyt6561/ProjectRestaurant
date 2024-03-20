package peaksoft.dto.requests;

import java.util.List;

public record ChequeRequest(
        List<Long> menuItemIds,
        Long userId) {
}
