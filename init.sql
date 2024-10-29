CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(100),
                       email VARCHAR(100) UNIQUE,
                       money_amount INTEGER
);

INSERT INTO users (name, email, money_amount) VALUES
                                                  ('Alice', 'alice@example.com', 1000),
                                                  ('Bob', 'bob@example.com', 500);
