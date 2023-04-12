insert into roles(role_id, name) values(1, 'ROLE_USER');

insert into airports(icao, name, country) values('EPWA', 'Warsaw Chopin Airport', 'PL');
insert into airports(icao, name, country) values('EDDF', 'Frankfurt Airport', 'DE');
insert into airports(icao, name, country) values('KLAX', 'Los Angeles International Airport', 'US');
insert into airports(icao, name, country) values('CYVR', 'Vancouver International Airport', 'CA');
insert into airports(icao, name, country) values('LFPG', 'Charles de Gaulle Airport', 'FR');
insert into airports(icao, name, country) values('EPKK', 'John Paul II International Airport Kraków-Balice', 'PL');


insert into flights(id, icao_departure, icao_destination, departure_date, airline) values('07199b29-1b40-4cb3-b5ca-2ef046cbe39b', 'EPWA', 'EDDF', '03/12/2023', 'LOT');
insert into flights(id, icao_departure, icao_destination, departure_date, airline) values('0c82e974-1447-4b67-b27c-8f55250dac3e', 'KLAX', 'CYVR', '04/12/2023', 'Lufthansa');
insert into flights(id, icao_departure, icao_destination, departure_date, airline) values('f31eabaf-ee9f-4f69-8285-1a02656807a9', 'EPWA', 'KLAX', '10/30/2023', 'British Airways');
insert into flights(id, icao_departure, icao_destination, departure_date, airline) values('bfe8c71b-af7e-4985-b3ba-64f36827af69', 'CYVR', 'EPWA', '01/15/2023', 'Delta Air Lines');
insert into flights(id, icao_departure, icao_destination, departure_date, airline) values('66f3f11f-a6bd-4b43-bfdb-dd842707b13a', 'EDDF', 'EPWA', '02/03/2023', 'Turkish Airlines');
insert into flights(id, icao_departure, icao_destination, departure_date, airline) values('200be1c1-06dc-40b6-a112-ddc435dc8f50', 'LFPG', 'EPKK', '06/10/2023', 'Lufthansa');
insert into flights(id, icao_departure, icao_destination, departure_date, airline) values('2d8212fc-85fe-4fed-bf7c-03903d3967f6', 'LFPG', 'EPKK', '07/10/2023', 'LOT');

insert into tickets(id, flight_id, user_id, price, travel_class, number_of_available_tickets) values('ea5218e6-32f2-45a8-a65b-6acc276013c5', '200be1c1-06dc-40b6-a112-ddc435dc8f50', null, 3500.0, 'BUSINESS_CLASS', 1);
insert into tickets(id, flight_id, user_id, price, travel_class, number_of_available_tickets) values('01ca2df1-45d4-4905-bc5d-3a1e4febb12d', '200be1c1-06dc-40b6-a112-ddc435dc8f50', null, 2800.0, 'FIRST_CLASS', 3);
