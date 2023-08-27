INSERT INTO "category" (uuid, name, description, parent_id)
VALUES ('1bfe5aac-a514-462d-90e7-7330ee446a65', 'Пластик', '', NULL),
       ('600a357e-0440-4d6b-8ed0-47427ca9aa4b', 'Стекло', '', NULL),
       ('1c090d68-338f-44ac-a6d1-80872a740677', 'Металл', '', NULL),
       ('abbf6777-1eef-430b-9393-0c327f9ef231', 'Бумага', '', NULL),
       ('f7bdbf8b-b5c8-4ed3-8816-b486e83a4883', 'Разное', '', NULL);

INSERT INTO "category" (uuid, name, description, parent_id)
VALUES ('123f260d-60e0-456b-97f5-a15fe2551319', 'Твёрдый', '', (select id from "category" where uuid = '1bfe5aac-a514-462d-90e7-7330ee446a65')),
       ('c5b50734-06fc-4296-a0c1-54e4776ff0c0', 'Мягкий', '', (select id from "category" where uuid = '1bfe5aac-a514-462d-90e7-7330ee446a65'));
