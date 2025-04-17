ALTER TABLE IF EXISTS sigas_deposito_causionale DROP CONSTRAINT IF EXISTS pk_sigas_deposito_causionale;

ALTER TABLE IF EXISTS sigas_deposito_causionale DROP CONSTRAINT IF EXISTS fk_sigas_documenti_sigas_deposito_causionale;

ALTER TABLE IF EXISTS sigas_deposito_causionale DROP CONSTRAINT IF EXISTS fk_sigas_provincia_sigas_deposito_causionale;

ALTER TABLE IF EXISTS sigas_deposito_causionale DROP CONSTRAINT IF EXISTS fk_sigas_anagrafica_soggetti_sigas_deposito_causionale;

DROP SEQUENCE IF EXISTS seq_sigas_deposito_causionale;

DROP TABLE IF EXISTS sigas_deposito_causionale;

CREATE TABLE sigas_deposito_cauzionale (
	id_deposito_cauzionale int4 not null,	
	importo numeric(30, 10) null,
	id_provincia numeric(7) not null,
	id_documento int4 NOT NULL,	
	id_anag int4 not null,
	anno_acccertamento varchar(10) null,
	numero_accertamento varchar(50) null,
	numero_determina varchar(50) null
);