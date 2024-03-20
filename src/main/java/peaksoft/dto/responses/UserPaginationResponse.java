package peaksoft.dto.responses;

import lombok.Builder;

import java.util.List;

@Builder
public record UserPaginationResponse(
        int page,
        int size,
        List<UserResponse> users
) {

}
