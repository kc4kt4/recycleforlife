INSERT INTO receiving_point (uuid, name, description, latitude, longitude, subtitle, email, msisdn, working_hours)
VALUES ('1c06306f-7c57-4c24-8eb9-6e12280da4a4', 'Контейнер для ПЭТ-бутылок Чистый дом', 'К сдаче принимаются ПЭТ-бутылки', 59.716210, 30.430341,
        'subtitle1', 'email1@email.email', '79998881111', '{}'::jsonb),
       ('75d4aed7-97f3-4359-8ead-ab3276a01307', 'Фандомат Ecoplatform', 'Можно сдать ПЭТ-бутылки, пластик, крышки и металл', 59.798724, 30.399295,
        'subtitle2', 'email2@email.email', '79998881112', '{}'::jsonb),
       ('69f97365-1256-4a1d-bb04-0550849640e5', 'Эко-центр Собиратор',
        'Один из самых крупных пунктов сбора вторсырья в Санкт-Петербурге. Принимает почти всё - от пластика до бытовой техники.', 59.893890,
        30.334725, 'subtitle3', 'email3@email.email', '79998881113', '{
         "workingHours": [
           {
             "from": "10:10:10",
             "to": "12:10:10",
             "dayOfWeek": 1
           }
         ]
       }'::jsonb);
