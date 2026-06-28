package org.zephyr.hotels_rest_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressDTO {
    @NotBlank(message = "House number may not be blank")
    private String houseNumber;

    @NotBlank(message = "Street may not be blank")
    @Size(min = 2, max = 50, message = "Street may not be less than 2 and greater than 50")
    private String street;

    @NotBlank(message = "City may not be blank")
    @Size(min = 2, max = 50, message = "City may not be less than 2 and greater than 50")
    private String city;

    @NotBlank(message = "Country may not be blank")
    @Size(min = 2, max = 50, message = "Country may not be less than 2 and greater than 50")
    private String country;

    @NotBlank(message = "Post code may not be blank")
    @Size(min = 3, max = 10, message = "Post code may not be less than 3 and greater than 10")
    private String postCode;
}
