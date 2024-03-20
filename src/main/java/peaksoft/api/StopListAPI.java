package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.StopListRequest;
import peaksoft.dto.requests.UpdateStopListRequest;
import peaksoft.dto.responses.StopListResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.services.StopListService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stopList")
public class StopListAPI {
    private final StopListService stopListService;
    @Operation(description = "Get by id")
    @GetMapping("/{stopListId}")
    public StopListResponse findById(@PathVariable Long stopListId) {
        return stopListService.findById(stopListId);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @Operation(description = "Save ")
    @PostMapping("/save")
    public SimpleResponse save(@RequestBody @Valid StopListRequest stopListRequest){
        return stopListService.save(stopListRequest);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @Operation(description = "Update")
    @PutMapping("/{stopListId}")
    public SimpleResponse update(@PathVariable Long stopListId, @RequestBody @Valid UpdateStopListRequest stopListRequest) {
        return stopListService.update(stopListId, stopListRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @Operation(description = "Delete")
    @DeleteMapping("/{stopListId}")
    public SimpleResponse delete(@PathVariable Long stopListId) {
        return stopListService.delete(stopListId);
    }

}
