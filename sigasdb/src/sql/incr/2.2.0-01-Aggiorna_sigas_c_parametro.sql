INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'ACTA_SERVER_PROTOCOL', 'http://', NULL, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'ACTA_ID_AOO', '276', NULL, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'ACTA_CF', 'SGSFTR80A01L219U', NULL, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'ACTA_ID_STRUTTURA', '1108', NULL, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'ACTA_ID_NODO', '1278', NULL, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'ACTA_CODE_FRUITORE', 'SIGAS', NULL, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'codiceClassificazione', '4.70.10', NULL, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'cognomeDirigente', 'ZANELLA', NULL, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'nomeDirigente', 'FABRIZIO', NULL, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'mailSettoreTributi', 'settore.tributi@regione.piemonte.it', NULL, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'REPOSITORY_ACTA', 'RP201209 Regione Piemonte - Agg. 09/2012', null, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'APPLICATION_KEY_ACTA', '24/65/61/-67/-101/-61/-28/36/11/121/-3/-116/-108/-39/-112/-73', null, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'ACTA_SIGN_STEP_BYPASS_FLAG', '0001000', null, NULL, NULL, '2021-01-28', NULL);


INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'DICHIARAZIONI_SERIE_FASCICOLI_CODICE', 'ARISGANWEBDICH', null, NULL, NULL, '2021-01-28', NULL);


INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'RIMBORSI_SERIE_FASCICOLI_CODICE', 'ARISGANWEBRIMB', null, NULL, NULL, '2021-01-28', NULL);


INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'COMUNICAZIONI_VARIE_SERIE_FASCICOLI_CODICE', 'ARISGANWEBCOMV', null, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'DEPOSITI_CAUZIONALI_SERIE_FASCICOLI_CODICE', 'ARISGANWEBDEPCA', null, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'ACCERTAMENTI_VARIE_SERIE_FASCICOLI_CODICE', 'ARISGANWEBACC', null, NULL, NULL, '2021-01-28', NULL);


INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'ACTA_TARGETOBJECT', 'ClassificazioniProtocollateView', NULL, NULL, NULL, '2021-01-28', NULL);


INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'ACTA_NAMESPACEURI', 'objectservice.acaris.acta.doqui.it', NULL, NULL, NULL, '2021-01-28', NULL);



INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'ACTA_LOCALPART', 'ObjectService', NULL, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'ACTA_ENDPOINTADDRESS', 'objectWS?wsdl', NULL, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'ACTA_NAVIWSDLLOCATION', 'navigationWS?wsdl', NULL, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'ACTA_RELAWSDLLOCATION', 'relationshipsWS?wsdl', NULL, NULL, NULL, '2021-01-28', NULL);


INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'ACTA_CODICE', 'codice', NULL, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'ACTA_ANNO', 'anno', NULL, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'ACTA_IDAOOPROTOCOLLANTE', 'idAooProtocollante', NULL, NULL, NULL, '2021-01-28', NULL);


INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'INDEX_BASEURL', 'http://tst-applogic.reteunitaria.piemonte.it/ecmenginews-exp02/', null, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'INDEX_URLWS1', '/services/EcmEngineManagement', null, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'INDEX_FRUITORE', 'sigas', null, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'INDEX_REPOSITORY', 'primary', null, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'INDEX_USERNAME', 'admin@sigas', null, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'INDEX_PASSWORD', 'sigas', null, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'INDEX_UIDPADRE', 'ea4219d6-d8db-11eb-81d5-6790efe1e9c9', null, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'INDEX_TYPEPREFIXEDNAME', 'sigas:documento', null, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'INDEX_MODELPREFIXEDNAME', 'sigas:model', null, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'INDEX_PARENTASSOCTYPEPREFIXEDNAME', 'cm:contains', null, NULL, NULL, '2021-01-28', NULL);

INSERT INTO sigas_c_parametro
(id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES((select max(id_parametro)+1 from sigas_c_parametro), 'INDEX_CONTENTPROPERTYPREFIXEDNAME', 'cm:content', null, NULL, NULL, '2021-01-28', NULL);