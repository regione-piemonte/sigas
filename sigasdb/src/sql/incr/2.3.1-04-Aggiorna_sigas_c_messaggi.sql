INSERT INTO sigas_c_messaggi (id_messaggio, desc_chiave_messaggio, valore_messaggio, livello_messaggio)
VALUES ((select max(id_messaggio)+1 from sigas_c_messaggi), 'sizeDenominazioneAnagrafica', 'Attenzione, il testo inserito nel campo ''Denominazione'' supera i 150 caratteri', 'INFO');

INSERT INTO sigas_c_messaggi (id_messaggio, desc_chiave_messaggio, valore_messaggio, livello_messaggio)
VALUES ((select max(id_messaggio)+1 from sigas_c_messaggi), 'sizeIndirizzoAnagrafica', 'Attenzione, il testo inserito nel campo ''Indirizzo'' supera i 255 caratteri', 'INFO');
