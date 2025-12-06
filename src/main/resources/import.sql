-- ============================================
-- SCRIPT DE IMPORTAÇÃO COMPLETO - E-commerce de Gabinetes
-- ============================================

-- ============================================
-- 1. MARCAS
-- ============================================
INSERT INTO marca (nome) VALUES
('Corsair'),
('Cooler Master'),
('NZXT'),
('Phanteks'),
('MSI'),
('Thermaltake'),
('Razer'),
('Gigabyte'),
('ASUS'),
('Lian Li');

-- ============================================
-- 2. FORNECEDORES
-- ============================================
INSERT INTO fornecedor (nome, email, telefone, cnpj) VALUES
('Tech Distribuidora LTDA', 'contato@techdist.com.br', '11987654321', '12345678000190'),
('Mega Parts Importadora', 'vendas@megaparts.com.br', '11923456789', '23456789000191'),
('PC Components Brasil', 'comercial@pccomp.com.br', '11934567890', '34567890000192'),
('Hardware Solutions', 'info@hwsolutions.com.br', '11945678901', '45678901000193'),
('Digital Store Distribuidora', 'suporte@digitalstore.com.br', '11956789012', '56789012000194');

-- ============================================
-- 3. MODELOS
-- ============================================
INSERT INTO modelo (nomeModelo, marca_id) VALUES
('MasterBox Q300L', 2),
('H510', 3),
('iCUE 4000X RGB', 1),
('P400A', 4),
('Matrexx 30', 2),
('Meshify C', 1),
('MAG Forge 100R', 5),
('View 71', 6),
('AORUS C300G', 8),
('VSK-3000', 2);

-- ============================================
-- 4. CATEGORIAS
-- ============================================
INSERT INTO categoria (nome, descricao) VALUES
('Gamer', 'Gabinetes para jogos de alta performance'),
('RGB Gamer', 'Gabinetes para jogos com iluminação RGB'),
('Compact Gamer', 'Gabinetes compactos para gamers'),
('Power Gamer', 'Gabinetes de grande porte para PCs poderosos'),
('AirFlow Gamer', 'Gabinetes com ótimo fluxo de ar'),
('Overclock Gamer', 'Gabinetes para overclocking'),
('Futuristic Gamer', 'Gabinetes com design futurista'),
('Glass Gamer', 'Gabinetes de vidro temperado'),
('Silent Gamer', 'Gabinetes silenciosos para jogos'),
('Multi-GPU Gamer', 'Gabinetes com espaço para múltiplas placas de vídeo');

-- ============================================
-- 5. GABINETES (SEM IMAGENS POR ENQUANTO)
-- ============================================
INSERT INTO gabinete (nomeexibicao, marca, preco, cor, formato, altura, largura, peso, tamanhomaxgpu, alturamaxcooler, qtdrgb, usb, usbc, descricao) VALUES
('MasterBox Q300L', 'Cooler Master', 499.99, 'Preto', 'ATX', 45, 220, 7.5, 350, 160, 4, 4, 2, 'Gabinete compacto com excelente ventilação e design moderno'),
('H510', 'NZXT', 399.99, 'Branco', 'Micro‑ATX', 42, 200, 6.2, 320, 150, 3, 3, 1, 'Design minimalista com painel de vidro temperado'),
('iCUE 4000X RGB', 'Corsair', 599.99, 'Cinza', 'Full Tower', 46, 230, 8.0, 370, 180, 6, 4, 2, 'Gabinete premium com iluminação RGB integrada'),
('P400A', 'Phanteks', 699.99, 'Preto Fosco', 'ATX', 50, 250, 9.5, 380, 160, 5, 6, 2, 'Gabinete com foco em airflow e desempenho'),
('Matrexx 30', 'Cooler Master', 299.99, 'Azul', 'Mid Tower', 43, 210, 7.0, 300, 140, 2, 3, 1, 'Gabinete econômico com bom custo‑benefício'),
('Meshify C', 'Corsair', 649.99, 'Preto', 'ATX', 48, 240, 8.2, 360, 170, 5, 4, 2, 'Design moderno com malha frontal para melhor ventilação'),
('MAG Forge 100R', 'MSI', 479.99, 'Branco', 'Mid Tower', 44, 220, 7.6, 340, 150, 4, 4, 1, 'Gabinete gamer com RGB e design agressivo'),
('View 71', 'Thermaltake', 799.99, 'Preto', 'Full Tower', 49, 260, 10.0, 400, 160, 6, 6, 3, 'Gabinete full tower premium com painéis de vidro'),
('AORUS C300G', 'Gigabyte', 399.99, 'Preto Fosco', 'Mid Tower', 47, 235, 7.9, 310, 160, 3, 4, 1, 'Gabinete gamer com design agressivo e RGB'),
('VSK‑3000', 'Cooler Master', 349.99, 'Preto', 'Micro‑ATX', 41, 210, 6.5, 320, 130, 2, 3, 2, 'Gabinete básico para uso geral e escritório'),
('O11 Dynamic', 'Lian Li', 899.99, 'Prata', 'ATX', 52, 270, 9.8, 390, 185, 6, 5, 3, 'Gabinete premium de alumínio com vidro temperado'),
('Define 7', 'Fractal Design', 799.99, 'Branco', 'ATX', 50, 235, 9.0, 370, 170, 3, 4, 2, 'Gabinete silencioso com ótimo gerenciamento de cabos'),
('NR200P', 'Cooler Master', 549.99, 'Grafite', 'Mini‑ITX', 38, 210, 6.0, 330, 140, 3, 3, 1, 'Gabinete super compacto para SFF com excelente compatibilidade'),
('H710i', 'NZXT', 679.99, 'Preto', 'ATX', 50, 245, 9.3, 380, 175, 4, 5, 2, 'Gabinete de alto desempenho da linha H'),
('Enthoo Evolv X', 'Phanteks', 849.99, 'Titânio', 'Full Tower', 53, 260, 10.5, 400, 185, 5, 6, 3, 'Gabinete high‑end com espaço para dual system'),
('Dark Base 900', 'be quiet!', 749.99, 'Preto', 'ATX', 51, 260, 9.6, 370, 170, 3, 4, 2, 'Gabinete premium focado em silêncio e qualidade de construção'),
('Obsidian 1000D', 'Corsair', 1299.99, 'Preto', 'Super Tower', 56, 300, 13.0, 420, 190, 8, 6, 3, 'Gabinete gigante para builds ultra‑top'),
('Meshify 2', 'Fractal Design', 699.99, 'Preto', 'Mid Tower', 47, 240, 8.5, 370, 175, 4, 4, 2, 'Gabinete com ótimo airflow e construção robusta'),
('LanCool II Mesh', 'Lian Li', 499.99, 'Branco', 'Mid Tower', 46, 225, 8.0, 350, 165, 4, 5, 2, 'Gabinete com painel frontal em malha e design elegante'),
('H500 Flow', 'NZXT', 459.99, 'Vermelho', 'Mid Tower', 44, 215, 7.2, 340, 155, 4, 4, 1, 'Gabinete com ótimo fluxo de ar e estilo gamer'),
('Dark Phantom 920', 'Cooler Master', 599.99, 'Preto/Prata', 'ATX', 49, 230, 9.2, 360, 165, 5, 5, 2, 'Gabinete de médio‑alto padrão com design robusto'),
('O11 EVO XL', 'Lian Li', 999.99, 'Branco', 'Full Tower', 55, 270, 11.0, 410, 190, 6, 6, 3, 'Gabinete flagship com vidro panorâmico e suporte E‑ATX'),
('Pure Base 500DX', 'be quiet!', 479.99, 'Preto', 'Mid Tower', 45, 215, 8.1, 340, 160, 3, 4, 1, 'Gabinete com foco em silêncio e ventilação premium'),
('Mag Pylon 010', 'MSI', 429.99, 'Preto/Vermelho', 'Mid Tower', 43, 220, 7.4, 330, 150, 4, 4, 1, 'Gabinete gamer compacto com painel de vidro temperado'),
('MasterBox TD500 Mesh', 'Cooler Master', 449.99, 'Preto', 'Mid Tower', 42, 225, 7.1, 340, 150, 3, 3, 1, 'Gabinete focado em fluxo de ar e design acessível'),
('Fara R5 TG', 'SilverStone', 399.99, 'Preto/Cinza', 'Mid Tower', 44, 215, 7.0, 330, 145, 2, 3, 1, 'Gabinete elegante com painel lateral de vidro'),
('AGM X5', 'Gigabyte', 529.99, 'Preto', 'ATX', 48, 235, 8.7, 350, 170, 4, 5, 2, 'Gabinete versátil com boas conexões e RGB discreto'),
('C3 Tempered Glass', 'InWin', 519.99, 'Branco', 'Mid Tower', 46, 225, 8.2, 340, 155, 3, 4, 2, 'Gabinete premium com vidro temperado e acabamento refinado'),
('Dynamic X2', 'Thermaltake', 599.99, 'Preto', 'Mid Tower', 47, 230, 8.9, 360, 165, 5, 5, 2, 'Gabinete gamer com recursos avançados e RGB integrado'),
('Enthoo Luxe 2', 'Phanteks', 999.99, 'Preto/Vermelho', 'Full Tower', 54, 260, 11.5, 400, 190, 6, 6, 3, 'Gabinete top‑de‑linha para entusiastas e water‑cooling');

-- ============================================
-- 6. INFORMAÇÕES ADICIONAIS
-- ============================================
INSERT INTO informacaoadicional (titulo, descricao, gabinete_id) VALUES
('garantia', '2 anos', 1),
('material', 'Aço e Vidro Temperado', 2),
('compatibilidade_placa', 'ATX, Micro-ATX, Mini-ITX', 3),
('slots_expansao', '7', 4),
('baias_35', '2', 5),
('baias_25', '4', 6),
('peso_maximo_suportado', '25kg', 7),
('certificacao', 'CE, FCC, RoHS', 8);

-- ============================================
-- 7. RELACIONAMENTO GABINETE-CATEGORIA
-- ============================================
INSERT INTO gabinete_categoria (gabinete_id, categoria_id) VALUES
(1, 1), (1, 5), (1, 8),
(2, 1), (2, 3), (2, 8),
(3, 1), (3, 2), (3, 4), (3, 8),
(4, 1), (4, 5), (4, 6),
(5, 1), (5, 3),
(6, 1), (6, 5), (6, 8),
(7, 1), (7, 2), (7, 7),
(8, 1), (8, 4), (8, 8), (8, 10),
(9, 1), (9, 2), (9, 7),
(10, 3), (10, 9),
(11, 1), (11, 4), (11, 8),
(12, 1), (12, 9),
(13, 1), (13, 3),
(14, 1), (14, 4),
(15, 1), (15, 4), (15, 6),
(16, 1), (16, 9),
(17, 1), (17, 4), (17, 10),
(18, 1), (18, 5),
(19, 1), (19, 5), (19, 8),
(20, 1), (20, 5),
(21, 1), (21, 4),
(22, 1), (22, 4), (22, 8),
(23, 1), (23, 9),
(24, 1), (24, 3),
(25, 1), (25, 5),
(26, 1), (26, 8),
(27, 1), (27, 2),
(28, 1), (28, 8),
(29, 1), (29, 2),
(30, 1), (30, 4), (30, 6);

-- ============================================
-- 8. ESTOQUE
-- ============================================
INSERT INTO estoque (gabinete_id, quantidade_disponivel) VALUES
(1, 10),
(2, 5),
(3, 15),
(4, 8),
(5, 12),
(6, 20),
(7, 7),
(8, 25),
(9, 18),
(10, 30),
(11, 8),
(12, 12),
(13, 6),
(14, 14),
(15, 9),
(16, 11),
(17, 4),
(18, 16),
(19, 13),
(20, 7),
(21, 10),
(22, 5),
(23, 18),
(24, 8),
(25, 15),
(26, 9),
(27, 12),
(28, 6),
(29, 14),
(30, 3);

-- ============================================
-- 9. CLIENTES COM LOGIN
-- ============================================
INSERT INTO cliente (nome, email, telefone, cpf, senha, perfil) VALUES
('Rafael Diniz', 'rafael.diniz@gmail.com', '11987654321', '04548621128', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'ADMIN'),
('Lucas Silva', 'lucas.silva@gmail.com', '11923456789', '12345678901', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'CLIENTE'),
('Ana Oliveira', 'ana.oliveira@gmail.com', '11987654322', '98765432100', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'CLIENTE'),
('Maria Pereira', 'maria.pereira@gmail.com', '11923456780', '23456789012', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'ADMIN'),
('João Santos', 'joao.santos@gmail.com', '11934567890', '34567890123', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'CLIENTE'),
('Beatriz Almeida', 'beatriz.almeida@gmail.com', '11945678901', '45678901234', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'CLIENTE'),
('Pedro Ferreira', 'pedro.ferreira@gmail.com', '11956789012', '56789012345', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'ADMIN'),
('Carla Mendes', 'carla.mendes@gmail.com', '11967890123', '67890123456', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'CLIENTE'),
('Felipe Araujo', 'felipe.araujo@gmail.com', '11978901234', '78901234567', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'CLIENTE'),
('Fernanda Barros', 'fernanda.barros@gmail.com', '11989012345', '89012345678', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'ADMIN');

-- ============================================
-- 10. ENDEREÇOS DOS CLIENTES
-- ============================================
INSERT INTO cliente_endereco (cliente_id, estado, cidade, bairro, cep, numero, complemento) VALUES
(1, 'SP', 'São Paulo', 'Centro', '01000000', '120', 'Apto 501'),
(2, 'RJ', 'Rio de Janeiro', 'Copacabana', '22000000', '56', 'Bloco B'),
(3, 'MG', 'Belo Horizonte', 'Savassi', '30000000', '78', 'Casa'),
(4, 'BA', 'Salvador', 'Barra', '40000000', '92', 'Apto 302'),
(5, 'CE', 'Fortaleza', 'Meireles', '60000000', '15', 'Cobertura'),
(6, 'PR', 'Curitiba', 'Centro', '80000000', '88', 'Sala 12'),
(7, 'RS', 'Porto Alegre', 'Moinhos de Vento', '90000000', '110', 'Apto 1001'),
(8, 'AM', 'Manaus', 'Centro', '69000000', '45', 'Loja 3'),
(9, 'PE', 'Recife', 'Boa Viagem', '51000000', '34', 'Apto 201'),
(10, 'SC', 'Florianópolis', 'Centro', '88000000', '50', 'Casa 2');

-- ============================================
-- 11. PEDIDOS
-- ============================================
INSERT INTO pedido (valortotal, cliente_id, datapedido, status) VALUES
(499.99, 1, '2025-05-08 10:00:00', 'PROCESSANDO'),
(799.98, 2, '2025-05-07 11:15:00', 'ENVIADO'),
(649.99, 3, '2025-05-06 14:30:00', 'ENTREGUE'),
(999.97, 4, '2025-05-05 09:45:00', 'CANCELADO'),
(479.99, 5, '2025-05-04 16:00:00', 'PROCESSANDO'),
(899.98, 6, '2025-05-03 18:00:00', 'ENVIADO'),
(899.97, 7, '2025-05-02 13:00:00', 'ENTREGUE'),
(659.98, 8, '2025-05-01 12:00:00', 'CANCELADO'),
(749.97, 9, '2025-04-30 15:00:00', 'PROCESSANDO'),
(549.99, 10, '2025-04-29 17:30:00', 'ENVIADO');

-- ============================================
-- 12. ITENS DOS PEDIDOS
-- ============================================
INSERT INTO itempedido (precounitario, quantidade, precototal, gabinete_id, pedido_id) VALUES
(499.99, 1, 499.99, 1, 1),
(399.99, 2, 799.98, 2, 2),
(649.99, 1, 649.99, 3, 3),
(499.99, 2, 999.98, 4, 4),
(479.99, 1, 479.99, 5, 5),
(449.99, 2, 899.98, 6, 6),
(599.99, 1, 599.99, 7, 7),
(329.99, 2, 659.98, 8, 8),
(249.99, 3, 749.97, 9, 9),
(549.99, 1, 549.99, 10, 10);

-- ============================================
-- 13. PAGAMENTOS
-- ============================================
INSERT INTO pagamento (pedido_id, forma_pagamento, status_pagamento, valor, data) VALUES
(1, 'CARTAO_CREDITO', 'APROVADO', 499.99, '2025-05-08 10:05:00'),
(2, 'PIX', 'APROVADO', 799.98, '2025-05-07 11:20:00'),
(3, 'BOLETO', 'APROVADO', 649.99, '2025-05-06 14:35:00'),
(4, 'CARTAO_DEBITO', 'RECUSADO', 999.97, '2025-05-05 09:50:00'),
(5, 'CARTAO_CREDITO', 'PENDENTE', 479.99, '2025-05-04 16:05:00'),
(6, 'PIX', 'APROVADO', 899.98, '2025-05-03 18:05:00'),
(7, 'BOLETO', 'APROVADO', 899.97, '2025-05-02 13:05:00'),
(8, 'CARTAO_DEBITO', 'RECUSADO', 659.98, '2025-05-01 12:05:00'),
(9, 'CARTAO_CREDITO', 'PENDENTE', 749.97, '2025-04-30 15:05:00'),
(10, 'PIX', 'APROVADO', 549.99, '2025-04-29 17:35:00');