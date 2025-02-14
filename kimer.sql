-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 14/02/2025 às 21:23
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `kimer`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `cliente`
--

CREATE TABLE `cliente` (
  `id` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `cpf` varchar(15) NOT NULL,
  `email` varchar(40) DEFAULT NULL,
  `telefone` varchar(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `cliente`
--

INSERT INTO `cliente` (`id`, `nome`, `cpf`, `email`, `telefone`) VALUES
(9, 'Larah Raquel', '86415935468', 'larah@gmail.com', '73982118198'),
(26, 'Ailla Almeida', '06242352540', 'ailla@gmail.com', '73981405275'),
(27, 'Lucas Eduardo', '86404849846', 'lesds@gmail.com', '73981405275'),
(48, 'Bruno Souza', '92876543210', 'bruno.souza@email.com', '11987654321'),
(49, 'Carla Mendes', '11212223334', 'carla.mendes@email.com', '21987654322'),
(50, 'Daniela Oliveira', '22223334445', 'daniela.oliveira@email.com', '31987654323'),
(51, 'Eduardo Lima', '33344435556', 'eduardo.lima@email.com', '41987654324'),
(52, 'Fernanda Costa', '44453556667', 'fernanda.costa@email.com', '51987654325'),
(53, 'Gabriel Rocha', '55566367778', 'gabriel.rocha@email.com', '61987654326'),
(54, 'Helena Martins', '66673778889', 'helena.martins@email.com', '71987654327'),
(55, 'Igor Santos', '77788893990', 'igor.santos@email.com', '81987654328'),
(56, 'Juliana Ribeiro', '88839990001', 'juliana.ribeiro@email.com', '91987654329'),
(57, 'Kleber Nunes', '99900031112', 'kleber.nunes@email.com', '12987654330'),
(58, 'Larissa Pereira', '00031112223', 'larissa.pereira@email.com', '22987654331'),
(59, 'Marcos Fernandes', '11132223334', 'marcos.fernandes@email.com', '32987654332'),
(61, 'Otávio Moreira', '33343445556', 'otavio.moreira@email.com', '52987654334'),
(62, 'Paula Xavier', '44455536667', 'paula.xavier@email.com', '62987654335'),
(63, 'Rafael Gomes', '55566637778', 'rafael.gomes@email.com', '72987654336'),
(64, 'Sabrina Duarte', '66673778889', 'sabrina.duarte@email.com', '82987654337'),
(65, 'Thiago Rezende', '77783889990', 'thiago.rezende@email.com', '92987654338'),
(66, 'Vanessa Freitas', '88893990001', 'vanessa.freitas@email.com', '13987654339'),
(67, 'William Castro', '99903001112', 'william.castro@email.com', '23987654340'),
(68, 'Antonio Nunes da Silva', '12332112332', 'antonius@gmail.com', '1561563212');

-- --------------------------------------------------------

--
-- Estrutura para tabela `estoque`
--

CREATE TABLE `estoque` (
  `id` int(11) NOT NULL,
  `peca` int(11) NOT NULL,
  `quantidade` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `estoque`
--

INSERT INTO `estoque` (`id`, `peca`, `quantidade`) VALUES
(1, 1, 50),
(2, 3, 30.5),
(3, 4, 20),
(4, 5, 15),
(5, 6, 40),
(6, 7, 22),
(7, 8, 18.5),
(8, 9, 60),
(9, 10, 33),
(10, 11, 45),
(11, 12, 55),
(12, 13, 25),
(13, 14, 37),
(14, 15, 29),
(15, 16, 48),
(16, 17, 52),
(17, 18, 31),
(18, 19, 20),
(19, 20, 27.5),
(20, 21, 44),
(21, 22, 38),
(22, 23, 19.5),
(23, 24, 50),
(24, 25, 41),
(25, 26, 36),
(26, 27, 23),
(27, 28, 29.5),
(28, 29, 47),
(29, 30, 33.5),
(30, 31, 26);

-- --------------------------------------------------------

--
-- Estrutura para tabela `marca`
--

CREATE TABLE `marca` (
  `id` int(11) NOT NULL,
  `nome` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `marca`
--

INSERT INTO `marca` (`id`, `nome`) VALUES
(1, 'Honda'),
(3, 'yamaha'),
(4, 'shineray'),
(5, 'bmw'),
(7, 'Suzuki'),
(9, 'Daywan');

-- --------------------------------------------------------

--
-- Estrutura para tabela `modelo`
--

CREATE TABLE `modelo` (
  `id` int(11) NOT NULL,
  `modelo` varchar(45) NOT NULL,
  `cilindrada` varchar(11) NOT NULL,
  `marca_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `modelo`
--

INSERT INTO `modelo` (`id`, `modelo`, `cilindrada`, `marca_id`) VALUES
(2, 'Fan', '160', 1),
(3, 'kawasaki', '500', 7),
(4, 'Intruder', '150', 3),
(7, 'Novo', '190', 4);

-- --------------------------------------------------------

--
-- Estrutura para tabela `moto_cliente`
--

CREATE TABLE `moto_cliente` (
  `id` int(11) NOT NULL,
  `placa` varchar(8) NOT NULL,
  `ano` varchar(4) NOT NULL,
  `modelo_id` int(11) NOT NULL,
  `cliente_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `moto_cliente`
--

INSERT INTO `moto_cliente` (`id`, `placa`, `ano`, `modelo_id`, `cliente_id`) VALUES
(2, 'rco2h12', '2020', 2, 27),
(6, 'PKM3299', '2019', 3, 62),
(7, 'PHM2312', '2312', 2, 27),
(11, 'KJh0k00', '2025', 3, 9),
(12, 'asp3123', '2013', 3, 26);

-- --------------------------------------------------------

--
-- Estrutura para tabela `peca`
--

CREATE TABLE `peca` (
  `id` int(11) NOT NULL,
  `nome` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `peca`
--

INSERT INTO `peca` (`id`, `nome`) VALUES
(1, 'Kit Transmissão'),
(3, 'Pastilha de Freio'),
(4, 'Disco de Freio'),
(5, 'Corrente de Transmissão'),
(6, 'Pneu Dianteiro'),
(7, 'Pneu Traseiro'),
(8, 'Bateria'),
(9, 'Óleo de Motor'),
(10, 'Filtro de Óleo'),
(11, 'Filtro de Ar'),
(12, 'Cabo de Embreagem'),
(13, 'Cabo de Acelerador'),
(14, 'Rolamento de Roda'),
(15, 'Amortecedor Traseiro'),
(16, 'Retrovisor Esquerdo'),
(17, 'Retrovisor Direito'),
(18, 'Farol'),
(19, 'Lanterna Traseira'),
(20, 'Pisca-Pisca Dianteiro'),
(21, 'Pisca-Pisca Traseiro'),
(22, 'Manete de Embreagem'),
(23, 'Manete de Freio'),
(24, 'Coroa'),
(25, 'Pinhão'),
(26, 'Velas de Ignição'),
(27, 'Carburador'),
(28, 'Injetor de Combustível'),
(29, 'Guidão'),
(30, 'Painel de Instrumentos'),
(31, 'Banco de Moto');

-- --------------------------------------------------------

--
-- Estrutura para tabela `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `username` varchar(25) NOT NULL,
  `senha` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `usuario`
--

INSERT INTO `usuario` (`id`, `nome`, `username`, `senha`) VALUES
(1, 'lucas eduardo', 'lucas', '123');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `estoque`
--
ALTER TABLE `estoque`
  ADD PRIMARY KEY (`id`),
  ADD KEY `peca` (`peca`);

--
-- Índices de tabela `marca`
--
ALTER TABLE `marca`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `modelo`
--
ALTER TABLE `modelo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `marca_id` (`marca_id`);

--
-- Índices de tabela `moto_cliente`
--
ALTER TABLE `moto_cliente`
  ADD PRIMARY KEY (`id`),
  ADD KEY `modelo_id` (`modelo_id`),
  ADD KEY `cliente_id` (`cliente_id`);

--
-- Índices de tabela `peca`
--
ALTER TABLE `peca`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=69;

--
-- AUTO_INCREMENT de tabela `estoque`
--
ALTER TABLE `estoque`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT de tabela `marca`
--
ALTER TABLE `marca`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de tabela `modelo`
--
ALTER TABLE `modelo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de tabela `moto_cliente`
--
ALTER TABLE `moto_cliente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de tabela `peca`
--
ALTER TABLE `peca`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT de tabela `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `estoque`
--
ALTER TABLE `estoque`
  ADD CONSTRAINT `estoque_ibfk_1` FOREIGN KEY (`peca`) REFERENCES `peca` (`id`);

--
-- Restrições para tabelas `modelo`
--
ALTER TABLE `modelo`
  ADD CONSTRAINT `modelo_ibfk_1` FOREIGN KEY (`marca_id`) REFERENCES `marca` (`id`);

--
-- Restrições para tabelas `moto_cliente`
--
ALTER TABLE `moto_cliente`
  ADD CONSTRAINT `moto_cliente_ibfk_1` FOREIGN KEY (`modelo_id`) REFERENCES `modelo` (`id`),
  ADD CONSTRAINT `moto_cliente_ibfk_2` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
