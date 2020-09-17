BEGIN;

DROP TABLE IF EXISTS products CASCADE;
CREATE TABLE products
(
    id    bigserial     NOT NULL,
    title varchar(255)  NOT NULL,
    price numeric(6, 2) NOT NULL,
    CONSTRAINT products_pk PRIMARY KEY (id)
);
INSERT INTO products (title, price)
VALUES ('milk', 79.90),
       ('bread', 24.90),
       ('butter', 220.00),
       ('cheese', 350.55),
       ('coca-cola', 69.90),
       ('beer', 75.90);


DROP TABLE IF EXISTS payers CASCADE;
CREATE TABLE payers
(
    id     bigserial    NOT NULL,
    "name" varchar(255) NOT NULL,
    CONSTRAINT payers_pk PRIMARY KEY (id)
);

INSERT INTO payers (name)
VALUES ('Alexander'),
       ('Bob'),
       ('John');


DROP TABLE IF EXISTS payers_products CASCADE;
CREATE TABLE payers_products
(
    payer_id   int8 NOT NULL,
    product_id int8 NOT NULL,
    CONSTRAINT payers_prod_payer_fk FOREIGN KEY (payer_id) REFERENCES payers (id),
    CONSTRAINT payers_products_fk FOREIGN KEY (product_id) REFERENCES products (id)
);

INSERT INTO payers_products (payer_id, product_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (1, 2),
       (2, 3),
       (3, 4),
       (2, 5);

COMMIT;