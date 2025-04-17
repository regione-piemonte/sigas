INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'mittente_mail_deposito_cauzionale', 'assistenza.sigas@csi.it', null, null, null, now(), null);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'oggetto_mail_deposito_cauzionale', 'Conferma deposito cauzionale', null, null, null, now(), null);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'corpo_mail_deposito_cauzionale', 'Gentile <<DENOMINAZIONE>> la sua richiesta è stata accolta.', null, null, null, now(), null);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'oggetto_mail_rifiuto_deposito_cauzionale', 'Rifiuto deposito cauzionale', null, null, null, now(), null);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'corpo_mail_rifiuto_deposito_cauzionale', 'Gentile <<DENOMINAZIONE>> la sua richiesta è stata rifiutata a causa dei seguenti motivi: <<NOTE>>', null, null, null, now(), null);