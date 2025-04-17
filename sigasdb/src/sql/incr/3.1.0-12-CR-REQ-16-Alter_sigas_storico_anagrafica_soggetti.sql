ALTER TABLE sigas_storico_anagrafica_soggetti 
	ADD CONSTRAINT pk_sigas_storico_anagrafica_soggetti PRIMARY KEY (id_storico_anag);
	
ALTER TABLE sigas_storico_anagrafica_soggetti 
	ADD CONSTRAINT fk_sigas_anagrafica_soggetti_sigas_storico_anagrafica_soggetti FOREIGN KEY (id_anag) 
	REFERENCES sigas_anagrafica_soggetti(id_anag) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE CASCADE;