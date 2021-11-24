INSERT INTO sigas_c_messaggi
(id_messaggio, desc_chiave_messaggio, valore_messaggio, livello_messaggio)
VALUES(1, 'utenteFoAttesaRegistrazioneIride', 'La sua pratica di accreditamento è stata validata al fine di consentire ai nostri sistemi l''acquisizione del vostro profilo è necessario attendere alcuni giorni.', 'INFO');
INSERT INTO sigas_c_messaggi
(id_messaggio, desc_chiave_messaggio, valore_messaggio, livello_messaggio)
VALUES(2, 'richiestaAccreditamentoGiaInserita', 'La richiesta di accreditamento per la socità scelta è gia stata inserita.', 'DANGER');
INSERT INTO sigas_c_messaggi (id_messaggio, desc_chiave_messaggio, valore_messaggio, livello_messaggio)
VALUES(3, 'mailConfermaPagamento', 'Gentile contribuente,

Le confermiamo l''avvenuto pagamento, mediante il sistema SIGAS, dell''imposta Regionale sull''accisa sul Gas Naturale relativa al mese @MESE@ anno @ANNO@ di Competenza per il dichiarante i cui estremi sono di seguito riportati:

Denominazione dichiarante: @DICHIARANTE@
Partita IVA: @PIVA@
Codice fiscale: @CODICEFISCALE@

I riferimenti del pagamento sono:
Identificativo Transazione: @IDTRANSAZIONE@
IUV: @IUV@

Lo IUV è l''Identificativo Univoco di Versamento ed è necessario per ogni futura richiesta di informazioni in merito al pagamento stesso.
La ricevuta telematica attestante l''avvenuto pagamento sarà resa disponibile dalla funzionalità ''Stampa ricevuta'' del SIGAS, collegandosi all''indirizzo
@URLSIGAS@ e specificando lo IUV (Identificativo Univoco di Versamento).

Cordiali Saluti,
Servizio SIGAS', 'INFO');

INSERT INTO sigas_c_messaggi (id_messaggio, desc_chiave_messaggio, valore_messaggio, livello_messaggio)
VALUES(4, 'mailOggettoConfermaPagamento', 'Conferma pagamento SIGAS', 'INFO');
