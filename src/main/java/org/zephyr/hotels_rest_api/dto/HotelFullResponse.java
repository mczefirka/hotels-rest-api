package org.zephyr.hotels_rest_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HotelFullResponse {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private AddressDTO address;
    private ContactDTO contacts;
    private ArrivalTimeDTO arrivalTime;
    private List<String> amenities;
}
