CREATE TABLE sigas_dich_compensazioni (	
	conguaglio_di_riferimento numeric(30,10) NULL,
	conguaglio_compensato numeric(30,10) NULL,
	compensazione numeric(30,10) NULL,
	data_compensazione timestamp null,
	id_compensazione int4 not null,
	id_consumi int4 not null
);

ALTER TABLE sigas_dich_compensazioni 
	ADD CONSTRAINT pk_sigas_dich_compensazioni PRIMARY KEY (id_compensazione);
	
ALTER TABLE sigas_dich_compensazioni 
	ADD CONSTRAINT fk_sigas_dich_consumi_sigas_dich_compensazioni FOREIGN KEY (id_consumi) 
	REFERENCES sigas_dich_consumi(id_consumi) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE CASCADE;
	
CREATE SEQUENCE seq_dich_compensazioni OWNED BY sigas_dich_compensazioni.id_compensazione;		

GRANT USAGE ON SCHEMA sigas TO sigas_rw;
GRANT SELECT,INSERT,UPDATE,DELETE ON ALL TABLES IN SCHEMA sigas TO sigas_rw;
GRANT SELECT,UPDATE ON ALL SEQUENCES IN SCHEMA sigas TO sigas_rw;