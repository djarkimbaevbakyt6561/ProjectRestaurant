package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.ChequeRequest;
import peaksoft.dto.responses.AvgTotalPriceChequesRestaurantResponse;
import peaksoft.dto.responses.ChequeResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.TotalCountChequesWaiterResponse;
import peaksoft.services.ChequeService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cheque")
public class ChequeAPI {
    private final ChequeService chequeService;
    @Operation(description = "Get by id")
    @GetMapping("/{chequeId}")
    public ChequeResponse findById(@PathVariable Long chequeId) {
        return chequeService.findById(chequeId);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @Operation(description = "Save ")
    @PostMapping("/save")
    public SimpleResponse save(@RequestBody @Valid ChequeRequest chequeRequest){
        return chequeService.save(chequeRequest);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @Operation(description = "Update")
    @PutMapping("/{chequeId}")
    public SimpleResponse update(@PathVariable Long chequeId, @RequestBody @Valid ChequeRequest chequeRequest, Principal principal) {
        return chequeService.update(chequeId, chequeRequest, principal);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @Operation(description = "Delete")
    @DeleteMapping("/{chequeId}")
    public SimpleResponse delete(@PathVariable Long chequeId, Principal principal) {
        return chequeService.delete(chequeId, principal);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(description = "Get total count of cheques waiter")
    @GetMapping("/getTotal/waiter/{waiterId}")
    public TotalCountChequesWaiterResponse getTotalCountOfChequesWaiter(@PathVariable Long waiterId) {
        return chequeService.getTotalCountOfChequesWaiterForOneDay(waiterId);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(description = "Delete")
    @GetMapping("/getTotal/restaurant/{restaurantId}")
    public AvgTotalPriceChequesRestaurantResponse getAvgTotalPriceChequesOfRestaurant(@PathVariable Long restaurantId) {
        return chequeService.getAvgTotalPriceChequesOfRestaurant(restaurantId);
    }

}
