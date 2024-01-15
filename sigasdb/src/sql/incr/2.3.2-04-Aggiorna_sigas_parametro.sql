INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'nuovo_versamento_anno_riferimento', null, null, null, null, now(), null);