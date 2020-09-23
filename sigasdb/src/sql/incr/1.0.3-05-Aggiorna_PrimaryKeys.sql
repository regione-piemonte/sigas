alter table csi_log_audit add constraint pk_csi_log_audit primary key(key_oper,utente,data_ora);

alter table sigas_c_parametro add CONSTRAINT pk_c_parametro PRIMARY KEY (id_parametro);