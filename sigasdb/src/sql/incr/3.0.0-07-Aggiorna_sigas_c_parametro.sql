-- PAGAMENTO REST FULL
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'rest_url_check_debtposition_status', '/organizations/{codiceFiscaleEnteCreditore}/paymenttypes/{epay-service-codice-versamento}/debtpositions/{iuv}/status', null, null, null, now(), null);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'check_debtposition_status_limite_numero_giorni', null, 60, null, null, now(), null);