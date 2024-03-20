package peaksoft.services.impls;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.StopListRequest;
import peaksoft.dto.requests.UpdateStopListRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.StopListResponse;
import peaksoft.entities.MenuItem;
import peaksoft.entities.StopList;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositories.MenuItemRepository;
import peaksoft.repositories.StopListRepository;
import peaksoft.services.StopListService;

@Service
@RequiredArgsConstructor
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;


    @Override
    public StopListResponse findById(Long stopListId) {
        StopList stopList = stopListRepository.findById(stopListId)
                .orElseThrow(() -> new NotFoundException("Stop list with id " + stopListId + " not found!"));

        return new StopListResponse(stopList.getId(), stopList.getReason(), stopList.getDate());
    }

    @Override
    public SimpleResponse save(StopListRequest stopListRequest) {
        StopList stopList = new StopList();
        MenuItem menuItem = menuItemRepository.findById(stopListRequest.menuItemId()).orElseThrow(() -> new NotFoundException("Menu item with id " + stopListRequest.menuItemId() + " not found!"));
        stopList.setReason(stopListRequest.reason());
        stopList.setDate(stopListRequest.date());
        stopList.setMenuItem(menuItem);
        stopListRepository.save(stopList);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully saved!").build();
    }

    @Override
    @Transactional
    public SimpleResponse update(Long stopListId, UpdateStopListRequest stopListRequest) {
        StopList stopList = stopListRepository.findById(stopListId)
                .orElseThrow(() -> new NotFoundException("Stop list with id " + stopListId + " not found!"));

        stopList.setReason(stopListRequest.reason());
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully updated!").build();
    }

    @Override
    public SimpleResponse delete(Long stopListId) {
        stopListRepository.findById(stopListId)
                .orElseThrow(() -> new NotFoundException("Stop list with id " + stopListId + " not found!"));
        stopListRepository.deleteById(stopListId);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully deleted!").build();
    }
}
