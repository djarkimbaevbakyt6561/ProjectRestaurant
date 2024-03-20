package peaksoft.services.impls;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.jwt.JwtService;
import peaksoft.dto.requests.SignInRequest;
import peaksoft.dto.requests.SignUpChefRequest;
import peaksoft.dto.requests.SignUpWaiterRequest;
import peaksoft.dto.requests.UpdateRequest;
import peaksoft.dto.responses.*;
import peaksoft.entities.Restaurant;
import peaksoft.entities.User;
import peaksoft.enums.Role;
import peaksoft.exceptions.AuthorizeException;
import peaksoft.exceptions.ForbiddenException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositories.RestaurantRepository;
import peaksoft.repositories.UserRepository;
import peaksoft.services.UserService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostConstruct
    private void saveAdmin() {
        Restaurant restaurant = new Restaurant();
        restaurant.setService((byte) 20);
        restaurant.setName("Super restoran");
        restaurant.setLocation("Super street 24");
        restaurant.setRestType("FAST_FOOD");
        restaurant.setNumberOfEmployees((byte) 0);
        userRepository.save(
                User.builder()
                        .firstName("Admin")
                        .lastName("Adminov")
                        .phoneNumber("+996700000000")
                        .email("admin@gmail.com")
                        .role(Role.ADMIN)
                        .restaurant(restaurant)
                        .password(passwordEncoder.encode("admin123"))
                        .build()
        );

    }

//    @PostConstruct
//    private void saveClient() {
//        userRepository.save(
//                User.builder()
//                        .firstName("Ars")
//                        .lastName("Dzharkymbaev")
//                        .gender(Gender.MALE)
//                        .dateOfBirth(LocalDate.of(2000, 1, 1))
//                        .phoneNumber("+996702300000")
//                        .email("user@gmail.com")
//                        .password(passwordEncoder.encode("user123"))
//                        .role(Role.USER)
//                        .build()
//        );
//
//    }

    @Override
    public UserResponse findById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found!"));

        return UserResponse
                .builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .role(user.getRole())
                .experience(user.getExperience())
                .build();
    }

    @Override
    public UserPaginationResponse getAllEmployeesByRestaurantId(int page, int size, Long restaurantId) {
        restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant with id " + restaurantId + " not found!"));

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<User> userPages = userRepository.findAllByRestaurantId(restaurantId, pageable);

        List<UserResponse> userResponses = userPages.getContent().stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .dateOfBirth(user.getDateOfBirth())
                        .phoneNumber(user.getPhoneNumber())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .experience(user.getExperience())
                        .build())
                .collect(Collectors.toList());

        return UserPaginationResponse.builder()
                .page(userPages.getNumber() + 1)
                .size(userPages.getTotalPages())
                .users(userResponses)
                .build();
    }


    @Override
    @Transactional
    public RegisterResponse signUpWaiter(SignUpWaiterRequest signUpRequest) {
        User user = getRegisterResponse(signUpRequest.email(), signUpRequest.firstName(), signUpRequest.lastName(), signUpRequest.experience(), signUpRequest.phoneNumber(), signUpRequest.dateOfBirth(), signUpRequest.password(), signUpRequest.restaurantId());
        user.setRole(Role.WAITER);
        userRepository.save(user);

        String newToken = jwtService.createToken(user);
        return RegisterResponse.builder()
                .token(newToken)
                .simpleResponse(
                        SimpleResponse.builder()
                                .httpStatus(HttpStatus.OK)
                                .message("Successfully saved!")
                                .build())
                .build();
    }

    @Override
    @Transactional
    public RegisterResponse signUpChef(SignUpChefRequest signUpRequest) {
        User user = getRegisterResponse(signUpRequest.email(), signUpRequest.firstName(), signUpRequest.lastName(), signUpRequest.experience(), signUpRequest.phoneNumber(), signUpRequest.dateOfBirth(), signUpRequest.password(), signUpRequest.restaurantId());
        user.setRole(Role.CHEF);
        userRepository.save(user);

        String newToken = jwtService.createToken(user);
        return RegisterResponse.builder()
                .token(newToken)
                .simpleResponse(
                        SimpleResponse.builder()
                                .httpStatus(HttpStatus.OK)
                                .message("Successfully saved!")
                                .build())
                .build();
    }

    @Transactional
    protected User getRegisterResponse(String email, String firstName, String lastName, Byte experience, String phoneNumber, String dateOfBirth, String password, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFoundException("Restaurant with id " + restaurantId + " not found!"));
        boolean exists = userRepository.existsByEmail(email);
        if (exists) throw new AuthorizeException("Email : " + email + " already exist");

        User user = new User();
        user.setPhoneNumber(phoneNumber);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setExperience(experience);
        user.setDateOfBirth(LocalDate.parse(dateOfBirth));

        if (restaurant.getNumberOfEmployees() > 15) {
            throw new ForbiddenException("Restaurant is full, cannot assign more employees!");
        }
        restaurant.setNumberOfEmployees((byte) (restaurant.getNumberOfEmployees() + 1));
        user.setRestaurant(restaurant);

        user.setPassword(passwordEncoder.encode(password));
        return user;
    }

    @Override
    public SignResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.findByEmail(signInRequest.email()).orElseThrow(() ->
                new NotFoundException("User with email: " + signInRequest.email() + " not found!"));

        String encodePassword = user.getPassword();
        String password = signInRequest.password();

        boolean matches = passwordEncoder.matches(password, encodePassword);

        if (!matches) throw new AuthorizeException("Invalid password");


        String token = jwtService.createToken(user);
        return SignResponse.builder()
                .token(token)
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    @Transactional
    public SimpleResponse update(Principal principal, Long userId, UpdateRequest user) {
        String email = principal.getName();
        User loginUser = userRepository.getByEmail(email);
        User realUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found!"));

        if (loginUser.getRole().equals(Role.ADMIN) || loginUser.getId().equals(userId)) {
            if (realUser != null) {
                realUser.setLastName(user.lastName());
                realUser.setFirstName(user.firstName());
                realUser.setPhoneNumber(user.phoneNumber());
                realUser.setExperience(user.experience());
            }
        } else {
            throw new ForbiddenException("User can't update if he doesn't owner or role not equals Admin");
        }
        return SimpleResponse.builder().message("Successfully updated!").httpStatus(HttpStatus.OK).build();
    }

    @Override
    @Transactional
    public SimpleResponse delete(Long userId, Principal principal) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found!"));
        User loginUser = userRepository.getByEmail(principal.getName());

        if (loginUser.getRole().equals(Role.ADMIN) || loginUser.getId().equals(userId)) {
            Restaurant restaurant = user.getRestaurant();
            byte numberOfEmployees = restaurant.getNumberOfEmployees();
            if (numberOfEmployees > 0) {
                restaurant.setNumberOfEmployees((byte) (numberOfEmployees - 1));
            }
            userRepository.deleteById(userId);
        } else {
            throw new ForbiddenException("User can't delete if he doesn't owner or role not equals Admin");
        }
        return SimpleResponse.builder().
                httpStatus(HttpStatus.OK).
                message("Successfully deleted!").
                build();
    }

}
