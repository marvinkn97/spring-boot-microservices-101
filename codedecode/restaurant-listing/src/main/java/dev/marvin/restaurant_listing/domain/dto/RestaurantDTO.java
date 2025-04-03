package dev.marvin.restaurant_listing.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {
    private String id;
    private String name;
    private String description;
    private String address;
    private String  city;
}
