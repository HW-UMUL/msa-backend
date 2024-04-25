INSERT INTO role(role) VALUES('ROLE_USER');
INSERT INTO role(role) VALUES('ROLE_MODERATOR');
INSERT INTO role(role) VALUES('ROLE_ADMIN');

INSERT INTO user(username, email, password) VALUES('qwerafsi23djsaf', '@fasdfb8099asdif.casd', 'asdlfiba5sdk2455lfas143dfa');

INSERT INTO user (id, username, email, password) SELECT id + 1, CONCAT('username_', id + 1) AS username, CONCAT('email_', id + 1, '@example.com') AS email, CONCAT('password_', id + 1) AS password FROM(SELECT seq AS id FROM seq_1_to_4999) AS id_generator;