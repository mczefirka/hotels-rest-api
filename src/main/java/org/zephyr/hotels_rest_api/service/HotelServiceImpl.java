package org.zephyr.hotels_rest_api.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zephyr.hotels_rest_api.dto.HotelCreateRequest;
import org.zephyr.hotels_rest_api.dto.HotelFullResponse;
import org.zephyr.hotels_rest_api.dto.HotelShortResponse;
import org.zephyr.hotels_rest_api.mapper.HotelMapper;
import org.zephyr.hotels_rest_api.model.Hotel;
import org.zephyr.hotels_rest_api.repository.HotelRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<HotelShortResponse> getHotels() {
        return hotelRepository.findAll()
                .stream()
                .map(hotelMapper::toShortResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HotelFullResponse getFullHotel(Long id) {
        return hotelRepository.findById(id)
                .map(hotelMapper::toFullResponse)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelShortResponse> searchHotels(String name, String brand, String city, String country, String amenities) {
        List<Specification<Hotel>> predicates = new ArrayList<>();

        if (name != null && !name.isBlank()) {
            predicates.add((root, query, cb) ->
                    cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (brand != null && !brand.isBlank()) {
            predicates.add((root, query, cb) ->
                    cb.like(cb.lower(root.get("brand")), "%" + brand.toLowerCase() + "%"));
        }
        if (city != null && !city.isBlank()) {
            predicates.add((root, query, cb) ->
                    cb.like(cb.lower(root.get("address").get("city")), "%" + city.toLowerCase() + "%"));
        }
        if (country != null && !country.isBlank()) {
            predicates.add((root, query, cb) ->
                    cb.like(cb.lower(root.get("address").get("country")), "%" + country.toLowerCase() + "%"));
        }
        if (amenities != null && !amenities.isBlank()) {
            predicates.add((root, query, cb) -> {
                query.distinct(true);
                Join<Hotel, String> join = root.join("amenities");
                return cb.like(cb.lower(join), "%" + amenities.toLowerCase() + "%");
            });
        }

        Specification<Hotel> spec = predicates.isEmpty()
                ? (root, query, cb) -> cb.conjunction()
                : Specification.allOf(predicates.toArray(Specification[]::new));

        return hotelRepository.findAll(spec)
                .stream()
                .map(hotelMapper::toShortResponse)
                .toList();
    }

    @Override
    @Transactional
    public HotelShortResponse createHotel(HotelCreateRequest request) {
        Hotel hotel = hotelMapper.toEntity(request);
        Hotel saved = hotelRepository.save(hotel);
        return hotelMapper.toShortResponse(saved);
    }

    @Override
    @Transactional
    public HotelFullResponse addAmenities(Long id, List<String> amenities) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found with id: " + id));

        for (String amenity : amenities) {
            if (!hotel.getAmenities().contains(amenity)) {
                hotel.getAmenities().add(amenity);
            }
        }

        Hotel saved = hotelRepository.save(hotel);
        return hotelMapper.toFullResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> getHistogram(String param) {
        List<Object[]> results = switch (param) {
            case "brand" -> hotelRepository.countByBrand();
            case "city" -> hotelRepository.countByCity();
            case "country" -> hotelRepository.countByCountry();
            case "amenities" -> hotelRepository.countByAmenities();
            default -> throw new IllegalArgumentException("Invalid histogram parameter: " + param);
        };

        Map<String, Long> histogram = new LinkedHashMap<>();
        for (Object[] row : results) {
            histogram.put((String) row[0], (Long) row[1]);
        }
        return histogram;
    }
}
