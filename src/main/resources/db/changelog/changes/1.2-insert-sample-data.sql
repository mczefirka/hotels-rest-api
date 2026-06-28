-- liquibase formatted sql

-- changeset init:3
INSERT INTO hotels (name, description, brand, house_number, street, city, country, post_code, phone, email, check_in, check_out)
VALUES ('DoubleTree by Hilton Minsk',
        'The DoubleTree by Hilton Minsk offers modern rooms, conference facilities, and views over the city from its central location.',
        'Hilton', '9', 'Pobediteley Avenue', 'Minsk', 'Belarus', '220004',
        '+375 17 309-80-00', 'doubletreeminsk.info@hilton.com', '14:00', '12:00');

INSERT INTO hotels (name, description, brand, house_number, street, city, country, post_code, phone, email, check_in, check_out)
VALUES ('Minsk Marriott Hotel',
        'Minsk Marriott Hotel is a premium 5-star hotel located on the banks of the Svislach River. It features spacious rooms, fine dining restaurants, a spa, fitness center, and modern conference facilities.',
        'Marriott', '20', 'Pobediteley Avenue', 'Minsk', 'Belarus', '220020',
        '+375 17 279-30-00', 'minsk.reservation@marriott.com', '15:00', '12:00');

INSERT INTO hotels (name, description, brand, house_number, street, city, country, post_code, phone, email, check_in, check_out)
VALUES ('Hotel Minsk',
        'Hotel Minsk is a well-known city-center hotel with comfortable rooms and convenient access to major Minsk attractions.',
        'Independent', '11', 'Nezavisimosti Avenue', 'Minsk', 'Belarus', '220030',
        '+375 17 209-90-10', 'info@hotelminsk.by', '14:00', '12:00');

INSERT INTO hotels (name, description, brand, house_number, street, city, country, post_code, phone, email, check_in, check_out)
VALUES ('Renaissance Minsk Hotel',
        'Renaissance Minsk Hotel provides upscale accommodation, a fitness center, and services suited for both business and leisure stays.',
        'Marriott', '1E', 'Dzerzhinskogo Avenue', 'Minsk', 'Belarus', '220036',
        '+375 17 309-90-90', 'info@renaissanceminsk.by', '15:00', '12:00');

-- amenities for DoubleTree by Hilton Minsk (hotel id: 1)
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (1, 'Free parking');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (1, 'Free WiFi');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (1, 'Non-smoking rooms');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (1, 'Concierge');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (1, 'On-site restaurant');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (1, 'Fitness center');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (1, 'Pet-friendly rooms');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (1, 'Room service');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (1, 'Business center');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (1, 'Meeting rooms');

-- amenities for Minsk Marriott Hotel (hotel id: 2)
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (2, 'Free WiFi');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (2, 'Non-smoking rooms');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (2, 'On-site restaurant');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (2, 'Fitness center');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (2, 'Spa');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (2, 'Room service');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (2, 'Business center');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (2, 'Meeting rooms');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (2, 'Valet parking');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (2, 'Concierge');

-- amenities for Hotel Minsk (hotel id: 3)
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (3, 'Free WiFi');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (3, 'Non-smoking rooms');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (3, 'Room service');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (3, 'On-site restaurant');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (3, 'Parking');

-- amenities for Renaissance Minsk Hotel (hotel id: 4)
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (4, 'Free WiFi');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (4, 'Non-smoking rooms');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (4, 'Fitness center');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (4, 'Room service');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (4, 'On-site restaurant');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (4, 'Business center');
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES (4, 'Parking');
