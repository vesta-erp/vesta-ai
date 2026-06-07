-- =============================================================
-- VESTA - DML - Carga de Dados de Teste (152 registros)
-- Disciplina: Mastering Relational and Non-Relational Database
-- FIAP Global Solution 2026/1
-- =============================================================

-- PERFIS (3)
INSERT INTO TB_PERFIL_ACESSO (nm_perfil, ds_permissoes) VALUES ('ADMIN',    'Acesso total ao sistema');
INSERT INTO TB_PERFIL_ACESSO (nm_perfil, ds_permissoes) VALUES ('GESTOR',   'Visualizar e atuar na região');
INSERT INTO TB_PERFIL_ACESSO (nm_perfil, ds_permissoes) VALUES ('OPERADOR', 'Atualizar abrigo vinculado');

-- REGIÕES (5)
INSERT INTO TB_REGIAO (nm_regiao, sg_estado) VALUES ('Grande São Paulo',         'SP');
INSERT INTO TB_REGIAO (nm_regiao, sg_estado) VALUES ('Vale do Paraíba',          'SP');
INSERT INTO TB_REGIAO (nm_regiao, sg_estado) VALUES ('Baixada Fluminense',       'RJ');
INSERT INTO TB_REGIAO (nm_regiao, sg_estado) VALUES ('Região Metropolitana BH',  'MG');
INSERT INTO TB_REGIAO (nm_regiao, sg_estado) VALUES ('Litoral Sul RS',           'RS');

-- INSTITUIÇÕES (5)
INSERT INTO TB_INSTITUICAO (nm_instituicao, tp_instituicao, id_regiao) VALUES ('Defesa Civil SP Capital',      'DEFESA_CIVIL',      1);
INSERT INTO TB_INSTITUICAO (nm_instituicao, tp_instituicao, id_regiao) VALUES ('Prefeitura de São José',       'PREFEITURA',        2);
INSERT INTO TB_INSTITUICAO (nm_instituicao, tp_instituicao, id_regiao) VALUES ('Defesa Civil Estado RJ',       'GOVERNO_ESTADUAL',  3);
INSERT INTO TB_INSTITUICAO (nm_instituicao, tp_instituicao, id_regiao) VALUES ('Prefeitura de Contagem',       'PREFEITURA',        4);
INSERT INTO TB_INSTITUICAO (nm_instituicao, tp_instituicao, id_regiao) VALUES ('Defesa Civil RS',              'DEFESA_CIVIL',      5);

-- ABRIGOS (10)
INSERT INTO TB_ABRIGO (nm_abrigo, ds_endereco, qt_capacidade_maxima, qt_ocupacao_atual, st_status, id_regiao, id_instituicao)
VALUES ('Abrigo Ibirapuera',      'Av. Pedro Álvares Cabral, 1301 - SP', 300, 245, 'ATIVO',   1, 1);
INSERT INTO TB_ABRIGO (nm_abrigo, ds_endereco, qt_capacidade_maxima, qt_ocupacao_atual, st_status, id_regiao, id_instituicao)
VALUES ('Abrigo Ginásio Norte',   'R. das Palmeiras, 50 - SP',           200, 200, 'LOTADO',  1, 1);
INSERT INTO TB_ABRIGO (nm_abrigo, ds_endereco, qt_capacidade_maxima, qt_ocupacao_atual, st_status, id_regiao, id_instituicao)
VALUES ('Abrigo Escola Municipal','Rua Tiradentes, 22 - SJC',            150,  98, 'ATIVO',   2, 2);
INSERT INTO TB_ABRIGO (nm_abrigo, ds_endereco, qt_capacidade_maxima, qt_ocupacao_atual, st_status, id_regiao, id_instituicao)
VALUES ('Abrigo Estadual Leste',  'Av. Brasil, 5000 - Duque de Caxias',  400, 312, 'ATIVO',   3, 3);
INSERT INTO TB_ABRIGO (nm_abrigo, ds_endereco, qt_capacidade_maxima, qt_ocupacao_atual, st_status, id_regiao, id_instituicao)
VALUES ('Abrigo Estádio Municipal','Rua do Sport, 10 - Nova Iguaçu',     350,   0, 'INATIVO', 3, 3);
INSERT INTO TB_ABRIGO (nm_abrigo, ds_endereco, qt_capacidade_maxima, qt_ocupacao_atual, st_status, id_regiao, id_instituicao)
VALUES ('Abrigo SESC Contagem',   'Av. João César, 234 - Contagem',      250, 189, 'ATIVO',   4, 4);
INSERT INTO TB_ABRIGO (nm_abrigo, ds_endereco, qt_capacidade_maxima, qt_ocupacao_atual, st_status, id_regiao, id_instituicao)
VALUES ('Abrigo Centro BH',       'Praça Sete, 100 - BH',                180,  55, 'ATIVO',   4, 4);
INSERT INTO TB_ABRIGO (nm_abrigo, ds_endereco, qt_capacidade_maxima, qt_ocupacao_atual, st_status, id_regiao, id_instituicao)
VALUES ('Abrigo Pelotas Sul',     'R. XV de Novembro, 300 - Pelotas',    220, 198, 'ATIVO',   5, 5);
INSERT INTO TB_ABRIGO (nm_abrigo, ds_endereco, qt_capacidade_maxima, qt_ocupacao_atual, st_status, id_regiao, id_instituicao)
VALUES ('Abrigo Porto Alegre',    'Av. Borges de Medeiros, 1700 - POA',  500, 421, 'ATIVO',   5, 5);
INSERT INTO TB_ABRIGO (nm_abrigo, ds_endereco, qt_capacidade_maxima, qt_ocupacao_atual, st_status, id_regiao, id_instituicao)
VALUES ('Abrigo Escola RS',       'Rua Uruguai, 45 - Rio Grande',        100,  78, 'ATIVO',   5, 5);

-- USUÁRIOS (15)
INSERT INTO TB_USUARIO (nm_usuario, ds_email, ds_senha_hash, id_perfil, id_abrigo)
VALUES ('Carlos Admin',     'admin@vesta.gov.br',       '$2a$10$hashadmin1234', 1, NULL);
INSERT INTO TB_USUARIO (nm_usuario, ds_email, ds_senha_hash, id_perfil, id_abrigo)
VALUES ('Ana Gestora SP',   'ana.sp@vesta.gov.br',      '$2a$10$hashgest0001',  2, NULL);
INSERT INTO TB_USUARIO (nm_usuario, ds_email, ds_senha_hash, id_perfil, id_abrigo)
VALUES ('Bruno Gestor SJC', 'bruno.sjc@vesta.gov.br',   '$2a$10$hashgest0002',  2, NULL);
INSERT INTO TB_USUARIO (nm_usuario, ds_email, ds_senha_hash, id_perfil, id_abrigo)
VALUES ('Diana Gestora RJ', 'diana.rj@vesta.gov.br',    '$2a$10$hashgest0003',  2, NULL);
INSERT INTO TB_USUARIO (nm_usuario, ds_email, ds_senha_hash, id_perfil, id_abrigo)
VALUES ('Eduardo Gestor MG','eduardo.mg@vesta.gov.br',  '$2a$10$hashgest0004',  2, NULL);
INSERT INTO TB_USUARIO (nm_usuario, ds_email, ds_senha_hash, id_perfil, id_abrigo)
VALUES ('Fernanda Gest RS', 'fernanda.rs@vesta.gov.br', '$2a$10$hashgest0005',  2, NULL);
INSERT INTO TB_USUARIO (nm_usuario, ds_email, ds_senha_hash, id_perfil, id_abrigo)
VALUES ('Gabriel Op SP1',   'gabriel.sp1@vesta.gov.br', '$2a$10$hashop00001',   3, 1);
INSERT INTO TB_USUARIO (nm_usuario, ds_email, ds_senha_hash, id_perfil, id_abrigo)
VALUES ('Helena Op SP2',    'helena.sp2@vesta.gov.br',  '$2a$10$hashop00002',   3, 2);
INSERT INTO TB_USUARIO (nm_usuario, ds_email, ds_senha_hash, id_perfil, id_abrigo)
VALUES ('Igor Op SJC',      'igor.sjc@vesta.gov.br',    '$2a$10$hashop00003',   3, 3);
INSERT INTO TB_USUARIO (nm_usuario, ds_email, ds_senha_hash, id_perfil, id_abrigo)
VALUES ('Júlia Op RJ',      'julia.rj@vesta.gov.br',    '$2a$10$hashop00004',   3, 4);
INSERT INTO TB_USUARIO (nm_usuario, ds_email, ds_senha_hash, id_perfil, id_abrigo)
VALUES ('Kevin Op CTG',     'kevin.ctg@vesta.gov.br',   '$2a$10$hashop00005',   3, 6);
INSERT INTO TB_USUARIO (nm_usuario, ds_email, ds_senha_hash, id_perfil, id_abrigo)
VALUES ('Laura Op BH',      'laura.bh@vesta.gov.br',    '$2a$10$hashop00006',   3, 7);
INSERT INTO TB_USUARIO (nm_usuario, ds_email, ds_senha_hash, id_perfil, id_abrigo)
VALUES ('Marcos Op Pel',    'marcos.pel@vesta.gov.br',  '$2a$10$hashop00007',   3, 8);
INSERT INTO TB_USUARIO (nm_usuario, ds_email, ds_senha_hash, id_perfil, id_abrigo)
VALUES ('Nadia Op POA',     'nadia.poa@vesta.gov.br',   '$2a$10$hashop00008',   3, 9);
INSERT INTO TB_USUARIO (nm_usuario, ds_email, ds_senha_hash, id_perfil, id_abrigo)
VALUES ('Otávio Op RG',     'otavio.rg@vesta.gov.br',   '$2a$10$hashop00009',   3, 10);

-- RECURSOS (10)
INSERT INTO TB_RECURSO (nm_recurso, tp_recurso, ds_unidade_medida) VALUES ('Água Potável',        'AGUA',        'LITRO');
INSERT INTO TB_RECURSO (nm_recurso, tp_recurso, ds_unidade_medida) VALUES ('Arroz',               'ALIMENTO',    'KG');
INSERT INTO TB_RECURSO (nm_recurso, tp_recurso, ds_unidade_medida) VALUES ('Feijão',              'ALIMENTO',    'KG');
INSERT INTO TB_RECURSO (nm_recurso, tp_recurso, ds_unidade_medida) VALUES ('Cobertor',            'VESTUARIO',   'UNIDADE');
INSERT INTO TB_RECURSO (nm_recurso, tp_recurso, ds_unidade_medida) VALUES ('Kit Higiene Pessoal', 'HIGIENE',     'UNIDADE');
INSERT INTO TB_RECURSO (nm_recurso, tp_recurso, ds_unidade_medida) VALUES ('Medicamento Básico',  'MEDICAMENTO', 'CAIXA');
INSERT INTO TB_RECURSO (nm_recurso, tp_recurso, ds_unidade_medida) VALUES ('Fraldas',             'HIGIENE',     'PACOTE');
INSERT INTO TB_RECURSO (nm_recurso, tp_recurso, ds_unidade_medida) VALUES ('Leite em Pó',         'ALIMENTO',    'KG');
INSERT INTO TB_RECURSO (nm_recurso, tp_recurso, ds_unidade_medida) VALUES ('Colchão',             'EQUIPAMENTO', 'UNIDADE');
INSERT INTO TB_RECURSO (nm_recurso, tp_recurso, ds_unidade_medida) VALUES ('Gerador de Energia',  'EQUIPAMENTO', 'UNIDADE');

-- ESTOQUE POR ABRIGO (25 registros)
-- Abrigo 1 - Ibirapuera
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (1, 1,  500, 1000);
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (1, 2,  120,  100);
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (1, 4,   80,  150);
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (1, 6,   15,   30);
-- Abrigo 2 - Ginásio Norte (LOTADO)
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (2, 1,  200,  400);
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (2, 2,   50,   80);
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (2, 5,   20,   50);
-- Abrigo 3 - Escola SJC
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (3, 1,  600,  300);
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (3, 3,  100,   60);
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (3, 9,  120,  100);
-- Abrigo 4 - Estadual RJ
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (4, 1, 1200, 1600);
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (4, 2,  200,  160);
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (4, 7,   30,   80);
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (4, 6,   25,   40);
-- Abrigo 6 - SESC Contagem
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (6, 1,  750,  500);
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (6, 4,  200,  125);
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (6, 8,   40,   60);
-- Abrigo 8 - Pelotas Sul
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (8, 1,  300,  400);
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (8, 2,   90,   80);
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (8, 4,  180,  100);
-- Abrigo 9 - Porto Alegre
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (9, 1,  800, 1000);
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (9, 2,  250,  200);
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (9, 6,   20,   50);
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (9, 9,  430,  250);
INSERT INTO TB_ESTOQUE_ABRIGO (id_abrigo, id_recurso, qt_atual, qt_minima) VALUES (9,10,    2,    2);

-- FAMÍLIAS (15)
INSERT INTO TB_FAMILIA (nm_responsavel, nr_cpf_responsavel, nr_telefone, id_abrigo, dt_entrada) VALUES ('José da Silva',    '11122233301', '(11)91111-0001', 1, DATE '2026-05-01');
INSERT INTO TB_FAMILIA (nm_responsavel, nr_cpf_responsavel, nr_telefone, id_abrigo, dt_entrada) VALUES ('Maria Oliveira',   '11122233302', '(11)91111-0002', 1, DATE '2026-05-02');
INSERT INTO TB_FAMILIA (nm_responsavel, nr_cpf_responsavel, nr_telefone, id_abrigo, dt_entrada) VALUES ('Pedro Santos',     '11122233303', '(11)91111-0003', 1, DATE '2026-05-03');
INSERT INTO TB_FAMILIA (nm_responsavel, nr_cpf_responsavel, nr_telefone, id_abrigo, dt_entrada) VALUES ('Ana Costa',        '11122233304', '(11)91111-0004', 2, DATE '2026-05-01');
INSERT INTO TB_FAMILIA (nm_responsavel, nr_cpf_responsavel, nr_telefone, id_abrigo, dt_entrada) VALUES ('Luiz Ferreira',    '11122233305', '(11)91111-0005', 2, DATE '2026-05-02');
INSERT INTO TB_FAMILIA (nm_responsavel, nr_cpf_responsavel, nr_telefone, id_abrigo, dt_entrada) VALUES ('Carla Mendes',     '11122233306', '(12)92222-0001', 3, DATE '2026-05-04');
INSERT INTO TB_FAMILIA (nm_responsavel, nr_cpf_responsavel, nr_telefone, id_abrigo, dt_entrada) VALUES ('Roberto Lima',     '11122233307', '(21)93333-0001', 4, DATE '2026-05-01');
INSERT INTO TB_FAMILIA (nm_responsavel, nr_cpf_responsavel, nr_telefone, id_abrigo, dt_entrada) VALUES ('Fernanda Souza',   '11122233308', '(21)93333-0002', 4, DATE '2026-05-02');
INSERT INTO TB_FAMILIA (nm_responsavel, nr_cpf_responsavel, nr_telefone, id_abrigo, dt_entrada) VALUES ('Paulo Almeida',    '11122233309', '(21)93333-0003', 4, DATE '2026-05-03');
INSERT INTO TB_FAMILIA (nm_responsavel, nr_cpf_responsavel, nr_telefone, id_abrigo, dt_entrada) VALUES ('Sandra Rodrigues', '11122233310', '(31)94444-0001', 6, DATE '2026-05-05');
INSERT INTO TB_FAMILIA (nm_responsavel, nr_cpf_responsavel, nr_telefone, id_abrigo, dt_entrada) VALUES ('Marcos Pereira',   '11122233311', '(31)94444-0002', 6, DATE '2026-05-06');
INSERT INTO TB_FAMILIA (nm_responsavel, nr_cpf_responsavel, nr_telefone, id_abrigo, dt_entrada) VALUES ('Cristina Nunes',   '11122233312', '(53)95555-0001', 8, DATE '2026-05-01');
INSERT INTO TB_FAMILIA (nm_responsavel, nr_cpf_responsavel, nr_telefone, id_abrigo, dt_entrada) VALUES ('Tiago Barbosa',    '11122233313', '(51)96666-0001', 9, DATE '2026-05-01');
INSERT INTO TB_FAMILIA (nm_responsavel, nr_cpf_responsavel, nr_telefone, id_abrigo, dt_entrada) VALUES ('Juliana Castro',   '11122233314', '(51)96666-0002', 9, DATE '2026-05-02');
INSERT INTO TB_FAMILIA (nm_responsavel, nr_cpf_responsavel, nr_telefone, id_abrigo, dt_entrada) VALUES ('Renato Araújo',    '11122233315', '(53)97777-0001',10, DATE '2026-05-03');

-- PESSOAS ABRIGADAS (35 registros)
-- Família 1
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('José da Silva Jr.', DATE '2005-03-10', 1, 1);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Maria Silva',        DATE '2008-07-22', 1, 1);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Clara Silva',        DATE '1978-11-05', 1, 1);
-- Família 2
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Carlos Oliveira',    DATE '1970-01-15', 2, 1);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Beatriz Oliveira',   DATE '2003-06-30', 2, 1);
-- Família 3
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Lucas Santos',       DATE '1995-09-12', 3, 1);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Mariana Santos',     DATE '1998-04-18', 3, 1);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Vinícius Santos',    DATE '2020-02-28', 3, 1);
-- Família 4
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Rafael Costa',       DATE '1985-08-01', 4, 2);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Isabela Costa',      DATE '1988-11-14', 4, 2);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Miguel Costa',       DATE '2015-05-20', 4, 2);
-- Família 5
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Antônio Ferreira',   DATE '1960-12-03', 5, 2);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Teresa Ferreira',    DATE '1963-07-17', 5, 2);
-- Família 6
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Felipe Mendes',      DATE '1990-03-25', 6, 3);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Sofia Mendes',       DATE '2018-10-11', 6, 3);
-- Família 7
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Roberto Lima Jr.',   DATE '1982-06-09', 7, 4);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Patricia Lima',      DATE '1985-02-14', 7, 4);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Lara Lima',          DATE '2012-09-30', 7, 4);
-- Família 8
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Gustavo Souza',      DATE '1975-04-22', 8, 4);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Mariana Souza',      DATE '1978-08-16', 8, 4);
-- Família 9
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Diego Almeida',      DATE '1988-01-07', 9, 4);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Aline Almeida',      DATE '1991-05-23', 9, 4);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Theo Almeida',       DATE '2022-03-15', 9, 4);
-- Família 10
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Rodrigo Rodrigues',  DATE '1979-10-30',10, 6);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Camila Rodrigues',   DATE '1982-06-05',10, 6);
-- Família 11
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Anderson Pereira',   DATE '1993-12-19',11, 6);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Larissa Pereira',    DATE '1996-08-28',11, 6);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Noah Pereira',       DATE '2023-01-10',11, 6);
-- Família 12
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Sebastião Nunes',    DATE '1958-03-14',12, 8);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Helena Nunes',       DATE '1961-07-02',12, 8);
-- Família 13
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Henrique Barbosa',   DATE '1987-11-28',13, 9);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Vanessa Barbosa',    DATE '1990-04-17',13, 9);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Alice Barbosa',      DATE '2019-08-05',13, 9);
-- Família 14
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Eduardo Castro',     DATE '1972-09-21',14, 9);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Monica Castro',      DATE '1975-01-31',14, 9);
-- Família 15
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Renato Araújo Jr.',  DATE '2000-06-14',15,10);
INSERT INTO TB_PESSOA_ABRIGADA (nm_pessoa, dt_nascimento, id_familia, id_abrigo) VALUES ('Leticia Araújo',     DATE '2003-11-22',15,10);

-- OCORRÊNCIAS (8)
INSERT INTO TB_OCORRENCIA (id_abrigo, id_usuario, nm_titulo, ds_descricao, tp_severidade, st_status)
VALUES (1, 7, 'Falta de água potável',
        'Reservatório com menos de 50% da capacidade mínima. Risco iminente.',
        'CRITICA', 'ABERTA');
INSERT INTO TB_OCORRENCIA (id_abrigo, id_usuario, nm_titulo, ds_descricao, tp_severidade, st_status)
VALUES (2, 8, 'Superlotação confirmada',
        'Abrigo atingiu 100% da capacidade. Novas famílias recusadas.',
        'ALTA', 'ABERTA');
INSERT INTO TB_OCORRENCIA (id_abrigo, id_usuario, nm_titulo, ds_descricao, tp_severidade, st_status)
VALUES (4, 10, 'Infiltração no teto',
        'Setor B com infiltração após chuvas. 30 pessoas realocadas internamente.',
        'MEDIA', 'EM_ANDAMENTO');
INSERT INTO TB_OCORRENCIA (id_abrigo, id_usuario, nm_titulo, ds_descricao, tp_severidade, st_status)
VALUES (4, 10, 'Falta de medicamentos',
        'Estoque de medicamento básico esgotado. Idosos sem medicação.',
        'ALTA', 'ABERTA');
INSERT INTO TB_OCORRENCIA (id_abrigo, id_usuario, nm_titulo, ds_descricao, tp_severidade, st_status)
VALUES (6, 11, 'Falta de leite para bebês',
        'Estoque de leite em pó insuficiente para demanda.',
        'ALTA', 'ABERTA');
INSERT INTO TB_OCORRENCIA (id_abrigo, id_usuario, nm_titulo, ds_descricao, tp_severidade, st_status)
VALUES (8, 13, 'Problema na rede elétrica',
        'Curto-circuito no bloco A. Gerador acionado.',
        'MEDIA', 'EM_ANDAMENTO');
INSERT INTO TB_OCORRENCIA (id_abrigo, id_usuario, nm_titulo, ds_descricao, tp_severidade, st_status)
VALUES (9, 14, 'Risco de inundação do subsolo',
        'Nível d''água subindo próximo ao depósito de suprimentos.',
        'CRITICA', 'ABERTA');
INSERT INTO TB_OCORRENCIA (id_abrigo, id_usuario, nm_titulo, ds_descricao, tp_severidade, st_status, dt_resolucao)
VALUES (3, 9, 'Falta de cobertores',
        'Resolvida após doação recebida.',
        'BAIXA', 'RESOLVIDA', SYSDATE-1);

-- SOLICITAÇÕES DE RECURSO (8)
INSERT INTO TB_SOLICITACAO_RECURSO (id_abrigo, id_recurso, id_usuario_solicitante, qt_solicitada, st_status, ds_justificativa)
VALUES (1, 1, 7, 2000, 'ABERTA',         'Água abaixo do mínimo. Risco de desabastecimento em 24h.');
INSERT INTO TB_SOLICITACAO_RECURSO (id_abrigo, id_recurso, id_usuario_solicitante, qt_solicitada, st_status, ds_justificativa)
VALUES (1, 4, 7,  100, 'EM_ANALISE',     'Cobertor insuficiente para noites frias. Famílias vulneráveis.');
INSERT INTO TB_SOLICITACAO_RECURSO (id_abrigo, id_recurso, id_usuario_solicitante, qt_solicitada, st_status, ds_justificativa)
VALUES (2, 1, 8,  500, 'EM_ATENDIMENTO', 'Abrigo lotado. Consumo de água dobrou.');
INSERT INTO TB_SOLICITACAO_RECURSO (id_abrigo, id_recurso, id_usuario_solicitante, qt_solicitada, st_status, ds_justificativa)
VALUES (4, 7, 10,  80, 'ABERTA',         'Fraldas esgotadas. Há 15 bebês no abrigo.');
INSERT INTO TB_SOLICITACAO_RECURSO (id_abrigo, id_recurso, id_usuario_solicitante, qt_solicitada, st_status, ds_justificativa)
VALUES (4, 6, 10,  30, 'ABERTA',         'Sem medicação básica para idosos.');
INSERT INTO TB_SOLICITACAO_RECURSO (id_abrigo, id_recurso, id_usuario_solicitante, qt_solicitada, st_status, ds_justificativa)
VALUES (6, 8, 11,  40, 'EM_ANALISE',     'Leite em pó para 8 bebês abaixo de 1 ano.');
INSERT INTO TB_SOLICITACAO_RECURSO (id_abrigo, id_recurso, id_usuario_solicitante, qt_solicitada, st_status, ds_justificativa)
VALUES (9, 1, 14, 500, 'CONCLUIDA',      'Reposição urgente de água concluída.');
INSERT INTO TB_SOLICITACAO_RECURSO (id_abrigo, id_recurso, id_usuario_solicitante, qt_solicitada, st_status, ds_justificativa)
VALUES (8, 1, 13, 300, 'ABERTA',         'Estoque de água abaixo do mínimo com previsão de chuvas.');

-- MOVIMENTAÇÕES DE RECURSO (5)
INSERT INTO TB_MOVIMENTACAO_RECURSO (id_solicitacao, id_abrigo, id_recurso, id_usuario, tp_movimentacao, qt_movimentada, ds_observacao)
VALUES (7, 9, 1, 14, 'ENTRADA', 500, 'Recebimento reposição urgente - caminhão pipa');
INSERT INTO TB_MOVIMENTACAO_RECURSO (id_solicitacao, id_abrigo, id_recurso, id_usuario, tp_movimentacao, qt_movimentada, ds_observacao)
VALUES (3, 2, 1,  8, 'ENTRADA', 200, 'Entrega parcial. Restante em trânsito.');
INSERT INTO TB_MOVIMENTACAO_RECURSO (id_solicitacao, id_abrigo, id_recurso, id_usuario, tp_movimentacao, qt_movimentada, ds_observacao)
VALUES (NULL, 1, 1, 7, 'SAIDA',   50, 'Consumo diário registrado manualmente');
INSERT INTO TB_MOVIMENTACAO_RECURSO (id_solicitacao, id_abrigo, id_recurso, id_usuario, tp_movimentacao, qt_movimentada, ds_observacao)
VALUES (NULL, 4, 2, 10, 'ENTRADA', 100, 'Doação de supermercado local');
INSERT INTO TB_MOVIMENTACAO_RECURSO (id_solicitacao, id_abrigo, id_recurso, id_usuario, tp_movimentacao, qt_movimentada, ds_observacao)
VALUES (NULL, 6, 8, 11, 'AJUSTE',   -5, 'Correção de inventário - contagem física');

-- TRANSFERÊNCIAS (3)
INSERT INTO TB_TRANSFERENCIA_ABRIGO (id_abrigo_origem, id_abrigo_destino, id_familia, id_usuario_responsavel, ds_motivo, st_status)
VALUES (2, 1, 4, 2, 'Abrigo de origem lotado. Destino com vagas disponíveis.', 'PENDENTE');
INSERT INTO TB_TRANSFERENCIA_ABRIGO (id_abrigo_origem, id_abrigo_destino, id_familia, id_usuario_responsavel, ds_motivo, st_status, dt_conclusao)
VALUES (4, 6, 9, 4, 'Família solicitou transferência para proximidade de familiar.', 'CONCLUIDA', SYSDATE-2);
INSERT INTO TB_TRANSFERENCIA_ABRIGO (id_abrigo_origem, id_abrigo_destino, id_familia, id_usuario_responsavel, ds_motivo, st_status)
VALUES (9, 10, 14, 6, 'Redistribuição por capacidade disponível no abrigo escola RS.', 'APROVADA');

-- ALERTAS (5)
INSERT INTO TB_ALERTA (id_abrigo, tp_alerta, ds_mensagem, st_status)
VALUES (1, 'ESTOQUE_CRITICO',   'Água potável abaixo do mínimo (500L / mínimo 1000L). Solicitação aberta.', 'ATIVO');
INSERT INTO TB_ALERTA (id_abrigo, tp_alerta, ds_mensagem, st_status)
VALUES (2, 'LOTACAO',           'Abrigo Ginásio Norte atingiu 100% da capacidade (200/200).', 'ATIVO');
INSERT INTO TB_ALERTA (id_abrigo, tp_alerta, ds_mensagem, st_status)
VALUES (4, 'ESTOQUE_CRITICO',   'Fraldas abaixo do mínimo (30 / mínimo 80). Solicitação aberta.', 'ATIVO');
INSERT INTO TB_ALERTA (id_abrigo, tp_alerta, ds_mensagem, st_status)
VALUES (9, 'OCORRENCIA_CRITICA','Risco de inundação no subsolo. Suprimentos em risco.', 'ATIVO');
INSERT INTO TB_ALERTA (id_abrigo, tp_alerta, ds_mensagem, st_status, dt_resolucao)
VALUES (3, 'ESTOQUE_CRITICO',   'Cobertores abaixo do mínimo - RESOLVIDO por doação.', 'RESOLVIDO', SYSDATE-1);
