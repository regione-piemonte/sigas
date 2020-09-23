ALTER TABLE sigas_import_utf
	ADD CONSTRAINT pk_sigas_import_utf PRIMARY KEY (id_import);

ALTER TABLE sigas_provincia
	ADD CONSTRAINT pk_sigas_provincia PRIMARY KEY (id_provincia);

ALTER TABLE sigas_tipo_allarmi 
	ADD CONSTRAINT pk_sigas_tipo_allarmi PRIMARY KEY (id_tipo_allarme);

ALTER TABLE sigas_tipo_comunicazioni 
	ADD CONSTRAINT pk_sigas_tipo_comunicazioni PRIMARY KEY (id_tipo_comunicazione);

ALTER TABLE sigas_tipo_consumo 
	ADD CONSTRAINT pk_sigas_tipo_consumo PRIMARY KEY (id_tipo_consumo);

ALTER TABLE sigas_tipo_versamento 
	ADD CONSTRAINT pk_tipo_versamento PRIMARY KEY (id_tipo_versamento);

ALTER TABLE sigas_validazione 
	ADD CONSTRAINT pk_sigas_validazione PRIMARY KEY (id_valid);

ALTER TABLE sigas_comune
	ADD CONSTRAINT pk_sigas_comune PRIMARY KEY (id_comune);

ALTER TABLE sigas_frontespizio_utf 
	ADD CONSTRAINT pk_sigas_frontespizio_utf PRIMARY KEY (id_frontespizio);

ALTER TABLE sigas_quadro_f_utf 
	ADD CONSTRAINT pk_sigas_quadro_f_utf PRIMARY KEY (id_quadro_f);

ALTER TABLE sigas_quadro_g_utf 
	ADD CONSTRAINT pk_sigas_quadro_q_utf PRIMARY KEY (id_quadro_g);

ALTER TABLE sigas_quadro_h_utf 
	ADD CONSTRAINT pk_sigas_quadro_h_utf PRIMARY KEY (id_quadro_h);

ALTER TABLE sigas_quadro_i_utf 
	ADD CONSTRAINT pk_sigas_quadro_i_utf PRIMARY KEY (id_quadro_i);

ALTER TABLE sigas_quadro_m_utf 
	ADD CONSTRAINT pk_sigas_quadro_m_utf PRIMARY KEY (id_quadro_m);

ALTER TABLE sigas_quadro_n_utf
	ADD CONSTRAINT pk_sigas_quadro_n_utf PRIMARY KEY (id_quadro_n);

ALTER TABLE sigas_tipo_aliquote 
	ADD CONSTRAINT pk_sigas_tipo_aliquote PRIMARY KEY (id_tipo_aliquota);

ALTER TABLE sigas_aliquote
	ADD CONSTRAINT pk_sigas_aliquote PRIMARY KEY (id_aliquota);
	
ALTER TABLE sigas_anagrafica_soggetti 
	ADD CONSTRAINT pk_sigas_anagrafica_soggetti PRIMARY KEY (id_anag);

ALTER TABLE sigas_dich_consumi
	ADD CONSTRAINT pk_sigas_dich_consumi PRIMARY KEY (id_consumi);

ALTER TABLE sigas_dich_scarti 
	ADD CONSTRAINT pk_sigas_dich_scarti PRIMARY KEY (id_scarti);

ALTER TABLE sigas_dich_versamenti
	ADD CONSTRAINT pk_sigas_dich_versamenti PRIMARY KEY (id_versamento);

ALTER TABLE sigas_ana_comunicazioni 
	ADD CONSTRAINT pk_sigas_ana_comunicazioni PRIMARY KEY (id_comunicazione);

ALTER TABLE sigas_allarmi
	ADD CONSTRAINT pk_sigas_allarmi PRIMARY KEY (id_allarme);
	
ALTER TABLE sigas_tassi
	ADD CONSTRAINT pk_sigas_tassi PRIMARY KEY (id_tasso);
	
ALTER TABLE sigas_tipo_tassi
	ADD CONSTRAINT pk_sigas_tipo_tassi PRIMARY KEY (id_tipo_tasso);

ALTER TABLE sigas_report
	ADD CONSTRAINT pk_sigas_report PRIMARY KEY (id_report);
	
ALTER TABLE sigas_rimborso
	ADD CONSTRAINT pk_sigas_rimborsi PRIMARY KEY (id_rimborso);
	
