ALTER TABLE sigas_storico_anagrafica_soggetti ADD COLUMN fk_provincia numeric(7) null;

ALTER TABLE sigas_storico_anagrafica_soggetti ADD COLUMN fk_comune numeric(7) null;

ALTER TABLE sigas_storico_anagrafica_soggetti 
	ADD CONSTRAINT fk_sigas_storico_anagrafica_soggetti_sigas_comune FOREIGN KEY (fk_comune) 
	REFERENCES sigas_comune(id_comune) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE sigas_storico_anagrafica_soggetti 
	ADD CONSTRAINT fk_sigas_storico_anagrafica_soggetti_sigas_provincia FOREIGN KEY (fk_provincia) 
	REFERENCES sigas_provincia(id_provincia) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;