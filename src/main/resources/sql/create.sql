CREATE TABLE IF NOT EXISTS authors (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(32),
    last_name VARCHAR(32)
);

CREATE TABLE IF NOT EXISTS genres (
    id SERIAL PRIMARY KEY,
    name VARCHAR(32)
);

CREATE TABLE IF NOT EXISTS publishers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(32)
);

CREATE TABLE IF NOT EXISTS books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    author_id INTEGER REFERENCES authors (id),
    publication_date DATE,
    price DECIMAL,
    image VARCHAR(255),
    isbn VARCHAR(255),
    publisher_id INTEGER REFERENCES publishers (id),
    genre_id INTEGER REFERENCES genres (id),
    description VARCHAR(2000)
);

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(32),
    password VARCHAR(255),
    admin BOOLEAN DEFAULT FALSE,
    role VARCHAR DEFAULT 'ROLE_USER'
);

CREATE TABLE IF NOT EXISTS users_books_wishlist (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users (id),
    book_id INTEGER REFERENCES books (id)
);