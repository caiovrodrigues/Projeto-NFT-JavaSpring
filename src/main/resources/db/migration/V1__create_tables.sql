CREATE TABLE IF NOT EXISTS `tb_usuario` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `email` varchar(255),
    `username` varchar(20),
    `password` varchar(50)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `tb_nft` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(30) NOT NULL,
    `description` varchar(255),
    `price` float(8,2) NOT NULL,
    `qtd` int,
    `img_url` varchar(255),
    `data_criacao` TIMESTAMP NOT NULL,
    `user_id` int NOT NULL,
    FOREIGN KEY (user_id) REFERENCES tb_usuario(id)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `tb_comment` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `mensagem` varchar(255) NOT NULL,
    `data_criacao` TIMESTAMP NOT NULL,
    `user_id` int NOT NULL,
    `nft_id` int NOT NULL,
    FOREIGN KEY (user_id) REFERENCES tb_usuario(id),
    FOREIGN KEY (nft_id) REFERENCES tb_nft(id)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;