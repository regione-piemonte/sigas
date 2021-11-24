INSERT INTO sigas_stato_documento (id_stato_documento, descrizione, codice_stato) VALUES ((select max(id_stato_documento)+1 from sigas_stato_documento), 'Lettera di risposta', 'LETT_RISP');
