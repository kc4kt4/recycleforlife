INSERT INTO fraction_receiving_point (receiving_point_id, fraction_id)
VALUES ((select id from receiving_point where uuid = '69f97365-1256-4a1d-bb04-0550849640e5'), (select id from fraction where uuid = '67e28b51-83c1-497e-bf33-3f663c78b6d0')),
       ((select id from receiving_point where uuid = '69f97365-1256-4a1d-bb04-0550849640e5'), (select id from fraction where uuid = 'ed6ddf7f-05e6-4a42-8745-2fe3825ee951')),
       ((select id from receiving_point where uuid = '69f97365-1256-4a1d-bb04-0550849640e5'), (select id from fraction where uuid = '227bb5c5-1af5-419a-9a9e-c2511f2feae3')),
       ((select id from receiving_point where uuid = '75d4aed7-97f3-4359-8ead-ab3276a01307'), (select id from fraction where uuid = 'fcac4ded-4c82-4638-a50e-85fac2bbdd4e')),
       ((select id from receiving_point where uuid = '75d4aed7-97f3-4359-8ead-ab3276a01307'), (select id from fraction where uuid = '602859ed-7ee5-4ac5-bade-e0e49a9ed350')),
       ((select id from receiving_point where uuid = '69f97365-1256-4a1d-bb04-0550849640e5'), (select id from fraction where uuid = '074fde3b-1083-4898-9884-017d0eb2f2c7')),
       ((select id from receiving_point where uuid = '69f97365-1256-4a1d-bb04-0550849640e5'), (select id from fraction where uuid = 'f59dfd8a-073a-45c9-889e-6151a76c5a27')),
       ((select id from receiving_point where uuid = '69f97365-1256-4a1d-bb04-0550849640e5'), (select id from fraction where uuid = '62e64715-087f-4977-bbe1-f4a45c9a93b6'));
