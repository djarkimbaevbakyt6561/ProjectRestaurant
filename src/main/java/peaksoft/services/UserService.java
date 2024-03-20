package peaksoft.services;

import peaksoft.dto.requests.SignInRequest;
import peaksoft.dto.requests.SignUpChefRequest;
import peaksoft.dto.requests.SignUpWaiterRequest;
import peaksoft.dto.requests.UpdateRequest;
import peaksoft.dto.responses.*;

import java.security.Principal;

public interface UserService {
    UserResponse findById(Long userId);
    UserPaginationResponse getAllEmployeesByRestaurantId(int page, int size, Long restaurantId);
    RegisterResponse signUpWaiter(SignUpWaiterRequest signUpRequest);
    RegisterResponse signUpChef(SignUpChefRequest signUpRequest);

    SignResponse signIn(SignInRequest signInRequest);

    SimpleResponse update(Principal principal, Long userId, UpdateRequest user);
    SimpleResponse delete(Long userId, Principal principal);
}
