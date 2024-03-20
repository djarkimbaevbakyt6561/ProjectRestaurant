package peaksoft.services;

import peaksoft.dto.requests.ChequeRequest;
import peaksoft.dto.responses.AvgTotalPriceChequesRestaurantResponse;
import peaksoft.dto.responses.ChequeResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.TotalCountChequesWaiterResponse;

import java.security.Principal;

public interface ChequeService {
    ChequeResponse findById(Long chequeId);

    SimpleResponse save(ChequeRequest chequeRequest);

    SimpleResponse update(Long chequeId, ChequeRequest chequeRequest, Principal principal);

    SimpleResponse delete(Long chequeId, Principal principal);
    TotalCountChequesWaiterResponse getTotalCountOfChequesWaiterForOneDay(Long waiterId);
    AvgTotalPriceChequesRestaurantResponse getAvgTotalPriceChequesOfRestaurant(Long restaurantId);
}
