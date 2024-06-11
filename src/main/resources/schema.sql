CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--DROP TABLE Invoice;

CREATE TABLE IF NOT EXISTS Invoice (
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    invoice_number varchar(20) NOT NULL,
    issued_on TIMESTAMP NOT NULL,
    paid_on TIMESTAMP DEFAULT NULL,
    status varchar(20) NOT NULL,
    amount INTEGER NOT NULL,

    PRIMARY KEY(id)
);