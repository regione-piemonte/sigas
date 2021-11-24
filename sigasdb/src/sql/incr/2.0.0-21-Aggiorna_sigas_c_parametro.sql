INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'epay_service_endpoint_url', 'https://tu-exp-srv-paywso-sistemapiemonte.bilancio.csi.it/wso001/services/Enti2EPaywsoSecureProxy', NULL, NULL, NULL, '2021-01-28', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'epay_service_endpoint_uid', 'SIGAS-TEST', NULL, NULL, NULL, '2021-01-28', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'epay_service_endpoint_pwd', 'NeE3PSgkdQb3s8En', NULL, NULL, NULL, '2021-01-28', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'epay_service_codice_versamento', 'QZ00', NULL, NULL, NULL, '2021-01-28', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'epay_service_redirect', 'https://pay.sistemapiemonte.it/epayweb/accessoLibero/pagaConIuv/accessoChiamanteEsterno', NULL, NULL, NULL, '2021-01-28', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'epay_service_callback', 'https://dev-portale.ruparpiemonte.it/sigas/home/ppayCallback', NULL, NULL, NULL, '2021-01-28', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'epay_service_endpoint_redirect_callid', 'engsigasppaytest', NULL, NULL, NULL, '2021-01-28', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'epay_service_endpoint_redirect_call_pass', 'ppayTSqkdQb5sYEf', NULL, NULL, NULL, '2021-01-28', NULL);
