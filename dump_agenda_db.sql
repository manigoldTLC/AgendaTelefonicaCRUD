CREATE DATABASE IF NOT EXISTS agenda_db;

USE agenda_db;

CREATE TABLE IF NOT EXISTS contatos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100)
);

TRUNCATE TABLE contatos;

-- INSERE OS DADOS INICIAIS
INSERT INTO contatos (nome, telefone, email) VALUES
('Ana Silva', '(62) 99999-1111', 'ana.silva@email.com'),
('Bruno Costa', '(62) 98888-2222', 'bruno.costa@email.com'),
('Carlos Souza', '(62) 98765-4321', 'carlos.souza@email.com'),
('Mariana Lima', '(62) 91234-5678', 'mariana.lima@email.com'),
('Rafael Oliveira', '(62) 95555-8888', 'rafael.oliveira@email.com');