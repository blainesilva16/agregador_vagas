-- 0. Inserts users
INSERT INTO users (name, email, password) VALUES
                                              ('Alice Ferreira', 'alice.ferreira@email.com', 'hash_senha_p@ss1'),
                                              ('Bruno Guimarães', 'bruno.guima@email.com', 'seguranca_2026_br'),
                                              ('Carla Souza', 'carla.souza@email.com', 'carla_dev_top');

-- 1. Inserts para a tabela techs
INSERT INTO techs (name) VALUES
('Java'),
('Python'),
('SQL');

-- 2. Inserts para a tabela positions
INSERT INTO positions (name) VALUES
                                 ('Desenvolvedor'),
                                 ('Engenheiro de Dados'),
                                 ('Cientista de Dados');

-- 3. Inserts para a tabela customers
INSERT INTO customers (name, email, password, birthdate) VALUES
                                                             ('Matheus Silva', 'matheus.dev@email.com', 'hash_senha_123', '2002-04-15'),
                                                             ('Alice Ferreira', 'alice.ferreira@email.com', 'hash_senha_456', '1998-11-20'),
                                                             ('Bruno Guimarães', 'bruno.guima@email.com', 'hash_senha_789', '1995-02-10');

-- 4. Inserts para a tabela crawlerlogs (Vagas)
INSERT INTO crawlerlogs
(title, tech_id, position_id, city_name, uf_name, uf_abrev, tech_level, hiring_type, salary_range, company_name, posting_link, work_mode, creation_user, plataform)
VALUES
    ('Desenvolvedor Java Júnior', 1, 1, 'Limeira', 'São Paulo', 'SP', 'Júnior', 'CLT', 3500.00, 'Kafka Solutions', 'https://vaga.link/java-jr', 'HYBRID', 'CRAWLER', 'LinkedIn'),
    ('Engenheiro de Dados Pleno', 2, 2, 'Campinas', 'São Paulo', 'SP', 'Pleno', 'PJ', 9500.00, 'Interstellar Data', 'https://vaga.link/eng-dados', 'REMOTE', 'API', 'Gupy'),
    ('Cientista de Dados Sênior', 3, 3, 'São Paulo', 'São Paulo', 'SP', 'Sênior', 'CLT', 14000.00, 'Narnia Tech', 'https://vaga.link/data-science', 'REMOTE', 'CRAWLER', 'Indeed');

-- 5. Inserts para a tabela customerscrawlers (Intersecção)
INSERT INTO customerscrawlers (customer_id, crawler_id) VALUES
                                                            (1, 1),
                                                            (2, 2),
                                                            (3, 3);