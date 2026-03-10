INSERT INTO book (title, author, isbn) VALUES ('The Hobbit', 'J.R.R. Tolkien', '978-0261102217');
INSERT INTO book (title, author, isbn) VALUES ('1984', 'George Orwell', '978-0451524935');
INSERT INTO book (title, author, isbn) VALUES ('The Great Gatsby', 'F. Scott Fitzgerald', '978-0743273565');

-- The hash below is the BCrypt encryption for the word 'password'
-- It was generated using BCrypt with a strength of 10
INSERT INTO users (username, password, enabled)
VALUES ('admin', '$2a$10$yIgkZHQUlX2En18XVvLeTeYa125IqenCMDjMOkSMe46xNOoy/1djm', true)
ON CONFLICT (username) DO NOTHING;

INSERT INTO authorities (username, authority)
VALUES ('admin', 'ROLE_ADMIN')
ON CONFLICT (username, authority) DO NOTHING;

-- Adding a standard user with the same password ('password')
INSERT INTO users (username, password, enabled)
VALUES ('user', '$2a$10$yIgkZHQUlX2En18XVvLeTeYa125IqenCMDjMOkSMe46xNOoy/1djm', true)
ON CONFLICT (username) DO NOTHING;

INSERT INTO authorities (username, authority)
VALUES ('user', 'ROLE_USER')
ON CONFLICT (username, authority) DO NOTHING;