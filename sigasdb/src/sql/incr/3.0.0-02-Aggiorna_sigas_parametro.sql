--PARAMETRO APPLICATIVO
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'rest_piemometpay_base_url', 'http://tu-exp-srv-pay-fe.bilancio.csi.it/epayapi/api/v1', null, null, null, now(), null);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'rest_user_basic_auth', 'epayapi_020', null, null, null, now(), null);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'rest_pwd_basic_auth', '96wVeDM8', null, null, null, now(), null);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'rest_url_create_debt_position', '/organizations/{codiceFiscaleEnteCreditore}/paymenttypes/{epay-service-codice-versamento}/debtpositions', null, null, null, now(), null);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'rest_url_paymentnotice', '/organizations/{codiceFiscaleEnteCreditore}/paymenttypes/{epay-service-codice-versamento}/debtpositions/{iuv}/paymentnotice', null, null, null, now(), null);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'rest_url_payment', '/organizations/{codiceFiscaleEnteCreditore}/debtpositions/{iuv}/payment', null, null, null, now(), null);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'rest_ente_beneficiario', 'Regione Piemonte', null, null, null, now(), null);

--[UPDATE] PARAMETRO APPLICATIVO
UPDATE sigas_c_parametro SET valore_string = 'QZ02'
WHERE id_parametro in (SELECT id_parametro from sigas_c_parametro WHERE desc_parametro LIKE 'epay_service_codice_versamento');

UPDATE sigas_c_parametro SET valore_string = 'http://dev-portale.ruparpiemonte.it/sigas/#/home/selezione-soggetto-pagamento'
WHERE id_parametro in (SELECT id_parametro from sigas_c_parametro WHERE desc_parametro LIKE 'epay_service_callback');

UPDATE sigas_c_parametro SET valore_string = 'EPAY_SIGAS_REGIONE'
WHERE id_parametro in (SELECT id_parametro from sigas_c_parametro WHERE desc_parametro LIKE 'epay_service_endpoint_redirect_callid');

UPDATE sigas_c_parametro SET valore_string = 'VERfJCRzaTFTdGEmQ29tJmQ0dXR1bm4wJiY='
WHERE id_parametro in (SELECT id_parametro from sigas_c_parametro WHERE desc_parametro LIKE 'epay_service_endpoint_redirect_call_pass'); 
