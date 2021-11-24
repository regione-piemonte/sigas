INSERT INTO sigas_c_messaggi (id_messaggio, desc_chiave_messaggio, valore_messaggio, livello_messaggio)
VALUES ((select max(id_messaggio)+1 from sigas_c_messaggi), 'confermaCreazioneLetteraRisp', 'Si desidera creare la lettera di risposta?', 'INFO');

INSERT INTO sigas_c_messaggi (id_messaggio, desc_chiave_messaggio, valore_messaggio, livello_messaggio)
VALUES ((select max(id_messaggio)+1 from sigas_c_messaggi), 'confermaIndietroProtLetteraRisp', 'Si desidera annullare la protocollazione della lettera di risposta?', 'INFO');

INSERT INTO sigas_c_messaggi (id_messaggio, desc_chiave_messaggio, valore_messaggio, livello_messaggio)
VALUES ((select max(id_messaggio)+1 from sigas_c_messaggi), 'confermaAggiornaStatoDoc', 'Si desidera aggiornare il documento?', 'INFO');