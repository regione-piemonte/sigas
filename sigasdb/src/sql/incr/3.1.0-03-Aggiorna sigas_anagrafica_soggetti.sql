ALTER TABLE sigas_anagrafica_soggetti ADD COLUMN selected_import boolean null;

ALTER TABLE sigas_anagrafica_soggetti ADD COLUMN id_import int8 null;

--ALTER TABLE sigas_anagrafica_soggetti DROP CONSTRAINT fk_sigas_import_uf_sigas_anagrafica_soggetti;

ALTER TABLE sigas_anagrafica_soggetti 
	ADD CONSTRAINT fk_sigas_import_uf_sigas_anagrafica_soggetti FOREIGN KEY (id_import) 
	REFERENCES sigas_import_utf(id_import) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE SET NULL;

UPDATE sigas_anagrafica_soggetti SET selected_import = true;