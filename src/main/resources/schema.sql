CREATE TABLE book (
    id SERIAL PRIMARY KEY,
    isbn VARCHAR(255),
    title VARCHAR(255),
    authors VARCHAR(255),
    read BOOLEAN,
    owned BOOLEAN,
    date_read TIMESTAMP DEFAULT NULL,
    star_rating DOUBLE DEFAULT NULL
);

