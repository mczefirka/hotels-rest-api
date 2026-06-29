package org.zephyr.hotels_rest_api.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.zephyr.hotels_rest_api.dto.AddressDTO;
import org.zephyr.hotels_rest_api.dto.ContactDTO;
import org.zephyr.hotels_rest_api.dto.HotelCreateRequest;
import org.zephyr.hotels_rest_api.dto.HotelFullResponse;
import org.zephyr.hotels_rest_api.dto.HotelShortResponse;
import org.zephyr.hotels_rest_api.mapper.HotelMapper;
import org.zephyr.hotels_rest_api.model.Address;
import org.zephyr.hotels_rest_api.model.Contact;
import org.zephyr.hotels_rest_api.model.Hotel;
import org.zephyr.hotels_rest_api.repository.HotelRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.singletonList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.mockito.ArgumentMatchers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelServiceImplTest {

    private static final Long TEST_ID = 1L;
    private static final String TEST_NAME = "Larp hotel";
    private static final String TEST_BRAND = "Larpton";
    private static final String TEST_DESCRIPTION = "A hotel built to test my app";
    private static final String TEST_HOUSE_NUMBER = "67";
    private static final String TEST_STREET = "Main St";
    private static final String TEST_CITY = "New York";
    private static final String TEST_COUNTRY = "USA";
    private static final String TEST_POST_CODE = "10001";
    private static final String TEST_PHONE = "+123456789";
    private static final String TEST_EMAIL = "info@larpton.com";

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelServiceImpl hotelService;

    private Hotel testHotel;
    private HotelShortResponse testShortResponse;
    private HotelFullResponse testFullResponse;
    private HotelCreateRequest testCreateRequest;

    @BeforeEach
    void setUp() {
        Address address = new Address(TEST_HOUSE_NUMBER, TEST_STREET, TEST_CITY, TEST_COUNTRY, TEST_POST_CODE);
        Contact contact = new Contact(TEST_PHONE, TEST_EMAIL);

        testHotel = new Hotel();
        testHotel.setId(TEST_ID);
        testHotel.setName(TEST_NAME);
        testHotel.setDescription(TEST_DESCRIPTION);
        testHotel.setBrand(TEST_BRAND);
        testHotel.setAddress(address);
        testHotel.setContacts(contact);
        testHotel.setAmenities(new java.util.ArrayList<>(List.of("wifi", "pool")));

        testShortResponse = new HotelShortResponse();
        testShortResponse.setId(TEST_ID);
        testShortResponse.setName(TEST_NAME);
        testShortResponse.setDescription(TEST_DESCRIPTION);
        testShortResponse.setAddress(TEST_HOUSE_NUMBER + " " + TEST_STREET + ", " + TEST_CITY + ", " + TEST_POST_CODE + ", " + TEST_COUNTRY);
        testShortResponse.setPhone(TEST_PHONE);

        testFullResponse = new HotelFullResponse();
        testFullResponse.setId(TEST_ID);
        testFullResponse.setName(TEST_NAME);
        testFullResponse.setDescription(TEST_DESCRIPTION);
        testFullResponse.setBrand(TEST_BRAND);
        testFullResponse.setAddress(new AddressDTO(TEST_HOUSE_NUMBER, TEST_STREET, TEST_CITY, TEST_COUNTRY, TEST_POST_CODE));
        testFullResponse.setContacts(new ContactDTO(TEST_PHONE, TEST_EMAIL));
        testFullResponse.setAmenities(List.of("wifi", "pool"));

        testCreateRequest = new HotelCreateRequest();
        testCreateRequest.setName(TEST_NAME);
        testCreateRequest.setDescription(TEST_DESCRIPTION);
        testCreateRequest.setBrand(TEST_BRAND);
        testCreateRequest.setAddress(new AddressDTO(TEST_HOUSE_NUMBER, TEST_STREET, TEST_CITY, TEST_COUNTRY, TEST_POST_CODE));
        testCreateRequest.setContacts(new ContactDTO(TEST_PHONE, TEST_EMAIL));
    }

    @Test
    void getHotels_ShouldReturnListOfShortResponses_WhenHotelsExist() {
        when(hotelRepository.findAll()).thenReturn(List.of(testHotel));
        when(hotelMapper.toShortResponse(testHotel)).thenReturn(testShortResponse);

        List<HotelShortResponse> result = hotelService.getHotels();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getId()).isEqualTo(TEST_ID);
        assertThat(result.getFirst().getName()).isEqualTo(TEST_NAME);
        assertThat(result.getFirst().getPhone()).isEqualTo(TEST_PHONE);
        verify(hotelRepository).findAll();
        verify(hotelMapper).toShortResponse(testHotel);
    }

    @Test
    void getHotels_ShouldReturnEmptyList_WhenNoHotelsExist() {
        when(hotelRepository.findAll()).thenReturn(List.of());

        List<HotelShortResponse> result = hotelService.getHotels();

        assertThat(result).isEmpty();
        verify(hotelRepository).findAll();
        verifyNoInteractions(hotelMapper);
    }

    @Test
    void getFullHotel_ShouldReturnFullResponse_WhenHotelExists() {
        when(hotelRepository.findById(TEST_ID)).thenReturn(Optional.of(testHotel));
        when(hotelMapper.toFullResponse(testHotel)).thenReturn(testFullResponse);

        HotelFullResponse result = hotelService.getFullHotel(TEST_ID);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(TEST_ID);
        assertThat(result.getName()).isEqualTo(TEST_NAME);
        assertThat(result.getBrand()).isEqualTo(TEST_BRAND);
        assertThat(result.getAddress().getCity()).isEqualTo(TEST_CITY);
        assertThat(result.getContacts().getPhone()).isEqualTo(TEST_PHONE);
        verify(hotelRepository).findById(TEST_ID);
        verify(hotelMapper).toFullResponse(testHotel);
    }

    @Test
    void getFullHotel_ShouldThrowEntityNotFoundException_WhenHotelDoesNotExist() {
        when(hotelRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> hotelService.getFullHotel(TEST_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining(String.valueOf(TEST_ID));

        verify(hotelRepository).findById(TEST_ID);
        verifyNoInteractions(hotelMapper);
    }

    @Test
    void searchHotels_ShouldReturnFilteredResults_WhenAllParamsProvided() {
        when(hotelRepository.findAll(ArgumentMatchers.<Specification<Hotel>>any())).thenReturn(List.of(testHotel));
        when(hotelMapper.toShortResponse(testHotel)).thenReturn(testShortResponse);

        List<HotelShortResponse> result = hotelService.searchHotels(
                TEST_NAME, TEST_BRAND, TEST_CITY, TEST_COUNTRY, "wifi");

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo(TEST_NAME);
        verify(hotelRepository).findAll(ArgumentMatchers.<Specification<Hotel>>any());
        verify(hotelMapper).toShortResponse(testHotel);
    }

    @Test
    void searchHotels_ShouldReturnAllHotels_WhenNoParamsProvided() {
        when(hotelRepository.findAll(ArgumentMatchers.<Specification<Hotel>>any())).thenReturn(List.of(testHotel));
        when(hotelMapper.toShortResponse(testHotel)).thenReturn(testShortResponse);

        List<HotelShortResponse> result = hotelService.searchHotels(null, null, null, null, null);

        assertThat(result).hasSize(1);
        verify(hotelRepository).findAll(ArgumentMatchers.<Specification<Hotel>>any());
        verify(hotelMapper).toShortResponse(testHotel);
    }

    @Test
    void searchHotels_ShouldReturnAllHotels_WhenBlankParamsProvided() {
        when(hotelRepository.findAll(ArgumentMatchers.<Specification<Hotel>>any())).thenReturn(List.of(testHotel));
        when(hotelMapper.toShortResponse(testHotel)).thenReturn(testShortResponse);

        List<HotelShortResponse> result = hotelService.searchHotels("", "", "", "", "");

        assertThat(result).hasSize(1);
        verify(hotelRepository).findAll(ArgumentMatchers.<Specification<Hotel>>any());
        verify(hotelMapper).toShortResponse(testHotel);
    }

    @Test
    void createHotel_ShouldReturnShortResponse_WhenValidInput() {
        Hotel unsavedHotel = new Hotel();
        unsavedHotel.setName(TEST_NAME);
        unsavedHotel.setBrand(TEST_BRAND);

        when(hotelMapper.toEntity(testCreateRequest)).thenReturn(unsavedHotel);
        when(hotelRepository.save(unsavedHotel)).thenReturn(testHotel);
        when(hotelMapper.toShortResponse(testHotel)).thenReturn(testShortResponse);

        HotelShortResponse result = hotelService.createHotel(testCreateRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(TEST_ID);
        assertThat(result.getName()).isEqualTo(TEST_NAME);
        assertThat(result.getPhone()).isEqualTo(TEST_PHONE);
        verify(hotelMapper).toEntity(testCreateRequest);
        verify(hotelRepository).save(unsavedHotel);
        verify(hotelMapper).toShortResponse(testHotel);
    }

    @Test
    void addAmenities_ShouldAddOnlyNewAmenities_WhenHotelExists() {
        Hotel hotelWithWifi = new Hotel();
        hotelWithWifi.setId(TEST_ID);
        hotelWithWifi.setAmenities(new java.util.ArrayList<>(List.of("wifi")));

        HotelFullResponse fullResponse = new HotelFullResponse();
        fullResponse.setId(TEST_ID);
        fullResponse.setAmenities(List.of("wifi", "pool"));

        when(hotelRepository.findById(TEST_ID)).thenReturn(Optional.of(hotelWithWifi));
        when(hotelRepository.save(hotelWithWifi)).thenReturn(hotelWithWifi);
        when(hotelMapper.toFullResponse(hotelWithWifi)).thenReturn(fullResponse);

        HotelFullResponse result = hotelService.addAmenities(TEST_ID, List.of("wifi", "pool"));

        assertThat(result).isNotNull();
        assertThat(result.getAmenities()).containsExactly("wifi", "pool");
        assertThat(hotelWithWifi.getAmenities()).containsExactly("wifi", "pool");
        verify(hotelRepository).findById(TEST_ID);
        verify(hotelRepository).save(hotelWithWifi);
        verify(hotelMapper).toFullResponse(hotelWithWifi);
    }

    @Test
    void addAmenities_ShouldThrowEntityNotFoundException_WhenHotelDoesNotExist() {
        when(hotelRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> hotelService.addAmenities(TEST_ID, List.of("wifi")))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining(String.valueOf(TEST_ID));

        verify(hotelRepository).findById(TEST_ID);
        verify(hotelRepository, never()).save(any());
        verifyNoInteractions(hotelMapper);
    }

    @Test
    void getHistogram_ShouldReturnBrandCounts_WhenParamIsBrand() {
        when(hotelRepository.countByBrand()).thenReturn(List.of(
                new Object[]{"Hilton", 5L},
                new Object[]{"Marriott", 3L}
        ));

        Map<String, Long> result = hotelService.getHistogram("brand");

        assertThat(result).hasSize(2);
        assertThat(result).containsEntry("Hilton", 5L);
        assertThat(result).containsEntry("Marriott", 3L);
        verify(hotelRepository).countByBrand();
    }

    @Test
    void getHistogram_ShouldReturnCityCounts_WhenParamIsCity() {
        when(hotelRepository.countByCity()).thenReturn(singletonList(
                new Object[]{"New York", 10L}
        ));

        Map<String, Long> result = hotelService.getHistogram("city");

        assertThat(result).hasSize(1);
        assertThat(result).containsEntry("New York", 10L);
        verify(hotelRepository).countByCity();
    }

    @Test
    void getHistogram_ShouldReturnCountryCounts_WhenParamIsCountry() {
        when(hotelRepository.countByCountry()).thenReturn(singletonList(
                new Object[]{"USA", 7L}
        ));

        Map<String, Long> result = hotelService.getHistogram("country");

        assertThat(result).hasSize(1);
        assertThat(result).containsEntry("USA", 7L);
        verify(hotelRepository).countByCountry();
    }

    @Test
    void getHistogram_ShouldReturnAmenityCounts_WhenParamIsAmenities() {
        when(hotelRepository.countByAmenities()).thenReturn(List.of(
                new Object[]{"wifi", 15L},
                new Object[]{"pool", 8L}
        ));

        Map<String, Long> result = hotelService.getHistogram("amenities");

        assertThat(result).hasSize(2);
        assertThat(result).containsEntry("wifi", 15L);
        assertThat(result).containsEntry("pool", 8L);
        verify(hotelRepository).countByAmenities();
    }

    @Test
    void getHistogram_ShouldThrowIllegalArgumentException_WhenParamIsInvalid() {
        assertThatThrownBy(() -> hotelService.getHistogram("invalid"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("invalid");

        verifyNoInteractions(hotelRepository);
    }
}
