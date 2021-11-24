ALTER TABLE sigas_stato_documento 
	ADD CONSTRAINT pk_sigas_stato_documento PRIMARY KEY (id_stato_documento);
		
ALTER TABLE sigas_tipo_documento
	ADD CONSTRAINT pk_sigas_tipo_documento PRIMARY KEY (id_tipo_documento);
		
ALTER TABLE sigas_stato_archiviazione
	ADD CONSTRAINT pk_sigas_stato_allegato PRIMARY KEY (id_stato_archiviazione);
	
ALTER TABLE sigas_documenti
	ADD CONSTRAINT pk_sigas_documenti PRIMARY KEY (id_documento);
	
ALTER TABLE sigas_allegato
	ADD CONSTRAINT pk_t_allegato PRIMARY KEY (id_allegato);