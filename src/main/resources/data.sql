INSERT INTO tb_usuarios(username, role) VALUES ('caio', 'USER');

INSERT INTO tb_nft(name, created_at, update_at, url_minio, description, price, qtd, user_id)
VALUES ('nome nft', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'https://www.artalistic.com/en/media/catalog/product/cache/2/image/1800x/040ec09b1e35df139433887a97daa66f/e/l/el_ar.0424_002.jpg', 'Descrição qualquer.', 59.95, 20, 1);