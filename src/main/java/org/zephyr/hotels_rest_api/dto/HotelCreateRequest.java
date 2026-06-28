package org.zephyr.hotels_rest_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HotelCreateRequest {
    @NotBlank(message = "Name may not be blank")
    @Size(min = 2, max = 50, message = "Name may not be less than 2 and greater than 50")
    private String name;

    @Size(max = 500, message = "Description may not be greater than 500")
    private String description;

    @NotBlank(message = "Brand may not be blank")
    @Size(min = 2, max = 50, message = "Brand may not be less than 2 and greater than 50")
    private String brand;

    @NotNull(message = "Address may not be null")
    @Valid
    private AddressDTO address;

    @NotNull(message = "Contacts may not be null")
    @Valid
    private ContactDTO contacts;

    @Valid
    private ArrivalTimeDTO arrivalTime;
}
