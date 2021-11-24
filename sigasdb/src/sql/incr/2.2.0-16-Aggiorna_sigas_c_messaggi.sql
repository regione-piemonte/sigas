INSERT INTO sigas_c_messaggi (id_messaggio, desc_chiave_messaggio, valore_messaggio, livello_messaggio) 
VALUES ((select max(id_messaggio)+1 from sigas_c_messaggi), 'docGenericException', 'Si è verificato un errore durante il caricamento della documentazione', 'INFO');

INSERT INTO sigas_c_messaggi (id_messaggio, desc_chiave_messaggio, valore_messaggio, livello_messaggio) 
VALUES ((select max(id_messaggio)+1 from sigas_c_messaggi), 'docDimMax', 'L''allegato supera le dimensioni massime consentite', 'INFO');

INSERT INTO sigas_c_messaggi (id_messaggio, desc_chiave_messaggio, valore_messaggio, livello_messaggio) 
VALUES ((select max(id_messaggio)+1 from sigas_c_messaggi), 'docNumMaxAllegati', 'Il numero di allegati inseriti è superiore al massimo consentito di 20 allegati', 'INFO');

INSERT INTO sigas_c_messaggi (id_messaggio, desc_chiave_messaggio, valore_messaggio, livello_messaggio) 
VALUES ((select max(id_messaggio)+1 from sigas_c_messaggi), 'docDescAllegati', 'La descrizione per gli allegati è obbligatoria', 'INFO');

INSERT INTO sigas_c_messaggi (id_messaggio, desc_chiave_messaggio, valore_messaggio, livello_messaggio) 
VALUES ((select max(id_messaggio)+1 from sigas_c_messaggi), 'docSignException', 'Il documento non ha superato i controlli di integrità e validità della firma', 'INFO');