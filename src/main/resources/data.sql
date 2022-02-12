INSERT INTO user(id, username, first_name, last_name, password)
VALUES
       (1, 'admin', 'Mariola', 'Mariolkowa', '{noop}pass'),
       (2, 'Alinka', 'Alina', 'Alinkowa', '{noop}a'),
       (3, 'user', 'Grzegorz', 'Grzegosław', '{noop}pass'),
       (4, 'Filipek', 'Filip', 'Filipkowiak', '{noop}a'),
       (5, 'Marzenka', 'Marzena', 'Marzeniewska', '{noop}a');

INSERT INTO user_role(user_id, role)
VALUES
       (1, 'ROLE_USER'),
       (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER'),
       (2, 'ROLE_ADMIN'),
       (3, 'ROLE_USER'),
       (4, 'ROLE_USER'),
       (5, 'ROLE_USER');