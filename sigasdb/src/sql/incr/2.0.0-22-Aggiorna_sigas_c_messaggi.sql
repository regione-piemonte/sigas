INSERT INTO sigas_c_messaggi (id_messaggio, desc_chiave_messaggio, valore_messaggio, livello_messaggio)
	VALUES(5, 'carrelloErroreEliminazioneVoceCarrello', 'Impossibile eliminare la voce dal carrello dei pagamenti. Si ricrda che il pagamento non deve essere stato iniziato per poter modificare il carrello', 'INFO');
INSERT INTO sigas_c_messaggi (id_messaggio, desc_chiave_messaggio, valore_messaggio, livello_messaggio)
	VALUES(6, 'carrelloAttesaPagamento', 'Il pagamento dovrebbe partire a breve. Nel caso l''attesa dovesse protrarsi a lungo, si consiglia di riprovare in seguito accedendo nuovamente al carrello dal menu ''Paga''.', 'INFO');
INSERT INTO sigas_c_messaggi (id_messaggio, desc_chiave_messaggio, valore_messaggio, livello_messaggio)
	VALUES(7, 'carrelloAttesaPagamentoRedirect', 'Il codice IUV per effettuare il pagamento è stato generato con successo. Attendere la redirezione al sito di pagamento. Nel caso l''attesa dovesse protrarsi a lungo, si consiglia di riprovare in seguito accedendo nuovamente al carrello dal menu ''Paga''.', 'INFO');
