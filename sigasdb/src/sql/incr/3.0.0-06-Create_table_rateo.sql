CREATE TABLE sigas_rateo (
	id_rateo int4 not null,
	id_anag int4 not null,
	importo numeric(30, 10),
	mese varchar(50) not null,
	id_provincia numeric(7) not null,
	annualita varchar(10) not null
);

ALTER TABLE sigas_rateo 
	ADD CONSTRAINT pk_sigas_rateo PRIMARY KEY (id_rateo);
	
ALTER TABLE sigas_rateo 
	ADD CONSTRAINT fk_sigas_anagrafica_soggetti_sigas_rateo FOREIGN KEY (id_anag) 
	REFERENCES sigas_anagrafica_soggetti(id_anag) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE CASCADE;
	
ALTER TABLE sigas_rateo 
	ADD CONSTRAINT fk_sigas_provincia_sigas_rateo FOREIGN KEY (id_provincia) 
	REFERENCES sigas_provincia(id_provincia) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE CASCADE;
	
CREATE SEQUENCE seq_sigas_rateo OWNED BY sigas_rateo.id_rateo;		

GRANT USAGE ON SCHEMA sigas TO sigas_rw;
GRANT SELECT,INSERT,UPDATE,DELETE ON ALL TABLES IN SCHEMA sigas TO sigas_rw;
GRANT SELECT,UPDATE ON ALL SEQUENCES IN SCHEMA sigas TO sigas_rw;