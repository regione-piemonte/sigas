CREATE TABLE csi_log_audit (
	data_ora timestamptz NULL,
	id_app varchar(200) NULL,
	id_address varchar(50) NULL,
	utente varchar(16) NULL,
	operazione varchar(200) NULL,
	ogg_oper varchar(100) NULL,
	key_oper varchar(50) NULL
);

CREATE TABLE sigas_c_parametro (
	id_parametro numeric(4) NOT NULL,
	desc_parametro varchar(200) NOT NULL,
	valore_string varchar(1000) NULL,
	valore_number numeric NULL,
	valore_date date NULL,
	valore_boolean bool NULL,
	inizio_validita date NOT NULL DEFAULT 'now'::text::date,
	fine_validita date NULL
);