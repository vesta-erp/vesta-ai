-- =============================================================
-- VESTA - Correção dos hashes de senha
-- Substitui placeholders por hashes BCrypt válidos (força 10)
-- admin123    → perfil ADMIN
-- gestor123   → perfil GESTOR
-- operador123 → perfil OPERADOR
-- =============================================================

UPDATE TB_USUARIO SET ds_senha_hash = '$2a$10$76nvQyYyQyD/DAtsv8v7hu36sT9yBDIAkg6CNu1VbFlmMtzS1DE.G'
WHERE ds_email = 'admin@vesta.gov.br';

UPDATE TB_USUARIO SET ds_senha_hash = '$2a$10$2gZdn/g69l.a3flr0KaD2uFp./..9tUmLnmEp.PgmdCuCGpcNeuBO'
WHERE ds_email IN (
    'ana.sp@vesta.gov.br',
    'bruno.sjc@vesta.gov.br',
    'diana.rj@vesta.gov.br',
    'eduardo.mg@vesta.gov.br',
    'fernanda.rs@vesta.gov.br'
);

UPDATE TB_USUARIO SET ds_senha_hash = '$2a$10$YwbK7L3yZXUDSt7JyUXuJu7cgrSzQ96LkS4Pk2eeSEzb4i8TNJJty'
WHERE ds_email IN (
    'gabriel.sp1@vesta.gov.br',
    'helena.sp2@vesta.gov.br',
    'igor.sjc@vesta.gov.br',
    'julia.rj@vesta.gov.br',
    'kevin.ctg@vesta.gov.br',
    'laura.bh@vesta.gov.br',
    'marcos.pel@vesta.gov.br',
    'nadia.poa@vesta.gov.br',
    'otavio.rg@vesta.gov.br'
);
