alter table csi_log_audit drop constraint pk_csi_log_audit;
ALTER TABLE csi_log_audit ADD COLUMN id_log SERIAL;
ALTER TABLE csi_log_audit ADD CONSTRAINT pk_csi_log_audit PRIMARY KEY (id_log);
GRANT SELECT,UPDATE ON ALL SEQUENCES IN SCHEMA sigas TO sigas_rw;