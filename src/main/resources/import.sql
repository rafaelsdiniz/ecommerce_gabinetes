insert into categoria (descricao, nome) values
('Gabinetes para jogos de alta performance', 'Gamer'),
('Gabinetes para jogos com RGB', 'RGB Gamer'),
('Gabinetes compactos para gamers', 'Compact Gamer'),
('Gabinetes de grande porte para PCs poderosos', 'Power Gamer'),
('Gabinetes com ótimo fluxo de ar', 'AirFlow Gamer'),
('Gabinetes para overclocking', 'Overclock Gamer'),
('Gabinetes com design futurista', 'Futuristic Gamer'),
('Gabinetes de vidro temperado', 'Glass Gamer'),
('Gabinetes silenciosos para jogos', 'Silent Gamer'),
('Gabinetes com espaço para múltiplas placas de vídeo', 'Multi-GPU Gamer');

insert into cliente (telefone, email) values
('11987654321', 'rafael.diniz@email.com'),
('11923456789', 'lucas.silva@email.com'),
('11987654322', 'ana.oliveira@email.com'),
('11923456780', 'maria.pereira@email.com'),
('11934567890', 'joão.santos@email.com'),
('11945678901', 'beatriz.almeida@email.com'),
('11956789012', 'pedro.ferreira@email.com'),
('11967890123', 'carla.mendes@email.com'),
('11978901234', 'felipe.araujo@email.com'),
('11989012345', 'fernanda.barros@email.com'),
('11345678901', 'empresa1@domain.com'),
('11356789012', 'empresa2@domain.com'),
('11367890123', 'empresa3@domain.com'),
('11378901234', 'empresa4@domain.com'),
('11389012345', 'empresa5@domain.com'),
('11390123456', 'empresa6@domain.com'),
('11301234567', 'empresa7@domain.com'),
('11312345678', 'empresa8@domain.com'),
('11323456789', 'empresa9@domain.com'),
('11334567890', 'empresa10@domain.com');

insert into clientefisico (id, idade, cpf, nome, sobrenome) values
(1, 29, '04548621128', 'rafael', 'diniz'),
(2, 35, '12345678901', 'lucas', 'silva'),
(3, 22, '98765432100', 'ana', 'oliveira'),
(4, 41, '23456789012', 'maria', 'pereira'),
(5, 30, '34567890123', 'joão', 'santos'),
(6, 28, '45678901234', 'beatriz', 'almeida'),
(7, 33, '56789012345', 'pedro', 'ferreira'),
(8, 27, '67890123456', 'carla', 'mendes'),
(9, 38, '78901234567', 'felipe', 'araujo'),
(10, 26, '89012345678', 'fernanda', 'barros');



insert into clientejuridico (id, cnpj, nomefantasia, razaosocial) values
(11, '12345678000123', 'Tech Solutions', 'Tech Solutions LTDA'),
(12, '23456789000123', 'Construtora Silva', 'Construtora Silva S.A'),
(13, '34567890000123', 'Loja Digital', 'Loja Digital Comércio Ltda'),
(14, '45678901000123', 'Super Mercado ABC', 'Super Mercado ABC Ltda'),
(15, '56789012000123', 'Fábrica de Brinquedos', 'Fábrica de Brinquedos S.A'),
(16, '67890123000123', 'Restaurante Doce Sabor', 'Restaurante Doce Sabor Ltda'),
(17, '78901234000123', 'Livraria Nova Era', 'Livraria Nova Era Ltda'),
(18, '89012345000123', 'Academia Fit', 'Academia Fit LTDA'),
(19, '90123456000123', 'Consultoria Max', 'Consultoria Max S.A'),
(20, '01234567000123', 'Transportes Rápidos', 'Transportes Rápidos LTDA');


insert into gabinete (altura, alturamaxcooler, largura, peso, preco, qtdrgb, tamanhomaxgpu, usb, usbc, cor, formato, marca, nomeexibicao) values
(45, 160, 220, 7.5, 499.99, 4, 350, 4, 2, 'Preto', 'ATX', 'Cooler Master', 'MasterBox Q300L'),
(42, 150, 200, 6.2, 399.99, 3, 320, 3, 1, 'Branco', 'Micro-ATX', 'NZXT', 'H510'),
(46, 180, 230, 8.0, 599.99, 6, 370, 4, 2, 'Cinza', 'Full Tower', 'Corsair', 'iCUE 4000X RGB'),
(50, 160, 250, 9.5, 699.99, 5, 380, 6, 2, 'Preto Fosco', 'ATX', 'Phanteks', 'P400A'),
(43, 140, 210, 7.0, 299.99, 2, 300, 3, 1, 'Azul', 'Mid Tower', 'DeepCool', 'Matrexx 30'),
(48, 170, 240, 8.2, 649.99, 5, 360, 4, 2, 'Preto', 'ATX', 'Fractal Design', 'Meshify C'),
(44, 150, 220, 7.6, 479.99, 4, 340, 4, 1, 'Branco', 'Mid Tower', 'MSI', 'MAG Forge 100R'),
(49, 160, 260, 10.0, 799.99, 6, 400, 6, 3, 'Preto', 'Full Tower', 'Thermaltake', 'View 71'),
(47, 160, 235, 7.9, 399.99, 3, 310, 4, 1, 'Preto Fosco', 'Mid Tower', 'Gigabyte', 'AORUS C300G'),
(41, 130, 210, 6.5, 349.99, 2, 320, 3, 2, 'Preto', 'Micro-ATX', 'Antec', 'VSK-3000');

insert into pedido (valortotal, cliente_id, datapedido, bairro, cep, cidade, estado, logradouro, numero, status) values
(350.50, 1, '2025-05-08 10:00:00', 'Centro', '12345000', 'São Paulo', 'SP', 'Rua das Flores', 120, 'PROCESSANDO'),
(500.00, 2, '2025-05-07 11:15:00', 'Jardim das Palmeiras', '23456000', 'Rio de Janeiro', 'RJ', 'Avenida Brasil', 56, 'ENVIADO'),
(250.75, 3, '2025-05-06 14:30:00', 'Vila Progresso', '34567000', 'Belo Horizonte', 'MG', 'Rua do Comércio', 78, 'ENTREGUE'),
(120.90, 4, '2025-05-05 09:45:00', 'Ponte Nova', '45678000', 'Salvador', 'BA', 'Travessa dos Ventos', 92, 'CANCELADO'),
(90.25, 5, '2025-05-04 16:00:00', 'Praia Grande', '56789000', 'Fortaleza', 'CE', 'Rua das Águas', 15, 'PROCESSANDO'),
(600.00, 6, '2025-05-03 18:00:00', 'Vila Nova', '67890100', 'Curitiba', 'PR', 'Avenida Paraná', 88, 'ENVIADO'),
(450.60, 7, '2025-05-02 13:00:00', 'Alto da Serra', '78901200', 'Porto Alegre', 'RS', 'Rua das Palmeiras', 110, 'ENTREGUE'),
(310.45, 8, '2025-05-01 12:00:00', 'Setor Comercial', '89012300', 'Manaus', 'AM', 'Rua Amazonas', 45, 'CANCELADO'),
(200.20, 9, '2025-04-30 15:00:00', 'Vila Flores', '90123400', 'Recife', 'PE', 'Rua do Sol', 34, 'PROCESSANDO'),
(420.75, 10, '2025-04-29 17:30:00', 'Centro Histórico', '01234500', 'Florianópolis', 'SC', 'Avenida Central', 50, 'ENVIADO');

insert into itempedido (precototal, precounitario, quantidade, gabinete_id, pedido_id) values
(499.99, 499.99, 1, 1, 1),
(799.98, 399.99, 2, 2, 2),
(649.99, 649.99, 1, 3, 3),
(999.97, 499.99, 2, 4, 4),
(479.99, 479.99, 1, 5, 5),
(899.98, 449.99, 2, 6, 6),
(899.97, 599.99, 1, 7, 7),
(659.98, 329.99, 2, 8, 8),
(749.97, 249.99, 3, 9, 9),
(549.99, 549.99, 1, 10, 10);

-- Inserts para a tabela pedido
insert into pedido (valortotal, cliente_id, datapedido, bairro, cep, cidade, estado, logradouro, numero, status) values
(550.60, 11, '2025-05-09 10:00:00', 'Centro', '12346000', 'São Paulo', 'SP', 'Rua das Flores', 130, 'PROCESSANDO'),
(650.75, 12, '2025-05-10 11:15:00', 'Jardim das Palmeiras', '23457000', 'Rio de Janeiro', 'RJ', 'Avenida Brasil', 66, 'ENVIADO'),
(270.80, 13, '2025-05-11 14:30:00', 'Vila Progresso', '34568000', 'Belo Horizonte', 'MG', 'Rua do Comércio', 88, 'ENTREGUE'),
(150.95, 14, '2025-05-12 09:45:00', 'Ponte Nova', '45679000', 'Salvador', 'BA', 'Travessa dos Ventos', 102, 'CANCELADO'),
(110.50, 15, '2025-05-13 16:00:00', 'Praia Grande', '56790000', 'Fortaleza', 'CE', 'Rua das Águas', 18, 'PROCESSANDO'),
(700.25, 16, '2025-05-14 18:00:00', 'Vila Nova', '67891200', 'Curitiba', 'PR', 'Avenida Paraná', 99, 'ENVIADO'),
(470.80, 17, '2025-05-15 13:00:00', 'Alto da Serra', '78902400', 'Porto Alegre', 'RS', 'Rua das Palmeiras', 120, 'ENTREGUE'),
(320.50, 18, '2025-05-16 12:00:00', 'Setor Comercial', '89013400', 'Manaus', 'AM', 'Rua Amazonas', 55, 'CANCELADO'),
(210.75, 19, '2025-05-17 15:00:00', 'Vila Flores', '90124500', 'Recife', 'PE', 'Rua do Sol', 40, 'PROCESSANDO'),
(440.85, 20, '2025-05-18 17:30:00', 'Centro Histórico', '01234600', 'Florianópolis', 'SC', 'Avenida Central', 60, 'ENVIADO');

-- Inserts para a tabela itempedido
insert into itempedido (precototal, precounitario, quantidade, gabinete_id, pedido_id) values
(499.99, 499.99, 1, 1, 11),
(799.98, 399.99, 2, 2, 12),
(649.99, 649.99, 1, 3, 13),
(999.97, 499.99, 2, 4, 14),
(479.99, 479.99, 1, 5, 15),
(899.98, 449.99, 2, 6, 16),
(899.97, 599.99, 1, 7, 17),
(659.98, 329.99, 2, 8, 18),
(749.97, 249.99, 3, 9, 19),
(549.99, 549.99, 1, 10, 20);


insert into marca (nome) values
('Corrsair'),
('Coorler Master'),
('NZXrT'),
('Pharnteks'),
('MSrI'),
('Therrmaltake'),
('Razeeeer'),
('Girgabyte'),
('ASrUS'),
('Liran Li');

insert into pagamento (valor, data, pedido_id, forma_pagamento, status_pagamento) values
(150.75, '2025-05-08', 1, 'CARTAO_CREDITO', 'APROVADO'),
(200.00, '2025-05-07', 2, 'PIX', 'PENDENTE'),
(350.50, '2025-05-06', 3, 'BOLETO', 'APROVADO'),
(120.90, '2025-05-05', 4, 'CARTAO_DEBITO', 'RECUSADO'),
(90.25, '2025-05-04', 5, 'CARTAO_CREDITO', 'APROVADO'),
(500.00, '2025-05-03', 6, 'PIX', 'PENDENTE'),
(75.80, '2025-05-02', 7, 'BOLETO', 'RECUSADO'),
(180.60, '2025-05-01', 8, 'CARTAO_DEBITO', 'APROVADO'),
(250.45, '2025-04-30', 9, 'CARTAO_CREDITO', 'PENDENTE'),
(320.00, '2025-04-29', 10, 'PIX', 'APROVADO');


 