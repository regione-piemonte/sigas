drop table IF EXISTS sigas_allegato;

drop table IF EXISTS sigas_documenti;
drop table IF EXISTS sigas_stato_archiviazione;
drop table IF EXISTS sigas_stato_documento ;
drop table IF EXISTS sigas_tipo_documento;




CREATE TABLE sigas_stato_documento (
	id_stato_documento int4 NOT NULL,
	descrizione varchar(255) NULL,
	codice_stato varchar(255) NULL
);


CREATE TABLE sigas_tipo_documento (
	id_tipo_documento int4 NOT NULL,
	codice_tipo_documento varchar(200) NULL,
	descrizione varchar(255) NULL,
	id_tipo_documento_padre int4 NULL,
	desc_vital_record_code_type varchar(300) NULL
);



CREATE TABLE sigas_stato_archiviazione (
	id_stato_archiviazione int4 NOT NULL,
	descrizione varchar(255) NULL,
	codice_stato varchar(255) NULL
);

CREATE TABLE sigas_documenti (
	id_documento serial NOT NULL,
	id_tipo_documento int4 NULL,
	id_anag int4 NULL,
	annualita varchar(10) NULL,
	note text NULL,
	n_protocollo varchar(50) NULL,
	rif_archivio varchar(50) NULL,
	ins_user varchar(50) NULL,
	ins_date timestamp NULL,
	mod_user varchar(50) NULL,
	mod_date timestamp NULL,
	cf_piva varchar(16) NULL,
	id_stato int4 NULL,
	nome_file varchar(300) NULL,
	note_bo text NULL,
	data_protocollazione timestamp NULL,
	id_index varchar(50) NULL,	
	id_stato_archiviazione int4 NOT NULL,
	n_protocollo_accertamento varchar(50) NULL,
	anno_protocollo_accertamento varchar(10) NULL);






CREATE TABLE sigas_allegato (
	id_allegato int4 NOT NULL,
	nome_file varchar(300) NOT NULL,
	descrizione varchar(500) NOT NULL,
	id_index varchar(50) NULL,
	numero_protocollo varchar(50) NULL,
	data_ora_protocollo timestamptz NULL,
	ins_user varchar(50) NULL,
	ins_date timestamp NULL,
	id_allegato_documento int4 NULL,
	id_stato_archiviazione int4 NOT NULL
);