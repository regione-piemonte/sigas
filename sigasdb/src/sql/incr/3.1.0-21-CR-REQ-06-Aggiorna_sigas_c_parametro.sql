INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'epay_service_codice_dep_cau', 'QZ11', null, null, null, now(), null);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'oggettoPagamentoPpay_dep_cau', 'Deposito cauzionale gas naturale SIGAS', null, null, null, now(), null);