CREATE TABLE IF NOT EXISTS drivers (
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    license_number VARCHAR(50) NOT NULL UNIQUE,
    active BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS orders (
    id UUID PRIMARY KEY,
    status VARCHAR(20) NOT NULL,
    origin VARCHAR(250) NOT NULL,
    destination VARCHAR(250) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS assignments (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL REFERENCES orders(id),
    driver_id UUID NOT NULL REFERENCES drivers(id),
    created_at TIMESTAMP,
    ended_at TIMESTAMP,
    pdf_path VARCHAR(500) NOT NULL,
    image_path VARCHAR(500) NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS one_assignment_active_order
ON assignments(order_id) WHERE ended_at IS NULL;

CREATE TABLE IF NOT EXISTS users(
    id UUID PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(150) NOT NULL
);
