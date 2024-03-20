package peaksoft.dto.responses;

import lombok.Builder;

@Builder
public record RegisterResponse(String token, SimpleResponse simpleResponse) {}
