package org.zephyr.hotels_rest_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HotelShortResponse {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
}
