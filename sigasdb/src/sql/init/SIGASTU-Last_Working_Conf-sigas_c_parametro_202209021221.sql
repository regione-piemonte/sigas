INSERT INTO sigas_c_parametro (id_parametro,desc_parametro,valore_string,valore_number,valore_date,valore_boolean,inizio_validita,fine_validita) VALUES
	 (1,'codiceProdotto','SIGAS',NULL,NULL,NULL,'2020-04-20',NULL),
	 (2,'codiceLineaCliente','RP-01',NULL,NULL,NULL,'2020-04-20',NULL),
	 (3,'codiceAmbiente','TU',NULL,NULL,NULL,'2020-04-20',NULL),
	 (4,'codiceUnitaInstallazione','sigasbl',NULL,NULL,NULL,'2020-04-20',NULL),
	 (5,'urlTracciatoContabilia','/usr/skedtest/skedul/progetti/sigas-rp-01/dati/wrk/RPSGCB010_contabilia.csv',NULL,NULL,NULL,'2018-01-01','2021-12-31'),
	 (19,'descTipoAttoAmministrativo',NULL,52,NULL,NULL,'2018-01-01','2021-12-31'),
	 (21,'annoAttoAmministrativo',NULL,54,NULL,NULL,'2018-01-01','2021-12-31'),
	 (22,'numAttoAmministrativo',NULL,55,NULL,NULL,'2018-01-01','2021-12-31'),
	 (23,'oggettoAttoAmministrativo',NULL,56,NULL,NULL,'2018-01-01','2021-12-31'),
	 (24,'noteAttoAmministrativo',NULL,57,NULL,NULL,'2018-01-01','2021-12-31');
INSERT INTO sigas_c_parametro (id_parametro,desc_parametro,valore_string,valore_number,valore_date,valore_boolean,inizio_validita,fine_validita) VALUES
	 (25,'descPdcFinanziario4',NULL,67,NULL,NULL,'2018-01-01','2021-12-31'),
	 (6,'descOrdInc','',7,NULL,NULL,'2018-01-01','2021-12-31'),
	 (7,'annoOrdInc',NULL,5,NULL,NULL,'2018-01-01','2021-12-31'),
	 (26,'descPdcFinanziario5',NULL,69,NULL,NULL,'2018-01-01','2021-12-31'),
	 (8,'importoAttuale',NULL,109,NULL,NULL,'2018-01-01','2021-12-31'),
	 (9,'descSoggetto','ADDIZIONALE REGIONALE ALL''ACCISA SUL GAS NATURALE - VERSANTI VARI',28,NULL,NULL,'2018-01-01','2021-12-31'),
	 (27,'codCapitolo',NULL,102,NULL,NULL,'2018-01-01','2021-12-31'),
	 (10,'codPdcFinanziarioV','E.1.01.01.77.001',68,NULL,NULL,'2018-01-01','2021-12-31'),
	 (11,'dataEmissione',NULL,13,NULL,NULL,'2018-01-01','2021-12-31'),
	 (28,'descCapitolo',NULL,105,NULL,NULL,'2018-01-01','2021-12-31');
INSERT INTO sigas_c_parametro (id_parametro,desc_parametro,valore_string,valore_number,valore_date,valore_boolean,inizio_validita,fine_validita) VALUES
	 (12,'numOrdInc',NULL,6,NULL,NULL,'2018-01-01','2021-12-31'),
	 (13,'descStatoOrdInc',NULL,9,NULL,NULL,'2018-01-01','2021-12-31'),
	 (29,'importoIniziale',NULL,108,NULL,NULL,'2018-01-01','2021-12-31'),
	 (14,'descDistinta',NULL,25,NULL,NULL,'2018-01-01','2021-12-31'),
	 (15,'soggettoId',NULL,26,NULL,NULL,'2018-01-01','2021-12-31'),
	 (16,'codSoggetto',NULL,27,NULL,NULL,'2018-01-01','2021-12-31'),
	 (17,'cfSoggetto',NULL,29,NULL,NULL,'2018-01-01','2021-12-31'),
	 (18,'pIvaSoggetto',NULL,31,NULL,NULL,'2018-01-01','2021-12-31'),
	 (31,'firma',NULL,115,NULL,NULL,'2018-01-01','2021-12-31'),
	 (32,'dataElaborazione',NULL,116,NULL,NULL,'2018-01-01','2021-12-31');
INSERT INTO sigas_c_parametro (id_parametro,desc_parametro,valore_string,valore_number,valore_date,valore_boolean,inizio_validita,fine_validita) VALUES
	 (30,'dataFirma',NULL,114,NULL,NULL,'2018-01-01','2021-12-31'),
	 (61,'mailSmtpHost','mailfarm-app.csi.it',NULL,NULL,NULL,'2020-12-22',NULL),
	 (63,'epay_service_endpoint_url','https://tu-exp-srv-paywso-sistemapiemonte.bilancio.csi.it/wso001/services/Enti2EPaywsoSecureProxy',NULL,NULL,NULL,'2021-01-28',NULL),
	 (35,'irideAppId','SIGAS',NULL,NULL,NULL,'2020-02-11',NULL),
	 (36,'mittenteMailDomandaAccreditamento','sigastest_noreply@csi.it',NULL,NULL,NULL,'2020-11-17',NULL),
	 (37,'oggettoMailDomandaAccreditamento',' Registrazione domanda di accreditamento al sistema SIGAS',NULL,NULL,NULL,'2020-11-17',NULL),
	 (38,'corpoMailDomandaAccreditamento','Gentile <<NOME>> <<COGNOME>>
la sua richiesta è stata correttamente acquisita con codice <<CODICE_RICHIESTA>>. 
Al termine del processo di verifica riceverà una comunicazione. 
Cordiali saluti
L’informativa sul trattamento dei dati personali è consultabile al seguente link <<link>>
Questo indirizzo e-mail è dedicato esclusivamente all''invio della comunicazione sopra riportata. 
Si prega di non rispondere alla presente.',NULL,NULL,NULL,'2020-11-17',NULL),
	 (39,'mittenteMailValutazioneAccreditamento','sigastest_noreply@csi.it',NULL,NULL,NULL,'2020-11-17',NULL),
	 (40,'destinatarioMailValutazioneAccreditamento','cristiana.sabiacolucci@csi.it',NULL,NULL,NULL,'2020-11-17',NULL),
	 (41,'oggettoMailValutazioneAccreditamento','Richiesta valutazione nuova domanda di accreditamento al sistema SIGAS',NULL,NULL,NULL,'2020-11-17',NULL);
INSERT INTO sigas_c_parametro (id_parametro,desc_parametro,valore_string,valore_number,valore_date,valore_boolean,inizio_validita,fine_validita) VALUES
	 (42,'corpoMailValutazioneAccreditamento','Il soggetto
<<NOME>>
<<COGNOME>>
con codice fiscale <<CF_OPERATORE_FO>>
ha inviato la richiesta <<CODICE_RICHIESTA>> di accreditamento al sistema SIGAS FO per 
<<DENOMINAZIONE_SOC>> - <<CODICE_SOCIETA>>',NULL,NULL,NULL,'2020-11-17',NULL),
	 (43,'linkInformativaTrattamentoDatiPers','www.csi.it',NULL,NULL,NULL,'2020-11-17',NULL),
	 (47,'mittenteMailDiniegoAccreditamento','sigastest_noreply@csi.it',NULL,NULL,NULL,'2020-11-23',NULL),
	 (48,'oggettoMailDiniegoAccreditamento','Diniego Accreditamento a SIGAS',NULL,NULL,NULL,'2020-11-23',NULL),
	 (49,'corpoMailDiniegoAccreditamento','Gentile <<NOME>> <<COGNOME>>
la sua richiesta non è stata accolta.
<<NOTE>>
Cordiali saluti
L’informativa sul trattamento dei dati personali è consultabile al seguente link <<link>>
Questo indirizzo e-mail è dedicato esclusivamente all''invio della comunicazione sopra riportata. 
Si prega di non rispondere alla presente.',NULL,NULL,NULL,'2020-11-23',NULL),
	 (50,'mittenteMailRichiestaAbilitazione','sigastest_noreply@csi.it',NULL,NULL,NULL,'2020-11-23',NULL),
	 (51,'destinatarioMailRichiestaAbilitazione','assistenza.sigas@csi.it',NULL,NULL,NULL,'2020-11-23',NULL),
	 (52,'oggettoMailRichiestaAbilitazione','Richiesta di abilitazione utente a SIGAS FO',NULL,NULL,NULL,'2020-11-23',NULL),
	 (53,'corpoMailRichiestaAbilitazione','Con la presente si richiede di abilitare a SIGAS FO
<<NOME>> 
<<COGNOME>> 
CODICE FISCALE <<CF_OPERATORE_FO>> ',NULL,NULL,NULL,'2020-11-23',NULL),
	 (54,'linkServizioSigas','https://dev-secure.sistemapiemonte.it/',NULL,NULL,NULL,'2020-11-24',NULL);
INSERT INTO sigas_c_parametro (id_parametro,desc_parametro,valore_string,valore_number,valore_date,valore_boolean,inizio_validita,fine_validita) VALUES
	 (55,'codiceFiscaleEnteCreditore','80087670016',NULL,NULL,NULL,'2019-01-18',NULL),
	 (56,'oggettoPagamentoPpay','Pagamento SIGAS',NULL,NULL,NULL,'2019-01-18',NULL),
	 (58,'pagamentoPpaySigasUrl','http://applogic.consiglioregionale.piemonte.it/sigas',NULL,NULL,NULL,'2019-01-29',NULL),
	 (59,'operatoreFO','OP_SIGASFO_PAGA',NULL,NULL,NULL,'2020-11-28',NULL),
	 (60,'operatoreBO','OP_SIGASBO_ACCR, OPERATORE_SIGAS',NULL,NULL,NULL,'2020-11-28',NULL),
	 (44,'mittenteMailConfermaAccreditamento','sigastest_noreply@csi.it',NULL,NULL,NULL,'2020-11-23',NULL),
	 (34,'filtroDescrizioneAzienda','S.R.L., srl, energia, spa, S.P.A., RETE, GAS, SPA',NULL,NULL,NULL,'2020-10-28',NULL),
	 (64,'epay_service_endpoint_uid','SIGAS-TEST',NULL,NULL,NULL,'2021-01-28',NULL),
	 (65,'epay_service_endpoint_pwd','NeE3PSgkdQb3s8En',NULL,NULL,NULL,'2021-01-28',NULL),
	 (62,'mailSmtpPort','2599999999',NULL,NULL,NULL,'2020-12-22',NULL);
INSERT INTO sigas_c_parametro (id_parametro,desc_parametro,valore_string,valore_number,valore_date,valore_boolean,inizio_validita,fine_validita) VALUES
	 (69,'epay_service_endpoint_redirect_callid','EPAY_SIGAS_REGIONE',NULL,NULL,NULL,'2021-01-28',NULL),
	 (33,'intestazione',NULL,NULL,NULL,true,'2018-01-01','2021-12-31'),
	 (70,'epay_service_endpoint_redirect_call_pass','VERfJCRzaTFTdGEmQ29tJmQ0dXR1bm4wJiY=',NULL,NULL,NULL,'2021-01-28',NULL),
	 (71,'ACTA_SERVER','tst-applogic.reteunitaria.piemonte.it',NULL,NULL,NULL,'2021-01-28',NULL),
	 (66,'epay_service_codice_versamento','QZ02',NULL,NULL,NULL,'2021-01-28',NULL),
	 (67,'epay_service_redirect','https://tu-exp-pay-sistemapiemonte.bilancio.csi.it/epayweb/accessoLibero/pagaConIuv/accessoChiamanteEsterno',NULL,NULL,NULL,'2021-01-28',NULL),
	 (57,'pagamentoPpayMittenteMailConferma',NULL,NULL,NULL,NULL,'2020-12-22',NULL),
	 (68,'epay_service_callback','http://tst-portale.ruparpiemonte.it/sigas/#/home/selezione-soggetto-pagamento',NULL,NULL,NULL,'2021-01-28',NULL),
	 (72,'ACTA_CONTEXT','/actasrv/',NULL,NULL,NULL,'2021-01-28',NULL),
	 (73,'ACTA_PORT',NULL,80,NULL,NULL,'2021-01-28',NULL);
INSERT INTO sigas_c_parametro (id_parametro,desc_parametro,valore_string,valore_number,valore_date,valore_boolean,inizio_validita,fine_validita) VALUES
	 (74,'ACTA_IS_WS',NULL,NULL,NULL,true,'2021-01-28',NULL),
	 (75,'lunghezzaMinimaCodFiscalePIvaPpay',NULL,11,NULL,NULL,'2021-03-29',NULL),
	 (76,'tempoChiusuraCarrelliPending',NULL,0,NULL,NULL,'2021-03-29',NULL),
	 (45,'oggettoMailConfermaAccreditamento','Conferma Accreditamento a SIGAS',NULL,NULL,NULL,'2020-11-23',NULL),
	 (46,'corpoMailConfermaAccreditamento','Gentile <<NOME>> <<COGNOME>>
la sua richiesta è stata accolta.
Il Servizio SIGAS è disponibile al <<link_servizio_sigas>>.
Per accedere può usare le sue credenziali personali.
Cordiali saluti
L’informativa sul trattamento dei dati personali è consultabile al seguente link <<link>>
Questo indirizzo e-mail è dedicato esclusivamente all''invio della comunicazione sopra riportata. 
Si prega di non rispondere alla presente',NULL,NULL,NULL,'2020-11-23',NULL),
	 (77,'ACTA_SERVER_PROTOCOL','http://',NULL,NULL,NULL,'2021-01-28',NULL),
	 (78,'ACTA_ID_AOO','276',NULL,NULL,NULL,'2021-01-28',NULL),
	 (79,'ACTA_CF','SGSFTR80A01L219U',NULL,NULL,NULL,'2021-01-28',NULL),
	 (80,'ACTA_ID_STRUTTURA','1108',NULL,NULL,NULL,'2021-01-28',NULL),
	 (81,'ACTA_ID_NODO','1278',NULL,NULL,NULL,'2021-01-28',NULL);
INSERT INTO sigas_c_parametro (id_parametro,desc_parametro,valore_string,valore_number,valore_date,valore_boolean,inizio_validita,fine_validita) VALUES
	 (82,'ACTA_CODE_FRUITORE','SIGAS',NULL,NULL,NULL,'2021-01-28',NULL),
	 (83,'codiceClassificazione','4.70.10',NULL,NULL,NULL,'2021-01-28',NULL),
	 (86,'mailSettoreTributi','settore.tributi@regione.piemonte.it',NULL,NULL,NULL,'2021-01-28',NULL),
	 (87,'REPOSITORY_ACTA','RP201209 Regione Piemonte - Agg. 09/2012',NULL,NULL,NULL,'2021-01-28',NULL),
	 (88,'APPLICATION_KEY_ACTA','24/65/61/-67/-101/-61/-28/36/11/121/-3/-116/-108/-39/-112/-73',NULL,NULL,NULL,'2021-01-28',NULL),
	 (89,'ACTA_SIGN_STEP_BYPASS_FLAG','0001000',NULL,NULL,NULL,'2021-01-28',NULL),
	 (90,'DICHIARAZIONI_SERIE_FASCICOLI_CODICE','ARISGANWEBDICH',NULL,NULL,NULL,'2021-01-28',NULL),
	 (91,'RIMBORSI_SERIE_FASCICOLI_CODICE','ARISGANWEBRIMB',NULL,NULL,NULL,'2021-01-28',NULL),
	 (92,'COMUNICAZIONI_VARIE_SERIE_FASCICOLI_CODICE','ARISGANWEBCOMV',NULL,NULL,NULL,'2021-01-28',NULL),
	 (93,'DEPOSITI_CAUZIONALI_SERIE_FASCICOLI_CODICE','ARISGANWEBDEPCA',NULL,NULL,NULL,'2021-01-28',NULL);
INSERT INTO sigas_c_parametro (id_parametro,desc_parametro,valore_string,valore_number,valore_date,valore_boolean,inizio_validita,fine_validita) VALUES
	 (94,'ACCERTAMENTI_VARIE_SERIE_FASCICOLI_CODICE','ARISGANWEBACC',NULL,NULL,NULL,'2021-01-28',NULL),
	 (95,'ACTA_TARGETOBJECT','ClassificazioniProtocollateView',NULL,NULL,NULL,'2021-01-28',NULL),
	 (96,'ACTA_NAMESPACEURI','objectservice.acaris.acta.doqui.it',NULL,NULL,NULL,'2021-01-28',NULL),
	 (97,'ACTA_LOCALPART','ObjectService',NULL,NULL,NULL,'2021-01-28',NULL),
	 (98,'ACTA_ENDPOINTADDRESS','objectWS?wsdl',NULL,NULL,NULL,'2021-01-28',NULL),
	 (99,'ACTA_NAVIWSDLLOCATION','navigationWS?wsdl',NULL,NULL,NULL,'2021-01-28',NULL),
	 (100,'ACTA_RELAWSDLLOCATION','relationshipsWS?wsdl',NULL,NULL,NULL,'2021-01-28',NULL),
	 (101,'ACTA_CODICE','codice',NULL,NULL,NULL,'2021-01-28',NULL),
	 (102,'ACTA_ANNO','anno',NULL,NULL,NULL,'2021-01-28',NULL),
	 (103,'ACTA_IDAOOPROTOCOLLANTE','idAooProtocollante',NULL,NULL,NULL,'2021-01-28',NULL);
INSERT INTO sigas_c_parametro (id_parametro,desc_parametro,valore_string,valore_number,valore_date,valore_boolean,inizio_validita,fine_validita) VALUES
	 (104,'INDEX_BASEURL','http://tst-applogic.reteunitaria.piemonte.it/ecmenginews-exp02/',NULL,NULL,NULL,'2021-01-28',NULL),
	 (105,'INDEX_URLWS1','/services/EcmEngineManagement',NULL,NULL,NULL,'2021-01-28',NULL),
	 (106,'INDEX_FRUITORE','sigas',NULL,NULL,NULL,'2021-01-28',NULL),
	 (107,'INDEX_REPOSITORY','primary',NULL,NULL,NULL,'2021-01-28',NULL),
	 (108,'INDEX_USERNAME','admin@sigas',NULL,NULL,NULL,'2021-01-28',NULL),
	 (109,'INDEX_PASSWORD','sigas',NULL,NULL,NULL,'2021-01-28',NULL),
	 (110,'INDEX_UIDPADRE','ea4219d6-d8db-11eb-81d5-6790efe1e9c9',NULL,NULL,NULL,'2021-01-28',NULL),
	 (111,'INDEX_TYPEPREFIXEDNAME','sigas:documento',NULL,NULL,NULL,'2021-01-28',NULL),
	 (112,'INDEX_MODELPREFIXEDNAME','sigas:model',NULL,NULL,NULL,'2021-01-28',NULL),
	 (113,'INDEX_PARENTASSOCTYPEPREFIXEDNAME','cm:contains',NULL,NULL,NULL,'2021-01-28',NULL);
INSERT INTO sigas_c_parametro (id_parametro,desc_parametro,valore_string,valore_number,valore_date,valore_boolean,inizio_validita,fine_validita) VALUES
	 (114,'INDEX_CONTENTPROPERTYPREFIXEDNAME','cm:content',NULL,NULL,NULL,'2021-01-28',NULL),
	 (115,'cognomeDirigente','ZANELLA',NULL,NULL,NULL,'2021-01-28',NULL),
	 (116,'nomeDirigente','FABRIZIO',NULL,NULL,NULL,'2021-01-28',NULL);
