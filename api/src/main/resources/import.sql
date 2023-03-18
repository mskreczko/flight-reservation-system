insert into roles(role_id, name) values(1, 'ROLE_USER')

insert into tickets(id, flight_id, user_id, price, travel_class) values('45f78f53-ed63-480d-9301-f8a2f7acd70a', NULL, NULL, 250.0, NULL)

insert into airports(icao, name, country) values('EPWA', 'Warsaw Chopin Airport', 'PL')
insert into airports(icao, name, country) values('EDDF', 'Frankfurt Airport', 'DE')

insert into flights(id, icao_departure, icao_destination, departure_date) values('07199b29-1b40-4cb3-b5ca-2ef046cbe39b', 'EPWA', 'EDDF', '03/12/2023')