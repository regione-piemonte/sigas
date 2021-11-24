ALTER TABLE sigas_tipo_pagamento 
	ADD CONSTRAINT pk_d_tipo_pagamento PRIMARY KEY (id_tipo_pagamento);
	
ALTER TABLE sigas_modalita_pagamento 
	ADD CONSTRAINT pk_d_modalita_pagamento PRIMARY KEY (id_modalita_pagamento);
	
ALTER TABLE sigas_carrello_stato 
	ADD CONSTRAINT pk_stato_carrello PRIMARY KEY (id_stato_carrello);
	
ALTER TABLE sigas_mesi 
	ADD CONSTRAINT pk_d_mese PRIMARY KEY (id);
	
ALTER TABLE sigas_carrello_pagamenti	
	ADD CONSTRAINT fk_carrello_00 PRIMARY KEY (id_carrello);
	
ALTER TABLE sigas_carrello_rt	
	ADD CONSTRAINT fk_carrello_rt_00 PRIMARY KEY (id_carrello_rt);
	
ALTER TABLE sigas_carrello_notifica	
	ADD CONSTRAINT fk_carrello_notifica_00 PRIMARY KEY (id_carrello_notifica);
	
ALTER TABLE sigas_tipo_carrello 
	ADD CONSTRAINT pk_d_tipo_carrello PRIMARY KEY (id_tipo_carrello);

	
	----tabelle accreditamento------
ALTER TABLE sigas_dichiarante	
	ADD CONSTRAINT pk_t_dichiarante PRIMARY KEY (id_dichiarante);
	
ALTER TABLE sigas_operatore
	ADD CONSTRAINT pk_t_operatore PRIMARY KEY (id_operatore);
	
ALTER TABLE sigas_legale_rappresent
	ADD CONSTRAINT pk_t_legale_rappresent PRIMARY KEY (id_legale_rappresent);
	
ALTER TABLE sigas_ruolo
	ADD CONSTRAINT pk_tab_ruolo PRIMARY KEY (id_ruolo);
	
ALTER TABLE sigas_utente_provvisorio
	ADD CONSTRAINT pk_utente_provvisorio PRIMARY KEY (id_utente_provv);
	
ALTER TABLE sigas_utenti
	ADD CONSTRAINT pk_tab_utenti PRIMARY KEY (id_utente);
	
ALTER TABLE sigas_anagrafica_utente
	ADD CONSTRAINT pk_anagrafica_utente PRIMARY KEY (id_anag_utente);
	
ALTER TABLE sigas_c_messaggi
	ADD CONSTRAINT pk_c_messaggio PRIMARY KEY (id_messaggio);