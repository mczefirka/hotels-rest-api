-- liquibase formatted sql

-- changeset init:2
CREATE TABLE hotel_amenities (
    hotel_id BIGINT NOT NULL,
    amenity VARCHAR(255) NOT NULL,
    CONSTRAINT fk_hotel_amenities_hotel FOREIGN KEY (hotel_id) REFERENCES hotels(id)
);
