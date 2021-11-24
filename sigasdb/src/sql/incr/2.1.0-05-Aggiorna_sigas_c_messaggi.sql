INSERT INTO sigas_c_messaggi
(id_messaggio, desc_chiave_messaggio, valore_messaggio, livello_messaggio)
VALUES((select max(id_messaggio)+1 from sigas_c_messaggi), 'msgVersamentoEsistente', 'Esiste già un versamento corrispondente ai parametri selezionati', 'DANGER');