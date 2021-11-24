ALTER TYPE level_message ADD VALUE 'WARNING';
INSERT INTO sigas_c_messaggi
(id_messaggio, desc_chiave_messaggio, valore_messaggio, livello_messaggio)
VALUES((select max(id_messaggio)+1 from sigas_c_messaggi), 'msgLunghezzaMinimaCodFiscalePIvaPpay', 'La lunghezza minima del codice fiscale/P.iva è di 11 elementi', 'WARNING');
INSERT INTO sigas_c_messaggi
(id_messaggio, desc_chiave_messaggio, valore_messaggio, livello_messaggio)
VALUES((select max(id_messaggio)+1 from sigas_c_messaggi), 'carrelloNotificato', 'In attesa di ricezione esito pagamento', 'WARNING');