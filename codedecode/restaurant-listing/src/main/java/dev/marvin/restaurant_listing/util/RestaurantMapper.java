package dev.marvin.restaurant_listing.util;

import dev.marvin.restaurant_listing.domain.dto.RestaurantDTO;
import dev.marvin.restaurant_listing.domain.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RestaurantMapper {
    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    Restaurant mapToEntity(RestaurantDTO restaurantDTO);

    RestaurantDTO mapToDto(Restaurant restaurant);
}
