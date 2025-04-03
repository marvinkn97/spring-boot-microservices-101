package dev.marvin.restaurant_listing.api;

import dev.marvin.restaurant_listing.business.RestaurantService;
import dev.marvin.restaurant_listing.domain.dto.RestaurantDTO;
import dev.marvin.restaurant_listing.domain.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantDTO> addRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        RestaurantDTO savedRestaurant = restaurantService.create(restaurantDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRestaurant);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> fetchAllRestaurants() {
        List<RestaurantDTO> restaurants = restaurantService.getAll();
        return ResponseEntity.ok(restaurants);
    }
}
