package dev.marvin.restaurant_listing.business;

import dev.marvin.restaurant_listing.dbaccess.RestaurantRepository;
import dev.marvin.restaurant_listing.domain.dto.RestaurantDTO;
import dev.marvin.restaurant_listing.domain.entity.Restaurant;
import dev.marvin.restaurant_listing.util.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Override
    public RestaurantDTO create(RestaurantDTO restaurantDTO) {
        log.info("creating new restaurant {}", restaurantDTO);
        Restaurant request = RestaurantMapper.INSTANCE.mapToEntity(restaurantDTO);
        Restaurant savedRestaurant = restaurantRepository.save(request);
        return RestaurantMapper.INSTANCE.mapToDto(savedRestaurant);
    }

    @Override
    public List<RestaurantDTO> getAll() {
        return restaurantRepository.findAll().stream()
                .map(RestaurantMapper.INSTANCE::mapToDto)
                .toList();
    }
}
