INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro) , 'irideAppId', 'SIGAS', NULL, NULL, NULL, '2020-02-11', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'mittenteMailDomandaAccreditamento', 'sigastest_noreply@csi.it', NULL, NULL, NULL, '2020-11-17', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'oggettoMailDomandaAccreditamento', ' Registrazione domanda di accreditamento al sistema SIGAS', NULL, NULL, NULL, '2020-11-17', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'corpoMailDomandaAccreditamento', 'Gentile <<NOME>> <<COGNOME>>
la sua richiesta è stata correttamente acquisita con codice <<CODICE_RICHIESTA>>. 
Al termine del processo di verifica riceverà una comunicazione. 
Cordiali saluti
L’informativa sul trattamento dei dati personali è consultabile al seguente link <<link>>
Questo indirizzo e-mail è dedicato esclusivamente all''invio della comunicazione sopra riportata. 
Si prega di non rispondere alla presente.', NULL, NULL, NULL, '2020-11-17', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'mittenteMailValutazioneAccreditamento', 'sigastest_noreply@csi.it', NULL, NULL, NULL, '2020-11-17', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'destinatarioMailValutazioneAccreditamento', 'cristiana.sabiacolucci@csi.it', NULL, NULL, NULL, '2020-11-17', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'oggettoMailValutazioneAccreditamento', 'Richiesta valutazione nuova domanda di accreditamento al sistema SIGAS', NULL, NULL, NULL, '2020-11-17', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'corpoMailValutazioneAccreditamento', 'Il soggetto
<<NOME>>
<<COGNOME>>
con codice fiscale <<CF_OPERATORE_FO>>
ha inviato la richiesta <<CODICE_RICHIESTA>> di accreditamento al sistema SIGAS FO per 
<<DENOMINAZIONE_SOC>> - <<CODICE_SOCIETA>>', NULL, NULL, NULL, '2020-11-17', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'linkInformativaTrattamentoDatiPers', 'www.csi.it', NULL, NULL, NULL, '2020-11-17', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'mittenteMailConfermaAccreditamento', 'assistenza.sigas@csi.it', NULL, NULL, NULL, '2020-11-23', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'oggettoMailConfermaAccreditamento', 'Conferma Accreditamento a SIGAS', NULL, NULL, NULL, '2020-11-23', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'corpoMailConfermaAccreditamento', 'Gentile <<NOME>> <<COGNOME>>
la sua richiesta è stata accolta.
Il Servizio SIGAS è disponibile al <<link_servizio_sigas>>.
Per accedere può usare le sue credenziali personali.
Cordiali saluti
L’informativa sul trattamento dei dati personali è consultabile al seguente link <<link>>
Questo indirizzo e-mail è dedicato esclusivamente all''invio della comunicazione sopra riportata. 
Si prega di non rispondere alla presente', NULL, NULL, NULL, '2020-11-23', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'mittenteMailDiniegoAccreditamento', 'sigastest_noreply@csi.it', NULL, NULL, NULL, '2020-11-23', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'oggettoMailDiniegoAccreditamento', 'Diniego Accreditamento a SIGAS', NULL, NULL, NULL, '2020-11-23', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'corpoMailDiniegoAccreditamento', 'Gentile <<NOME>> <<COGNOME>>
la sua richiesta non è stata accolta.
<<NOTE>>
Cordiali saluti
L’informativa sul trattamento dei dati personali è consultabile al seguente link <<link>>
Questo indirizzo e-mail è dedicato esclusivamente all''invio della comunicazione sopra riportata. 
Si prega di non rispondere alla presente.', NULL, NULL, NULL, '2020-11-23', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'mittenteMailRichiestaAbilitazione', 'sigastest_noreply@csi.it', NULL, NULL, NULL, '2020-11-23', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'destinatarioMailRichiestaAbilitazione', 'assistenza.sigas@csi.it', NULL, NULL, NULL, '2020-11-23', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'oggettoMailRichiestaAbilitazione', 'Richiesta di abilitazione utente a SIGAS FO', NULL, NULL, NULL, '2020-11-23', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'corpoMailRichiestaAbilitazione', 'Con la presente si richiede di abilitare a SIGAS FO
<<NOME>> 
<<COGNOME>> 
CODICE FISCALE <<CF_OPERATORE_FO>> ', NULL, NULL, NULL, '2020-11-23', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'linkServizioSigas', 'https://dev-secure.sistemapiemonte.it/', NULL, NULL, NULL, '2020-11-24', NULL);

INSERT INTO sigas_c_parametro (id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita) VALUES ((select max(id_parametro)+1 from sigas_c_parametro), 'codiceFiscaleEnteCreditore', '80087670016', NULL, NULL, NULL, '2019-01-18', NULL);
INSERT INTO sigas_c_parametro (id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita) VALUES ((select max(id_parametro)+1 from sigas_c_parametro), 'oggettoPagamentoPpay', 'Pagamento SIGAS', NULL, NULL, NULL, '2019-01-18', NULL);
INSERT INTO sigas_c_parametro (id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita) VALUES ((select max(id_parametro)+1 from sigas_c_parametro), 'pagamentoPpayMittenteMailConferma', 'assistenza.sigas@csi.it', NULL, NULL, NULL, '2019-01-29', NULL);
INSERT INTO sigas_c_parametro (id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita) VALUES ((select max(id_parametro)+1 from sigas_c_parametro), 'pagamentoPpaySigasUrl', 'http://applogic.consiglioregionale.piemonte.it/sigas', NULL, NULL, NULL, '2019-01-29', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'operatoreFO', 'OP_SIGASFO_PAGA', NULL, NULL, NULL, '2020-11-28', NULL);
INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'operatoreBO', 'OP_SIGASBO_ACCR, OPERATORE_SIGAS', NULL, NULL, NULL, '2020-11-28', NULL);

