CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO users (id, username, password)
VALUES ('11111111-1111-1111-1111-111111111111', 'admin', crypt('admin123', gen_salt('bf', 10)));