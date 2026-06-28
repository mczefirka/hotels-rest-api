package org.zephyr.hotels_rest_api.dto;

import jakarta.validation.constraints.Email;
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
public class ContactDTO {
    @NotBlank(message = "Phone may not be blank")
    @Size(min = 5, max = 25, message = "Phone may not be less than 5 and greater than 25")
    private String phone;

    @NotBlank(message = "Email may not be blank")
    @Email(message = "Email must be a valid email address")
    private String email;
}
