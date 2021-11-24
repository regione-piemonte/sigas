ALTER TABLE sigas_carrello_pagamenti ADD CONSTRAINT fk_carrello_01 FOREIGN KEY (fk_modalita_pagamento) REFERENCES sigas_modalita_pagamento(id_modalita_pagamento) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE sigas_carrello_pagamenti ADD CONSTRAINT fk_carrello_02 FOREIGN KEY (fk_tipo_pagamento) REFERENCES sigas_tipo_pagamento(id_tipo_pagamento) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE sigas_carrello_pagamenti ADD CONSTRAINT fk_carrello_03 FOREIGN KEY (fk_stato_carrello) REFERENCES sigas_carrello_stato(id_stato_carrello) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE sigas_carrello_pagamenti ADD CONSTRAINT fk_carrello_06 FOREIGN KEY (fk_utente_insert) REFERENCES sigas_utenti(id_utente) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE sigas_carrello_pagamenti ADD CONSTRAINT fk_carrello_07 FOREIGN KEY (fk_utente_update) REFERENCES sigas_utenti(id_utente) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE sigas_carrello_pagamenti ADD CONSTRAINT fk_carrello_09 FOREIGN KEY (fk_provincia) REFERENCES sigas_provincia(id_provincia) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE sigas_carrello_pagamenti ADD CONSTRAINT fk_carrello_10 FOREIGN KEY (fk_tipo_carrello) REFERENCES sigas_tipo_carrello(id_tipo_carrello) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE sigas_carrello_pagamenti ADD CONSTRAINT fk_carrello_11 FOREIGN KEY (fk_anag_soggetto) REFERENCES sigas_anagrafica_soggetti(id_anag) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE sigas_carrello_pagamenti ADD CONSTRAINT fk_carrello_08 FOREIGN KEY (fk_dich_versamento) REFERENCES sigas_dich_versamenti(id_versamento) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE sigas_carrello_rt ADD CONSTRAINT fk_carrello_rt_06 FOREIGN KEY (fk_utente_insert) REFERENCES sigas_utenti(id_utente) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE sigas_carrello_rt ADD CONSTRAINT fk_carrello_rt_07 FOREIGN KEY (fk_utente_update) REFERENCES sigas_utenti(id_utente) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE sigas_carrello_notifica ADD CONSTRAINT fk_carrello_notifica_06 FOREIGN KEY (fk_utente_insert) REFERENCES sigas_utenti(id_utente) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE sigas_carrello_notifica ADD CONSTRAINT fk_carrello_notifica_07 FOREIGN KEY (fk_utente_update) REFERENCES sigas_utenti(id_utente) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;


ALTER TABLE sigas_dichiarante 
	ADD CONSTRAINT fk_t_dichiarante_01 FOREIGN KEY (fk_comune_sede_legale_dichiar) 
	REFERENCES sigas_comune(id_comune) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;
		
ALTER TABLE sigas_dichiarante 
	ADD CONSTRAINT fk_sigas_dichiarante_sigas_provincia FOREIGN KEY (fk_provincia)
	REFERENCES sigas_provincia(id_provincia) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;
		
		
ALTER TABLE sigas_operatore 
	ADD CONSTRAINT fk_t_operatore_01 FOREIGN KEY (fk_dichiarante) 
	REFERENCES sigas_dichiarante(id_dichiarante) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;
		
		
ALTER TABLE sigas_legale_rappresent 
	ADD CONSTRAINT fk_t_legale_rappresent_01 FOREIGN KEY (fk_dichiarante) 
	REFERENCES sigas_dichiarante(id_dichiarante) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;
		
		
ALTER TABLE sigas_utente_provvisorio 
	ADD CONSTRAINT fk_sigas_utente_provvisorio_sigas_dichiarante FOREIGN KEY (id_soggetto_provvisorio)
	REFERENCES sigas_dichiarante(id_dichiarante)MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;
		
		
ALTER TABLE sigas_utente_provvisorio 
	ADD CONSTRAINT fk_sigas_utente_provvisorio_sigas_legale_rappresent FOREIGN KEY (id_legale_rapp)
	REFERENCES sigas_legale_rappresent(id_legale_rappresent) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;
		
		
ALTER TABLE sigas_utente_provvisorio 
	ADD CONSTRAINT fk_sigas_utente_provvisorio_sigas_operatore FOREIGN KEY (id_operatore) 
	REFERENCES sigas_operatore(id_operatore) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;
		
ALTER TABLE sigas_utenti 
	ADD CONSTRAINT fk_sigas_utentusigas_ruolo FOREIGN KEY (ruolo)
	REFERENCES sigas_ruolo(id_ruolo) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;