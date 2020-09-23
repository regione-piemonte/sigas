ALTER TABLE sigas_comune 
	ADD CONSTRAINT fk_sigas_comune_sigas_provincia FOREIGN KEY (fk_provincia) 
		REFERENCES sigas_provincia(id_provincia) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE sigas_frontespizio_utf 
	ADD CONSTRAINT fk_sigas_frontespizio_utf_sigas_import_utf FOREIGN KEY (id_import) 
		REFERENCES sigas_import_utf(id_import) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE sigas_quadro_f_utf 
	ADD CONSTRAINT fk_sigas_quadro_f_utf_sigas_import_utf FOREIGN KEY (id_import) 
		REFERENCES sigas_import_utf(id_import) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE sigas_quadro_g_utf 
	ADD CONSTRAINT fk_sigas_quadro_g_utf_sigas_import_utf FOREIGN KEY (id_import) 
		REFERENCES sigas_import_utf(id_import) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;
	
ALTER TABLE sigas_quadro_h_utf 
	ADD CONSTRAINT fk_sigas_quadro_h_utf_sigas_import_utf FOREIGN KEY (id_import) 
		REFERENCES sigas_import_utf(id_import) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE sigas_quadro_i_utf
	ADD CONSTRAINT fk_sigas_quadro_i_utf_sigas_import_utf FOREIGN KEY (id_import) 
		REFERENCES sigas_import_utf(id_import) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE sigas_quadro_m_utf 
	ADD CONSTRAINT fk_sigas_quadro_m_utf_sigas_import_utf FOREIGN KEY (id_import) 
		REFERENCES sigas_import_utf(id_import) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE sigas_quadro_n_utf 
	ADD CONSTRAINT fk_sigas_quadro_i_utf_sigas_import_utf FOREIGN KEY (id_import) 
		REFERENCES sigas_import_utf(id_import) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE sigas_tipo_aliquote
	ADD CONSTRAINT fk_sigas_tipo_aliquote_sigas_tipo_consumo FOREIGN KEY (id_tipo_consumo) 
		REFERENCES sigas_tipo_consumo(id_tipo_consumo) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE sigas_aliquote 
	ADD CONSTRAINT fk_sigas_aliquote_sigas_tipo_aliquote FOREIGN KEY (id_tipo_aliquota) 
		REFERENCES sigas_tipo_aliquote(id_tipo_aliquota) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE sigas_anagrafica_soggetti
	ADD CONSTRAINT fk_sigas_anagrafica_soggetti_sigas_comune FOREIGN KEY (fk_comune) 
		REFERENCES sigas_comune(id_comune) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;
		
ALTER TABLE sigas_anagrafica_soggetti		
	ADD CONSTRAINT fk_sigas_anagrafica_soggetti_sigas_provincia FOREIGN KEY (fk_provincia) 
		REFERENCES sigas_provincia(id_provincia) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE sigas_dich_consumi 
	ADD CONSTRAINT fk_sigas_dich_consumi_sigas_anagrafica_soggetti FOREIGN KEY (id_anag) 
		REFERENCES sigas_anagrafica_soggetti(id_anag) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE sigas_dich_consumi 		
	ADD CONSTRAINT fk_sigas_dich_consumi_sigas_import_utf FOREIGN KEY (id_import) 
		REFERENCES sigas_import_utf(id_import) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE sigas_dich_scarti
	ADD CONSTRAINT fk_sigas_dich_scarti_sigas_dich_consumi FOREIGN KEY (id_consumi) 
		REFERENCES sigas_dich_consumi(id_consumi) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE sigas_dich_versamenti 
	ADD CONSTRAINT fk_sigas_dich_versamenti_sigas_dich_consumi FOREIGN KEY (id_consumi) 
		REFERENCES sigas_dich_consumi(id_consumi) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;
		
ALTER TABLE sigas_dich_versamenti 
	ADD CONSTRAINT fk_sigas_dich_versamenti_sigas_anagrafica_soggetti FOREIGN KEY (id_anag) 
		REFERENCES sigas_anagrafica_soggetti(id_anag) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;
		
ALTER TABLE sigas_dich_versamenti 
	ADD CONSTRAINT fk_sigas_dich_versamenti_tipo_versamento FOREIGN KEY (id_tipo_versamento) 
		REFERENCES sigas_tipo_versamento(id_tipo_versamento) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;
		
ALTER TABLE sigas_dich_versamenti 
	ADD CONSTRAINT fk_sigas_dich_versamenti_sigas_provincia FOREIGN KEY (fk_provincia)
		REFERENCES sigas_provincia(id_provincia) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;
		
ALTER TABLE sigas_ana_comunicazioni  
	ADD CONSTRAINT fksigas_ana_comunicazioni_sigas_anagrafica_soggetti FOREIGN KEY (id_anag) 
		REFERENCES sigas_anagrafica_soggetti(id_anag) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;
		
ALTER TABLE sigas_ana_comunicazioni 
	ADD CONSTRAINT fk_sigas_ana_comunicazioni_sigas_tipo_comunicazioni FOREIGN KEY (id_tipo_comunicazione) 
		REFERENCES sigas_tipo_comunicazioni(id_tipo_comunicazione) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE sigas_allarmi 
	ADD CONSTRAINT fk_sigas_allarmisigas_dich_consumi FOREIGN KEY (id_consumi) 
		REFERENCES sigas_dich_consumi(id_consumi) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;
		
ALTER TABLE sigas_allarmi 
	ADD CONSTRAINT fk_sigas_allarmi_sigas_ana_comunicazioni FOREIGN KEY (id_comunicazione) 
		REFERENCES sigas_ana_comunicazioni(id_comunicazione) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;
		
ALTER TABLE sigas_allarmi 
	ADD CONSTRAINT fk_sigas_allarmi_sigas_tipo_allarmi FOREIGN KEY (id_tipo_allarme) 
		REFERENCES sigas_tipo_allarmi(id_tipo_allarme) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;
		
ALTER TABLE sigas_tassi 
	ADD CONSTRAINT fk_sigas_tassi_sigas_tipo_tassi FOREIGN KEY (id_tipo_tasso) 
		REFERENCES sigas_tipo_tassi(id_tipo_tasso) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;
		
ALTER TABLE sigas_rimborso 
	ADD CONSTRAINT fk_sigas_rimborso_sigas_anagrafica_soggetti FOREIGN KEY (id_anag) 
		REFERENCES sigas_anagrafica_soggetti(id_anag) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;
		
		
ALTER TABLE sigas_rimborso 
	ADD CONSTRAINT sigas_rimborso_sigas_ana_comunicazioni FOREIGN KEY (fk_comunicazioni)
		REFERENCES sigas_ana_comunicazioni(id_comunicazione) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;		