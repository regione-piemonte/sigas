INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'lunghezzaMinimaCodFiscalePIvaPpay', NULL, 11, NULL, NULL, '2021-03-29', NULL);