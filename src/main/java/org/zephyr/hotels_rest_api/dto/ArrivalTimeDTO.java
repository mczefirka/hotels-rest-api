package org.zephyr.hotels_rest_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArrivalTimeDTO {
    @NotBlank(message = "Check-in may not be blank")
    private String checkIn;

    private String checkOut;
}
