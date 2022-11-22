MERGE INTO genre (id, genre_name) VALUES (1, 'Комедия');
MERGE INTO genre (id, genre_name) VALUES (2, 'Драма');
MERGE INTO genre (id, genre_name) VALUES (3, 'Мультфильм');
MERGE INTO genre (id, genre_name) VALUES (4, 'Триллер');
MERGE INTO genre (id, genre_name) VALUES (5, 'Документальный');
MERGE INTO genre (id, genre_name) VALUES (6, 'Боевик');

MERGE INTO MPA (id, rating) VALUES ( 1, 'G' );
MERGE INTO MPA (id, rating) VALUES ( 2, 'PG' );
MERGE INTO MPA (id, rating) VALUES ( 3, 'PG-13' );
MERGE INTO MPA (id, rating) VALUES ( 4, 'R' );
MERGE INTO MPA (id, rating) VALUES ( 5, 'NC-17' );