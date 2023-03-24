insert into roles(role_id, name) values(1, 'ROLE_USER')

insert into tickets(id, flight_id, user_id, price, travel_class) values('45f78f53-ed63-480d-9301-f8a2f7acd70a', NULL, NULL, 250.0, NULL)

insert into airports(icao, name, country) values('EPWA', 'Warsaw Chopin Airport', 'PL')
insert into airports(icao, name, country) values('EDDF', 'Frankfurt Airport', 'DE')
insert into airports(icao, name, country) values('KLAX', 'Los Angeles International Airport', 'US')
insert into airports(icao, name, country) values('CYVR', 'Vancouver International Airport', 'CA')

insert into flights(id, icao_departure, icao_destination, departure_date, price) values('07199b29-1b40-4cb3-b5ca-2ef046cbe39b', 'EPWA', 'EDDF', '03/12/2023', 2530.0)
insert into flights(id, icao_departure, icao_destination, departure_date, price) values('0c82e974-1447-4b67-b27c-8f55250dac3e', 'KLAX', 'CYVR', '04/12/2023', 600.0)
insert into flights(id, icao_departure, icao_destination, departure_date, price) values('f31eabaf-ee9f-4f69-8285-1a02656807a9', 'EPWA', 'KLAX', '10/30/2023', 4000.0)
insert into flights(id, icao_departure, icao_destination, departure_date, price) values('bfe8c71b-af7e-4985-b3ba-64f36827af69', 'CYVR', 'EPWA', '01/15/2023', 4300.0)
insert into flights(id, icao_departure, icao_destination, departure_date, price) values('66f3f11f-a6bd-4b43-bfdb-dd842707b13a', 'EDDF', 'EPWA', '02/03/2023', 1800.0)
