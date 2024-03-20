package peaksoft.services;

import peaksoft.dto.requests.StopListRequest;
import peaksoft.dto.requests.UpdateStopListRequest;
import peaksoft.dto.responses.StopListResponse;
import peaksoft.dto.responses.SimpleResponse;


public interface StopListService {
    StopListResponse findById(Long stopListId);

    SimpleResponse save(StopListRequest stopListRequest);

    SimpleResponse update(Long stopListId, UpdateStopListRequest stopListRequest);

    SimpleResponse delete(Long stopListId);
}
