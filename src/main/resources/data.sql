INSERT INTO "product" ("product_name", "product_price") VALUES ('Laptop', 14800);
INSERT INTO "product" ("product_name", "product_price") VALUES ('Mouse', 100);
INSERT INTO "product" ("product_name", "product_price") VALUES ('Keyboard', 1850.90);
INSERT INTO "product" ("product_name", "product_price") VALUES ('Headphone', 1785.50);
INSERT INTO "product" ("product_name", "product_price") VALUES ('Camera', 49.99);

INSERT INTO "customer" ("customer_email") VALUES ('john.doe@example.com');

INSERT INTO "basket" ("customer_id") VALUES (1);

INSERT INTO "cart_product" ("product_id", "cart_quantity", "cart_price", "basket_id")
VALUES (1, 2, 14800, 1);

INSERT INTO "cart_product" ("product_id", "cart_quantity", "cart_price", "basket_id")
VALUES (2, 3, 100, 1);

INSERT INTO "cart_product" ("product_id", "cart_quantity", "cart_price", "basket_id")
VALUES (3, 1, 1850.90, 1);
