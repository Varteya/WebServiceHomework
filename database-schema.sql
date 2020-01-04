CREATE TABLE users
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255),
    surname VARCHAR(225),
    email   VARCHAR(255),
    is_company BOOLEAN

);

CREATE TABLE advertisement (
    id      SERIAL PRIMARY KEY,
    header  VARCHAR(255),
    body    TEXT,
    category VARCHAR(255),
    phone   VARCHAR(255),
    date    DATE,
    author_id INTEGER REFERENCES users(id)
);