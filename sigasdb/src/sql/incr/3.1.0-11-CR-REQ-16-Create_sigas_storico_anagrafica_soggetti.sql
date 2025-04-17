
ALTER TABLE IF EXISTS sigas_storico_anagrafica_soggetti DROP CONSTRAINT IF EXISTS pk_sigas_storico_anagrafica_soggetti;

ALTER TABLE IF EXISTS sigas_storico_anagrafica_soggetti DROP CONSTRAINT IF EXISTS fk_sigas_anagrafica_soggetti_sigas_storico_anagrafica_soggetti;

DROP SEQUENCE IF EXISTS seq_sigas_storico_anagrafica_soggetti;

DROP TABLE IF EXISTS sigas_storico_anagrafica_soggetti; 

CREATE TABLE sigas_storico_anagrafica_soggetti (	
	codice_azienda varchar(150) NULL,
	denominazione varchar(150) NULL,
	indirizzo varchar(255) NULL,
	iban varchar(50) null,
	email varchar(150) null,
	pec varchar(150) null,
	id_storico_anag int4 not null,
	id_anag int4 not null,
	data_riferimento timestamp null,
	cf_piva varchar(16) null,
	owner_operazione varchar(50) null,
	id_import int8 null,
	annualita varchar(10) null
);
