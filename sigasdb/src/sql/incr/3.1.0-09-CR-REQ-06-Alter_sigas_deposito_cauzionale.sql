ALTER TABLE sigas_deposito_cauzionale 
	ADD CONSTRAINT pk_sigas_deposito_cauzionale PRIMARY KEY (id_deposito_cauzionale);

ALTER TABLE sigas_deposito_cauzionale 
	ADD CONSTRAINT fk_sigas_documenti_sigas_deposito_cauzionale FOREIGN KEY (id_documento) 
	REFERENCES sigas_documenti(id_documento) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE sigas_deposito_cauzionale 
	ADD CONSTRAINT fk_sigas_provincia_sigas_deposito_cauzionale FOREIGN KEY (id_provincia) 
	REFERENCES sigas_provincia(id_provincia) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE CASCADE;
	
ALTER TABLE sigas_deposito_cauzionale 
	ADD CONSTRAINT fk_sigas_anagrafica_soggetti_sigas_deposito_cauzionale FOREIGN KEY (id_anag) 
	REFERENCES sigas_anagrafica_soggetti(id_anag) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE CASCADE;