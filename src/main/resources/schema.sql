CREATE TABLE book (
    id SERIAL PRIMARY KEY,
    isbn VARCHAR(13),
    title VARCHAR(500),
    authors VARCHAR(500),
    read BOOLEAN,
    owned BOOLEAN,
    date_read TIMESTAMP DEFAULT NULL,
    star_rating DOUBLE DEFAULT NULL
);

