INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'mailSmtpHost', 'mailfarm-app.csi.it', NULL, NULL, NULL, '2020-12-22', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'mailSmtpPort', '25', NULL, NULL, NULL, '2020-12-22', NULL);