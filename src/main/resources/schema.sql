CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--DROP TABLE IF EXISTS item;
--DROP TABLE IF EXISTS invoice;
--DROP TABLE IF EXISTS customer;

CREATE TABLE IF NOT EXISTS customer (
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    ICO varchar(30),
    name varchar(100) NOT NULL,
    street_name varchar(100) NOT NULL,
    city varchar(50) NOT NULL,
    zip_code INTEGER NOT NULL,

    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS invoice (
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    invoice_number varchar(20) NOT NULL,
    issued_on TIMESTAMP NOT NULL,
    expected_on TIMESTAMP NOT NULL,
    paid_on TIMESTAMP DEFAULT NULL,
    status varchar(20) NOT NULL,
    amount INTEGER NOT NULL,
    customer_id uuid NOT NULL,

    PRIMARY KEY(id),
    CONSTRAINT FK_InvoiceCustomer
        FOREIGN KEY(customer_id)
            REFERENCES customer(id)
);

CREATE TABLE IF NOT EXISTS item (
    id SERIAL PRIMARY KEY,
    name varchar(150) NOT NULL,
    value INTEGER NOT NULL,
    amount INTEGER NOT NULL,
    invoice_id uuid,

    CONSTRAINT FK_ItemInvoice
        FOREIGN KEY(invoice_id)
            REFERENCES Invoice(id)
);