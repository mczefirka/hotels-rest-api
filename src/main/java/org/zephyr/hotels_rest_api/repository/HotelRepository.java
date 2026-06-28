package org.zephyr.hotels_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.zephyr.hotels_rest_api.model.Hotel;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {

    @Query("SELECT h.brand, COUNT(h) FROM Hotel h GROUP BY h.brand ORDER BY COUNT(h) DESC")
    List<Object[]> countByBrand();

    @Query("SELECT h.address.city, COUNT(h) FROM Hotel h GROUP BY h.address.city ORDER BY COUNT(h) DESC")
    List<Object[]> countByCity();

    @Query("SELECT h.address.country, COUNT(h) FROM Hotel h GROUP BY h.address.country ORDER BY COUNT(h) DESC")
    List<Object[]> countByCountry();

    @Query("SELECT a, COUNT(a) FROM Hotel h JOIN h.amenities a GROUP BY a ORDER BY COUNT(a) DESC")
    List<Object[]> countByAmenities();
}
