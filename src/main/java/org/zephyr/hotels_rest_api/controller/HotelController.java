package org.zephyr.hotels_rest_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.zephyr.hotels_rest_api.dto.HotelCreateRequest;
import org.zephyr.hotels_rest_api.dto.HotelFullResponse;
import org.zephyr.hotels_rest_api.dto.HotelShortResponse;
import org.zephyr.hotels_rest_api.service.HotelService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/hotels")
    public List<HotelShortResponse> getAllHotels() {
        return hotelService.getHotels();
    }

    @GetMapping("/hotels/{id}")
    public HotelFullResponse getHotelById(@PathVariable Long id) {
        return hotelService.getFullHotel(id);
    }

    @GetMapping("/search")
    public List<HotelShortResponse> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String amenities) {
        return hotelService.searchHotels(name, brand, city, country, amenities);
    }

    @PostMapping("/hotels")
    @ResponseStatus(HttpStatus.CREATED)
    public HotelShortResponse createHotel(@Valid @RequestBody HotelCreateRequest request) {
        return hotelService.createHotel(request);
    }

    @PostMapping("/hotels/{id}/amenities")
    public HotelFullResponse addAmenities(@PathVariable Long id, @RequestBody List<String> amenities) {
        return hotelService.addAmenities(id, amenities);
    }

    @GetMapping("/histogram/{param}")
    public Map<String, Long> getHistogram(@PathVariable String param) {
        return hotelService.getHistogram(param);
    }
}
