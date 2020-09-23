CREATE TABLE sigas_import_utf (
	id_import bigserial NOT NULL,
	user_id varchar(50) NOT NULL,
	import_date timestamp NOT NULL,
	esito int4 NOT NULL,
	annualita varchar(10) NULL DEFAULT NULL::character varying,
	errore varchar(255) NULL
);

CREATE TABLE sigas_provincia (
	id_provincia numeric(7) NOT NULL,
	cod_istat_provincia varchar(3) NOT NULL,
	denom_provincia varchar(100) NOT NULL,
	sigla_provincia varchar(2) NULL,
	inizio_validita date NOT NULL,
	fine_validita date NULL
);

CREATE TABLE sigas_tipo_allarmi (
	id_tipo_allarme int4 NOT NULL,
	denominazione varchar(50) NULL,
	descrizione varchar(255) NULL
);

CREATE TABLE sigas_tipo_comunicazioni (
	id_tipo_comunicazione int4 NOT NULL,
	denominazione varchar(200) NULL,
	descrizione varchar(255) NULL
);

CREATE TABLE sigas_tipo_consumo (
	id_tipo_consumo int4 NOT NULL,
	campo_dich_consumo varchar NULL,
	descrizione varchar(255) NULL
);

CREATE TABLE sigas_tipo_versamento (
	id_tipo_versamento int4 NOT NULL,
	denominazione varchar(50) NULL,
	descrizione varchar(255) NULL
);

CREATE TABLE sigas_validazione (
	id_valid int4 NOT NULL,
	codice_azienda varchar NULL,
	anno varchar NULL,
	stato varchar NULL
);

CREATE TABLE sigas_comune (
	id_comune numeric(8) NOT NULL,
	cod_istat_comune varchar(6) NOT NULL,
	cod_belfiore_comune varchar(4) NOT NULL,
	denom_comune varchar(100) NOT NULL,
	fk_provincia numeric(7) NOT NULL,
	cap varchar(5) NULL,
	inizio_validita date NOT NULL,
	fine_validita date NULL
);

CREATE TABLE sigas_frontespizio_utf (
	id_frontespizio int4 NOT NULL,
	id_import int4 NOT NULL,
	data_estrazione timestamp NOT NULL,
	codice_ditta varchar(150) NOT NULL,
	denominazione varchar(150) NOT NULL,
	provincia_sede varchar(150) NOT NULL,
	comune_sede varchar(150) NOT NULL,
	indirizzo_sede varchar(255) NOT NULL,
	tipo_soggetto varchar(2) NOT NULL,
	anno varchar(10) NOT NULL,
	data_presentazione timestamp NOT NULL,
	stato_dichiarazione varchar(2) NOT NULL,
	provincia varchar(50) NOT NULL
);

CREATE TABLE sigas_quadro_f_utf (
	id_quadro_f int4 NOT NULL,
	id_import int4 NOT NULL,
	data_estrazione timestamp NOT NULL,
	codice_ditta varchar(50) NOT NULL,
	anno varchar(50) NOT NULL,
	provincia varchar(50) NOT NULL,
	quadro varchar(50) NOT NULL,
	fascia_clima varchar(50) NOT NULL,
	prog_rigo varchar(50) NOT NULL,
	utenze varchar(50) NOT NULL,
	utenze_mc int4 NOT NULL
);

CREATE TABLE sigas_quadro_g_utf (
	id_quadro_g int4 NOT NULL,
	id_import int4 NOT NULL,
	data_estrazione timestamp NOT NULL,
	codice_ditta varchar(50) NOT NULL,
	anno varchar(50) NOT NULL,
	provincia varchar(50) NOT NULL,
	quadro varchar(50) NOT NULL,
	prog_rigo varchar(50) NOT NULL,
	utenze varchar(50) NOT NULL,
	utenze_mc int4 NOT NULL
);

CREATE TABLE sigas_quadro_h_utf (
	id_quadro_h int4 NOT NULL,
	id_import int4 NOT NULL,
	data_estrazione timestamp NOT NULL,
	codice_ditta varchar(50) NOT NULL,
	anno varchar(50) NOT NULL,
	provincia varchar(50) NOT NULL,
	quadro varchar(50) NOT NULL,
	prog_rigo varchar(50) NOT NULL,
	ubicazione varchar(2) NULL,
	segno_importo varchar(10) NULL,
	metri_cubi int4 NULL,
	aliquota numeric(20,10) NULL,
	totale_rigo numeric(20,10) NOT NULL,
	importo numeric(20,2) NULL
);

CREATE TABLE sigas_quadro_i_utf (
	id_quadro_i int4 NOT NULL,
	id_import int4 NOT NULL,
	data_estrazione timestamp NOT NULL,
	codice_ditta varchar(50) NOT NULL,
	anno varchar(50) NOT NULL,
	provincia varchar(50) NOT NULL,
	quadro varchar(50) NOT NULL,
	prog_rigo varchar(50) NOT NULL,
	consumi int4 NULL,
	aliquota numeric(20,10) NULL,
	imposta numeric(20,4) NULL
);

CREATE TABLE sigas_quadro_m_utf (
	id_quadro_m int4 NOT NULL,
	id_import int4 NOT NULL,
	data_estrazione timestamp NOT NULL,
	codice_ditta varchar(50) NOT NULL,
	anno varchar(50) NOT NULL,
	provincia varchar(50) NOT NULL,
	quadro varchar(50) NOT NULL,
	prog_rigo varchar(50) NOT NULL,
	fascia_climatica varchar(50) NOT NULL,
	consumi int4 NULL,
	aliquota numeric(30,10) NULL,
	imposta numeric(30,10) NULL
);

CREATE TABLE sigas_quadro_n_utf (
	id_quadro_n int4 NOT NULL,
	id_import int4 NOT NULL,
	data_estrazione timestamp NOT NULL,
	codice_ditta varchar(50) NOT NULL,
	anno varchar(50) NOT NULL,
	provincia varchar(50) NOT NULL,
	quadro varchar(50) NOT NULL,
	prog_rigo varchar(50) NOT NULL,
	imposta numeric(30,10) NULL
);

CREATE TABLE sigas_tipo_aliquote (
	id_tipo_aliquota int4 NOT NULL,
	nome_aliquota varchar(50) NULL,
	descrizione varchar(50) NULL,
	nuovo_allacciamento bool NULL,
	id_tipo_consumo int4 NULL,
	tipo varchar(50) NULL
);

CREATE TABLE sigas_aliquote (
	id_aliquota int4 NOT NULL,
	id_tipo_aliquota int4 NULL,
	aliquota numeric(12,8) NULL,
	validita_start timestamp NULL,
	validita_end timestamp NULL,
	prog_rigo varchar NULL,
	version int4 NULL
);

CREATE TABLE sigas_anagrafica_soggetti (
	id_anag int4 NOT NULL,
	id_fusione int4 NULL,
	data_fusione timestamp NULL,
	codice_azienda varchar(150) NOT NULL,
	denominazione varchar(150) NOT NULL,
	indirizzo varchar(255) NOT NULL,
	iban varchar(50) NULL,
	tipo varchar(2) NULL,
	status int4 NULL,
	telefono varchar(50) NULL,
	riferimento varchar(50) NULL,
	note varchar(255) NULL,
	fideussione bool NULL DEFAULT false,
	importo_fideussione numeric(30,10) NULL DEFAULT 0,
	email varchar(150) NULL,
	pec varchar(150) NULL,
	fk_provincia numeric(7) NULL,
	fk_comune numeric(7) NULL,
	version int4 NULL
);

CREATE TABLE sigas_dich_consumi (
	id_consumi int4 NOT NULL,
	id_anag int4 NOT NULL,
	id_import int4 NOT NULL,
	annualita varchar(10) NOT NULL,
	provincia_erogazione varchar(4) NULL,
	data_presentazione timestamp NULL,
	stato_dich varchar(1) NULL,
	usi_industriali_1200 int4 NULL,
	usi_industriali_up int4 NULL,
	usi_civili_120 int4 NULL,
	usi_civili_480 int4 NULL,
	usi_civili_1560 int4 NULL,
	usi_civili_up int4 NULL,
	tot_nuovi_allacciamenti numeric(30,10) NULL,
	tot_industriali numeric(30,10) NULL,
	tot_civili numeric(30,10) NULL,
	rettifiche numeric(30,10) NULL,
	arrotondamenti numeric(30,10) NULL,
	conguaglio_dich numeric(30,10) NULL,
	conguaglio_calcolato numeric(30,10) NULL,
	rateo_dich numeric(30,10) NULL,
	totale_dich numeric(30,10) NULL,
	totale_calcolato numeric(30,10) NULL,
	mod_id_consumi int4 NULL DEFAULT 0,
	mod_user varchar(50) NULL,
	mod_date timestamp NULL,
	note text NULL,
	addizionale_liquidata numeric(30,10) NULL,
	compensazione numeric(30,10) NULL
);

CREATE TABLE sigas_dich_scarti (
	id_scarti int4 NOT NULL,
	id_consumi int4 NULL,
	provincia varchar(4) NULL,
	consumi int4 NULL,
	aliquota numeric(9,8) NULL,
	imposta numeric(10,2) NULL,
	conciliato bool NULL
);

CREATE TABLE sigas_dich_versamenti (
	id_versamento int4 NOT NULL,
	data_versamento timestamp NULL,
	id_anag int4 NULL,
	importo numeric(30,10) NULL,
	id_consumi int4 NULL,
	iban_vers varchar(50) NULL,
	mod_user varchar(50) NULL,
	mod_date timestamp NULL,
	mese varchar(50) NULL,
	tipologia varchar(50) NULL,
	id_tipo_versamento int4 NULL,
	annualita varchar(10) NULL,
	fk_provincia numeric(7) NOT NULL,
	note varchar(255) NULL,
	data_accertamento timestamp NULL,
	importo_complessivo numeric(30,10) NULL
);

CREATE TABLE sigas_ana_comunicazioni (
	id_comunicazione serial NOT NULL,
	descrizione varchar(300) NULL,
	id_tipo_comunicazione int4 NULL,
	data_documento timestamp NULL,
	id_anag int4 NULL,
	annualita varchar(10) NULL,
	note text NULL,
	n_protocollo varchar(50) NULL,
	rif_archivio varchar(300) NULL,
	dati_riassuntivi varchar(300) NULL
); 

CREATE TABLE sigas_allarmi (
	id_allarme int4 NOT NULL,
	id_consumi int4 NULL,
	id_comunicazione int4 NULL,
	id_tipo_allarme int4 NOT NULL,
	nota text NULL,
	attivazione timestamp NOT NULL,
	utente varchar(50) NULL,
	status int4 NULL,
	codice_azienda varchar(50) NULL DEFAULT NULL::character varying,
	annualita varchar(10) NOT NULL,
	id_versamento int4 NULL
);

CREATE TABLE sigas_tipo_tassi (
	id_tipo_tasso int4 NOT NULL,
	nome_tasso varchar(50) NULL,
	descrizione varchar(50) NULL
);

CREATE TABLE sigas_tassi (
	id_tasso int4 NOT NULL,
	id_tipo_tasso int4 NULL,
	tasso numeric(12,8) NULL,
	validita_start timestamp NULL,
	validita_end timestamp NULL,
	version int4 NULL
);

CREATE TABLE sigas_report (
	id_report numeric(2) NOT NULL,
	cod_report varchar(50) NOT NULL,
	desc_report varchar(200) NOT NULL,
	jrxml xml NOT NULL,
	jasper bytea NULL,
	data_insert timestamptz NOT NULL DEFAULT now(),
	data_update timestamptz NULL
);

CREATE TABLE sigas_rimborso (
	id_rimborso int4 NOT NULL,
	id_anag int4 NOT NULL,
	annualita varchar(50) NOT NULL,
	data_istanza timestamptz NOT NULL,
	data_rimborso timestamptz NULL,
	importo numeric(30,10) NULL, 
	importo_rimborsato numeric(30,10) NULL, 
	stato_pratica varchar(20) NOT NULL,
	version int4 NULL,
	fk_comunicazioni int4 NULL
);