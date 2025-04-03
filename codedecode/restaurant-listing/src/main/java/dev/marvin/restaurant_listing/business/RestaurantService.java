package dev.marvin.restaurant_listing.business;

import dev.marvin.restaurant_listing.domain.dto.RestaurantDTO;

import java.util.List;

public interface RestaurantService {
    RestaurantDTO create(RestaurantDTO restaurantDTO);
    List<RestaurantDTO> getAll();

}
