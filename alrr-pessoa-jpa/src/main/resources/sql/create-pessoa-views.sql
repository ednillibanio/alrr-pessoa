/**
 * Esse script não funciona para ser executado em sequencia. Tem que executar manualmente o create view no pgadmin antes de criar as tabelas.
 */
DROP TABLE IF EXISTS pessoa_schema.pais;
DROP TABLE IF EXISTS pessoa_schema.municipio;
DROP TABLE IF EXISTS pessoa_schema.bairro;
DROP TABLE IF EXISTS pessoa_schema.cep;
DROP TABLE IF EXISTS pessoa_schema.pais;

DROP VIEW IF EXISTS pessoa_schema.pais;
DROP VIEW IF EXISTS pessoa_schema.municipio;
DROP VIEW IF EXISTS pessoa_schema.bairro;
DROP VIEW IF EXISTS pessoa_schema.cep;
DROP VIEW IF EXISTS pessoa_schema.pais;

CREATE VIEW pessoa_schema.municipio AS SELECT municipio.id, municipio.version, municipio.situacao, municipio.nome, municipio.ibge_id, municipio.uf_id FROM localidade_schema.municipio;
CREATE VIEW pessoa_schema.bairro AS SELECT bairro.id, bairro.version, bairro.situacao, bairro.nome, bairro.municipio_id FROM localidade_schema.bairro;
CREATE VIEW pessoa_schema.cep AS SELECT cep.id, cep.version, cep.logradouro, cep.numero, cep.bairro_id, cep.municipio_id FROM localidade_schema.cep;
CREATE VIEW pessoa_schema.pais AS SELECT pais.id, pais.version, pais.situacao, pais.nome, pais.codigo_discagem, pais.nacionalidade, pais.sigla FROM localidade_schema.pais;
CREATE VIEW pessoa_schema.unidade_federativa AS SELECT unidade_federativa.id, unidade_federativa.version, unidade_federativa.situacao, unidade_federativa.nome, unidade_federativa.ibgeid, unidade_federativa.sigla, unidade_federativa.pais_id FROM localidade_schema.unidade_federativa;