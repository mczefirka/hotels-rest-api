package org.zephyr.hotels_rest_api.service;

import org.zephyr.hotels_rest_api.dto.HotelCreateRequest;
import org.zephyr.hotels_rest_api.dto.HotelFullResponse;
import org.zephyr.hotels_rest_api.dto.HotelShortResponse;

import java.util.List;
import java.util.Map;

public interface HotelService {
    List<HotelShortResponse> getHotels();
    HotelFullResponse getFullHotel(Long id);
    List<HotelShortResponse> searchHotels(String name, String brand, String city, String country, String amenities);
    HotelShortResponse createHotel(HotelCreateRequest request);
    HotelFullResponse addAmenities(Long id, List<String> amenities);
    Map<String, Long> getHistogram(String param);
}
