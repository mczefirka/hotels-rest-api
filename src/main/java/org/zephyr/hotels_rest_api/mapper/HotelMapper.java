package org.zephyr.hotels_rest_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.zephyr.hotels_rest_api.dto.*;
import org.zephyr.hotels_rest_api.model.Address;
import org.zephyr.hotels_rest_api.model.ArrivalTime;
import org.zephyr.hotels_rest_api.model.Contact;
import org.zephyr.hotels_rest_api.model.Hotel;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    @Mapping(target = "address", source = "address", qualifiedByName = "formatAddress")
    @Mapping(target = "phone", source = "contacts.phone")
    HotelShortResponse toShortResponse(Hotel hotel);

    HotelFullResponse toFullResponse(Hotel hotel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    Hotel toEntity(HotelCreateRequest request);

    AddressDTO toDto(Address address);

    Address toEntity(AddressDTO dto);

    ContactDTO toDto(Contact contact);

    Contact toEntity(ContactDTO dto);

    ArrivalTimeDTO toDto(ArrivalTime arrivalTime);

    ArrivalTime toEntity(ArrivalTimeDTO dto);

    @Named("formatAddress")
    default String formatAddress(Address address) {
        if (address == null) {
            return null;
        }

        return address.getHouseNumber() + " " + address.getStreet()
                + ", " + address.getCity()
                + ", " + address.getPostCode()
                + ", " + address.getCountry();
    }
}
