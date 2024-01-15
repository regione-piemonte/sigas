INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'epay_service_endpoint_url_ws_security', 'https://tu-exp-srv-paywso-fe.bilancio.csi.it/wso001/services/Enti2EPaywsoSecureProxy', null, null, null, now(), null);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'ws_security_is_on', null, null, null, false, now(), null);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'ws_user', 'SIGAS-TEST', null, null, null, now(), null);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'ws_pwd', 'NeE3PSgkdQb3s8En', null, null, null, now(), null);