DROP TABLE IF EXISTS sigas_carrello_rt;
DROP TABLE IF EXISTS sigas_carrello_notifica;
DROP TABLE IF EXISTS sigas_carrello_pagamenti;
DROP TABLE IF EXISTS sigas_mesi;
DROP TABLE IF EXISTS sigas_carrello_stato;
DROP TABLE IF EXISTS sigas_modalita_pagamento;
DROP TABLE IF EXISTS sigas_tipo_pagamento;
DROP TABLE IF EXISTS sigas_tipo_carrello;

CREATE TABLE sigas_tipo_carrello (
	id_tipo_carrello int4 NOT NULL,
	denominazione varchar(50) NULL,
	descrizione varchar(255) NULL
);

CREATE TABLE sigas_tipo_pagamento (
	id_tipo_pagamento numeric(2) NOT NULL,
	cod_tipo_pagamento bpchar(1) NOT NULL,
	desc_tipo_pagamento varchar(50) NOT NULL
);

CREATE TABLE sigas_modalita_pagamento (
	id_modalita_pagamento numeric(2) NOT NULL,
	desc_modalita_pagamento varchar(50) NOT NULL
);

CREATE TABLE sigas_carrello_stato (
	id_stato_carrello numeric(2) NOT NULL,
	desc_stato_carrello varchar(25) NOT NULL
);
			
CREATE TABLE sigas_mesi (
	id numeric(2) NOT NULL,
	mese varchar(25) NOT NULL
);


CREATE TABLE sigas_carrello_pagamenti (
	id_carrello serial NOT NULL,
	codice_pagamento varchar(25) NOT NULL,
	email varchar(150) NULL,
	fk_modalita_pagamento int NULL,
	data_pagamento date NULL,
	data_versamento date NULL,
	fk_tipo_pagamento int NULL,
	fk_stato_carrello int NOT NULL DEFAULT 10,
	importo numeric(10,2) NOT NULL,
	denominazione_versante varchar NOT NULL,
	fk_utente_insert int NOT NULL,
	data_insert timestamptz NOT NULL DEFAULT now(),
	fk_utente_update numeric(4) NULL,
	data_update timestamptz NULL,
	version int NULL,
	mese int NOT NULL,
	anno varchar(4) NOT NULL,
	note VARCHAR NULL,
	fk_provincia int NOT NULL,
	sigla_provincia varchar(2) NULL,
	codice_azienda varchar NULL,
	iuv varchar NULL,
	id_posizione_debitoria varchar NULL,
	fk_anag_soggetto int NOT NULL,
	fk_tipo_carrello int NOT NULL,
	fk_dich_versamento int NULL
);


CREATE TABLE sigas_carrello_rt (
	id_carrello_rt serial NOT NULL,
	codice_pagamento varchar(25) NOT NULL,
	data_ricevuta date NULL,
	data_insert timestamptz NOT NULL DEFAULT now(),
	fk_utente_insert int NULL,
	fk_utente_update numeric(4) NULL,
	data_update timestamptz NULL,
	numero_rt int NULL,
	id_rt varchar NULL,
	xml bytea NULL
);



CREATE TABLE sigas_carrello_notifica (
	id_carrello_notifica serial NOT NULL,
	codice_pagamento varchar(25) NOT NULL,
	fk_utente_insert int NULL,
	fk_utente_update numeric(4) NULL,
	data_insert timestamptz NOT NULL DEFAULT now(),
	data_update timestamptz NULL,
	id_posizione_debitoria varchar NULL,
	anno_di_riferimento varchar NULL,
	iuv varchar NULL,
	importo_pagato varchar NULL,
	data_scadenza timestamptz NULL,
	descrizione_causale_versamento varchar NULL,
	data_esito_pagamento timestamptz NULL,
	soggetto_debitore varchar NULL,
	soggetto_versante varchar NULL,
	dati_transazione_psp varchar NULL,
	dati_specifici_riscossione varchar NULL,
	note varchar NULL,
	codice_avviso varchar NULL
);


drop table IF EXISTS sigas_utente_provvisorio;
drop table IF EXISTS sigas_legale_rappresent ;
drop table IF EXISTS sigas_operatore ;
drop table IF EXISTS sigas_dichiarante;
drop table IF EXISTS sigas_utenti;
drop table IF EXISTS sigas_anagrafica_utente ;
drop table IF EXISTS sigas_ruolo ;
drop table IF EXISTS sigas_c_messaggi ;
drop type IF exists level_message;

CREATE TABLE sigas_dichiarante (
	id_dichiarante numeric(6) NOT NULL,
	denom_dichiarante varchar(150) NOT NULL,
	codice_azienda varchar(150) NOT NULL,
	indirizzo_sele_legale_dichiar varchar(250) NULL,
	fk_comune_sede_legale_dichiar numeric(8) NULL,
	fk_provincia numeric(7) NULL,
	iban varchar(150) NULL,
	telefono_dichiarante varchar(25) NULL,
	email_dichiarante varchar(150)  NULL,
	pec_dichiarante varchar(150)  NULL,	
	note varchar(255) NULL,
	data_insert timestamptz NOT NULL DEFAULT now());





CREATE TABLE sigas_operatore (
	id_operatore numeric(6) NOT NULL,
	fk_dichiarante numeric(6) NOT NULL,
	cf_operatore varchar(16) NOT NULL,
	cognome_operatore varchar(100) NOT NULL,
	nome_operatore varchar(50) NOT NULL,
	telefono_operatore varchar(25) NOT NULL,
	email_operatore varchar(150) NOT NULL,
	data_insert timestamptz NOT NULL DEFAULT now(),
	data_update timestamptz NULL);




CREATE TABLE sigas_legale_rappresent (
	id_legale_rappresent numeric(6) NOT NULL,
	fk_dichiarante numeric(6) NOT NULL,
	cf_legale_rappresent varchar(16) NOT NULL,
	cognome_legale_rappresent varchar(100) NOT NULL,
	nome_legale_rappresent varchar(50) NOT NULL,
	telefono_legale_rappresent varchar(25) NOT NULL,
	email_legale_rappresent varchar(150) NOT NULL,
	data_insert timestamptz NOT NULL DEFAULT now(),
	fk_utente_update numeric(4) NULL,
	data_update timestamptz NULL);


CREATE TABLE sigas_ruolo (
	id_ruolo numeric(6) NOT NULL,
	descrizione varchar(50) NOT NULL);



CREATE TABLE sigas_utente_provvisorio (
	id_utente_provv numeric(6) NOT NULL,
	id_operatore numeric(6) NOT NULL,
	id_soggetto_provvisorio numeric(6) NOT NULL,
	id_legale_rapp numeric(6) NOT NULL,
	id_pratica varchar(50) NULL,
	note text NULL,
	stato varchar(50) NULL,
	ins_user varchar(50) NULL,
	ins_date timestamp NULL,
	mod_user varchar(50) NULL,
	mod_date timestamp NULL);



CREATE TABLE sigas_utenti (
	id_utente numeric(6) NOT NULL,
	codice_fiscale varchar(16) NOT NULL,
	ruolo numeric(6) NOT NULL,
	id_utente_provv numeric(6) NOT NULL
	);


CREATE TABLE sigas_anagrafica_utente (
	id_anag_utente numeric(6) NOT NULL,
	id_anag serial NOT NULL,
	id_utente_provv numeric(6) NOT NULL);


create type level_message as enum('SUCCESS', 'DANGER', 'INFO');

CREATE TABLE sigas_c_messaggi (
	id_messaggio numeric(4) NOT NULL,
	desc_chiave_messaggio varchar(200) NOT NULL,
	valore_messaggio varchar(5000) NULL,
	livello_messaggio level_message);