alter table sigas_provincia drop column IF EXISTS fk_regione;
alter table sigas_provincia add column fk_regione numeric(6) NOT null default 0;

UPDATE sigas_provincia
SET cod_istat_provincia='001', denom_provincia='TORINO', sigla_provincia='TO', fk_regione=1, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=1;
UPDATE sigas_provincia
SET cod_istat_provincia='002', denom_provincia='VERCELLI', sigla_provincia='VC', fk_regione=1, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=2;
UPDATE sigas_provincia
SET cod_istat_provincia='003', denom_provincia='NOVARA', sigla_provincia='NO', fk_regione=1, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=3;
UPDATE sigas_provincia
SET cod_istat_provincia='004', denom_provincia='CUNEO', sigla_provincia='CN', fk_regione=1, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=4;
UPDATE sigas_provincia
SET cod_istat_provincia='005', denom_provincia='ASTI', sigla_provincia='AT', fk_regione=1, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=5;
UPDATE sigas_provincia
SET cod_istat_provincia='006', denom_provincia='ALESSANDRIA', sigla_provincia='AL', fk_regione=1, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=6;
UPDATE sigas_provincia
SET cod_istat_provincia='007', denom_provincia='VALLE D''AOSTA/VALLĂ‰E D''AOSTE', sigla_provincia='AO', fk_regione=2, inizio_validita='2008-01-01', fine_validita=NULL
WHERE id_provincia=7;
UPDATE sigas_provincia
SET cod_istat_provincia='008', denom_provincia='IMPERIA', sigla_provincia='IM', fk_regione=7, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=8;
UPDATE sigas_provincia
SET cod_istat_provincia='009', denom_provincia='SAVONA', sigla_provincia='SV', fk_regione=7, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=9;
UPDATE sigas_provincia
SET cod_istat_provincia='010', denom_provincia='GENOVA', sigla_provincia='GE', fk_regione=7, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=10;
UPDATE sigas_provincia
SET cod_istat_provincia='011', denom_provincia='LA SPEZIA', sigla_provincia='SP', fk_regione=7, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=11;
UPDATE sigas_provincia
SET cod_istat_provincia='012', denom_provincia='VARESE', sigla_provincia='VA', fk_regione=3, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=12;
UPDATE sigas_provincia
SET cod_istat_provincia='013', denom_provincia='COMO', sigla_provincia='CO', fk_regione=3, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=13;
UPDATE sigas_provincia
SET cod_istat_provincia='014', denom_provincia='SONDRIO', sigla_provincia='SO', fk_regione=3, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=14;
UPDATE sigas_provincia
SET cod_istat_provincia='015', denom_provincia='MILANO', sigla_provincia='MI', fk_regione=3, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=15;
UPDATE sigas_provincia
SET cod_istat_provincia='016', denom_provincia='BERGAMO', sigla_provincia='BG', fk_regione=3, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=16;
UPDATE sigas_provincia
SET cod_istat_provincia='017', denom_provincia='BRESCIA', sigla_provincia='BS', fk_regione=3, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=17;
UPDATE sigas_provincia
SET cod_istat_provincia='018', denom_provincia='PAVIA', sigla_provincia='PV', fk_regione=3, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=18;
UPDATE sigas_provincia
SET cod_istat_provincia='019', denom_provincia='CREMONA', sigla_provincia='CR', fk_regione=3, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=19;
UPDATE sigas_provincia
SET cod_istat_provincia='020', denom_provincia='MANTOVA', sigla_provincia='MN', fk_regione=3, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=20;
UPDATE sigas_provincia
SET cod_istat_provincia='021', denom_provincia='BOLZANO/BOZEN', sigla_provincia='BZ', fk_regione=4, inizio_validita='2008-01-01', fine_validita=NULL
WHERE id_provincia=21;
UPDATE sigas_provincia
SET cod_istat_provincia='022', denom_provincia='TRENTO', sigla_provincia='TN', fk_regione=4, inizio_validita='1920-10-16', fine_validita=NULL
WHERE id_provincia=22;
UPDATE sigas_provincia
SET cod_istat_provincia='023', denom_provincia='VERONA', sigla_provincia='VR', fk_regione=5, inizio_validita='1866-11-19', fine_validita=NULL
WHERE id_provincia=23;
UPDATE sigas_provincia
SET cod_istat_provincia='024', denom_provincia='VICENZA', sigla_provincia='VI', fk_regione=5, inizio_validita='1866-11-19', fine_validita=NULL
WHERE id_provincia=24;
UPDATE sigas_provincia
SET cod_istat_provincia='025', denom_provincia='BELLUNO', sigla_provincia='BL', fk_regione=5, inizio_validita='1866-11-19', fine_validita=NULL
WHERE id_provincia=25;
UPDATE sigas_provincia
SET cod_istat_provincia='026', denom_provincia='TREVISO', sigla_provincia='TV', fk_regione=5, inizio_validita='1866-01-01', fine_validita=NULL
WHERE id_provincia=26;
UPDATE sigas_provincia
SET cod_istat_provincia='027', denom_provincia='VENEZIA', sigla_provincia='VE', fk_regione=5, inizio_validita='1866-01-01', fine_validita=NULL
WHERE id_provincia=27;
UPDATE sigas_provincia
SET cod_istat_provincia='028', denom_provincia='PADOVA', sigla_provincia='PD', fk_regione=5, inizio_validita='1866-01-01', fine_validita=NULL
WHERE id_provincia=28;
UPDATE sigas_provincia
SET cod_istat_provincia='029', denom_provincia='ROVIGO', sigla_provincia='RO', fk_regione=5, inizio_validita='1866-01-01', fine_validita=NULL
WHERE id_provincia=29;
UPDATE sigas_provincia
SET cod_istat_provincia='030', denom_provincia='UDINE', sigla_provincia='UD', fk_regione=6, inizio_validita='1866-11-19', fine_validita=NULL
WHERE id_provincia=30;
UPDATE sigas_provincia
SET cod_istat_provincia='031', denom_provincia='GORIZIA', sigla_provincia='GO', fk_regione=6, inizio_validita='1921-01-01', fine_validita=NULL
WHERE id_provincia=31;
UPDATE sigas_provincia
SET cod_istat_provincia='032', denom_provincia='TRIESTE', sigla_provincia='TS', fk_regione=6, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=32;
UPDATE sigas_provincia
SET cod_istat_provincia='033', denom_provincia='PIACENZA', sigla_provincia='PC', fk_regione=8, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=33;
UPDATE sigas_provincia
SET cod_istat_provincia='034', denom_provincia='PARMA', sigla_provincia='PR', fk_regione=8, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=34;
UPDATE sigas_provincia
SET cod_istat_provincia='035', denom_provincia='REGGIO NELL''EMILIA', sigla_provincia='RE', fk_regione=8, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=35;
UPDATE sigas_provincia
SET cod_istat_provincia='036', denom_provincia='MODENA', sigla_provincia='MO', fk_regione=8, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=36;
UPDATE sigas_provincia
SET cod_istat_provincia='037', denom_provincia='BOLOGNA', sigla_provincia='BO', fk_regione=8, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=37;
UPDATE sigas_provincia
SET cod_istat_provincia='038', denom_provincia='FERRARA', sigla_provincia='FE', fk_regione=8, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=38;
UPDATE sigas_provincia
SET cod_istat_provincia='039', denom_provincia='RAVENNA', sigla_provincia='RA', fk_regione=8, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=39;
UPDATE sigas_provincia
SET cod_istat_provincia='040', denom_provincia='FORLI''-CESENA', sigla_provincia='FC', fk_regione=8, inizio_validita='1992-04-04', fine_validita=NULL
WHERE id_provincia=40;
UPDATE sigas_provincia
SET cod_istat_provincia='041', denom_provincia='PESARO E URBINO', sigla_provincia='PU', fk_regione=11, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=41;
UPDATE sigas_provincia
SET cod_istat_provincia='042', denom_provincia='ANCONA', sigla_provincia='AN', fk_regione=11, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=42;
UPDATE sigas_provincia
SET cod_istat_provincia='043', denom_provincia='MACERATA', sigla_provincia='MC', fk_regione=11, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=43;
UPDATE sigas_provincia
SET cod_istat_provincia='044', denom_provincia='ASCOLI PICENO', sigla_provincia='AP', fk_regione=11, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=44;
UPDATE sigas_provincia
SET cod_istat_provincia='045', denom_provincia='MASSA-CARRARA', sigla_provincia='MS', fk_regione=9, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=45;
UPDATE sigas_provincia
SET cod_istat_provincia='046', denom_provincia='LUCCA', sigla_provincia='LU', fk_regione=9, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=46;
UPDATE sigas_provincia
SET cod_istat_provincia='047', denom_provincia='PISTOIA', sigla_provincia='PT', fk_regione=9, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=47;
UPDATE sigas_provincia
SET cod_istat_provincia='048', denom_provincia='FIRENZE', sigla_provincia='FI', fk_regione=9, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=48;
UPDATE sigas_provincia
SET cod_istat_provincia='049', denom_provincia='LIVORNO', sigla_provincia='LI', fk_regione=9, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=49;
UPDATE sigas_provincia
SET cod_istat_provincia='050', denom_provincia='PISA', sigla_provincia='PI', fk_regione=9, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=50;
UPDATE sigas_provincia
SET cod_istat_provincia='051', denom_provincia='AREZZO', sigla_provincia='AR', fk_regione=9, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=51;
UPDATE sigas_provincia
SET cod_istat_provincia='052', denom_provincia='SIENA', sigla_provincia='SI', fk_regione=9, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=52;
UPDATE sigas_provincia
SET cod_istat_provincia='053', denom_provincia='GROSSETO', sigla_provincia='GR', fk_regione=9, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=53;
UPDATE sigas_provincia
SET cod_istat_provincia='054', denom_provincia='PERUGIA', sigla_provincia='PG', fk_regione=10, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=54;
UPDATE sigas_provincia
SET cod_istat_provincia='055', denom_provincia='TERNI', sigla_provincia='TR', fk_regione=10, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=55;
UPDATE sigas_provincia
SET cod_istat_provincia='056', denom_provincia='VITERBO', sigla_provincia='VT', fk_regione=12, inizio_validita='1871-01-01', fine_validita=NULL
WHERE id_provincia=56;
UPDATE sigas_provincia
SET cod_istat_provincia='057', denom_provincia='RIETI', sigla_provincia='RI', fk_regione=12, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=57;
UPDATE sigas_provincia
SET cod_istat_provincia='058', denom_provincia='ROMA', sigla_provincia='RM', fk_regione=12, inizio_validita='1871-01-01', fine_validita=NULL
WHERE id_provincia=58;
UPDATE sigas_provincia
SET cod_istat_provincia='059', denom_provincia='LATINA', sigla_provincia='LT', fk_regione=12, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=59;
UPDATE sigas_provincia
SET cod_istat_provincia='060', denom_provincia='FROSINONE', sigla_provincia='FR', fk_regione=12, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=60;
UPDATE sigas_provincia
SET cod_istat_provincia='061', denom_provincia='CASERTA', sigla_provincia='CE', fk_regione=15, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=61;
UPDATE sigas_provincia
SET cod_istat_provincia='062', denom_provincia='BENEVENTO', sigla_provincia='BN', fk_regione=15, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=62;
UPDATE sigas_provincia
SET cod_istat_provincia='063', denom_provincia='NAPOLI', sigla_provincia='NA', fk_regione=15, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=63;
UPDATE sigas_provincia
SET cod_istat_provincia='064', denom_provincia='AVELLINO', sigla_provincia='AV', fk_regione=15, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=64;
UPDATE sigas_provincia
SET cod_istat_provincia='065', denom_provincia='SALERNO', sigla_provincia='SA', fk_regione=15, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=65;
UPDATE sigas_provincia
SET cod_istat_provincia='066', denom_provincia='L''AQUILA', sigla_provincia='AQ', fk_regione=13, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=66;
UPDATE sigas_provincia
SET cod_istat_provincia='067', denom_provincia='TERAMO', sigla_provincia='TE', fk_regione=13, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=67;
UPDATE sigas_provincia
SET cod_istat_provincia='068', denom_provincia='PESCARA', sigla_provincia='PE', fk_regione=13, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=68;
UPDATE sigas_provincia
SET cod_istat_provincia='069', denom_provincia='CHIETI', sigla_provincia='CH', fk_regione=13, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=69;
UPDATE sigas_provincia
SET cod_istat_provincia='070', denom_provincia='CAMPOBASSO', sigla_provincia='CB', fk_regione=14, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=70;
UPDATE sigas_provincia
SET cod_istat_provincia='071', denom_provincia='FOGGIA', sigla_provincia='FG', fk_regione=16, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=71;
UPDATE sigas_provincia
SET cod_istat_provincia='072', denom_provincia='BARI', sigla_provincia='BA', fk_regione=16, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=72;
UPDATE sigas_provincia
SET cod_istat_provincia='073', denom_provincia='TARANTO', sigla_provincia='TA', fk_regione=16, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=73;
UPDATE sigas_provincia
SET cod_istat_provincia='074', denom_provincia='BRINDISI', sigla_provincia='BR', fk_regione=16, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=74;
UPDATE sigas_provincia
SET cod_istat_provincia='075', denom_provincia='LECCE', sigla_provincia='LE', fk_regione=16, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=75;
UPDATE sigas_provincia
SET cod_istat_provincia='076', denom_provincia='POTENZA', sigla_provincia='PZ', fk_regione=17, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=76;
UPDATE sigas_provincia
SET cod_istat_provincia='077', denom_provincia='MATERA', sigla_provincia='MT', fk_regione=17, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=77;
UPDATE sigas_provincia
SET cod_istat_provincia='078', denom_provincia='COSENZA', sigla_provincia='CS', fk_regione=18, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=78;
UPDATE sigas_provincia
SET cod_istat_provincia='079', denom_provincia='CATANZARO', sigla_provincia='CZ', fk_regione=18, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=79;
UPDATE sigas_provincia
SET cod_istat_provincia='080', denom_provincia='REGGIO DI CALABRIA', sigla_provincia='RC', fk_regione=18, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=80;
UPDATE sigas_provincia
SET cod_istat_provincia='081', denom_provincia='TRAPANI', sigla_provincia='TP', fk_regione=19, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=81;
UPDATE sigas_provincia
SET cod_istat_provincia='082', denom_provincia='PALERMO', sigla_provincia='PA', fk_regione=19, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=82;
UPDATE sigas_provincia
SET cod_istat_provincia='083', denom_provincia='MESSINA', sigla_provincia='ME', fk_regione=19, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=83;
UPDATE sigas_provincia
SET cod_istat_provincia='084', denom_provincia='AGRIGENTO', sigla_provincia='AG', fk_regione=19, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=84;
UPDATE sigas_provincia
SET cod_istat_provincia='085', denom_provincia='CALTANISSETTA', sigla_provincia='CL', fk_regione=19, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=85;
UPDATE sigas_provincia
SET cod_istat_provincia='086', denom_provincia='ENNA', sigla_provincia='EN', fk_regione=19, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=86;
UPDATE sigas_provincia
SET cod_istat_provincia='087', denom_provincia='CATANIA', sigla_provincia='CT', fk_regione=19, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=87;
UPDATE sigas_provincia
SET cod_istat_provincia='088', denom_provincia='RAGUSA', sigla_provincia='RG', fk_regione=19, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=88;
UPDATE sigas_provincia
SET cod_istat_provincia='089', denom_provincia='SIRACUSA', sigla_provincia='SR', fk_regione=19, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=89;
UPDATE sigas_provincia
SET cod_istat_provincia='090', denom_provincia='SASSARI', sigla_provincia='SS', fk_regione=20, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=90;
UPDATE sigas_provincia
SET cod_istat_provincia='091', denom_provincia='NUORO', sigla_provincia='NU', fk_regione=20, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=91;
UPDATE sigas_provincia
SET cod_istat_provincia='092', denom_provincia='CAGLIARI', sigla_provincia='CA', fk_regione=20, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=92;
UPDATE sigas_provincia
SET cod_istat_provincia='093', denom_provincia='PORDENONE', sigla_provincia='PN', fk_regione=6, inizio_validita='1866-11-19', fine_validita=NULL
WHERE id_provincia=93;
UPDATE sigas_provincia
SET cod_istat_provincia='094', denom_provincia='ISERNIA', sigla_provincia='IS', fk_regione=14, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=94;
UPDATE sigas_provincia
SET cod_istat_provincia='095', denom_provincia='ORISTANO', sigla_provincia='OR', fk_regione=20, inizio_validita='1861-01-01', fine_validita=NULL
WHERE id_provincia=95;
UPDATE sigas_provincia
SET cod_istat_provincia='096', denom_provincia='BIELLA', sigla_provincia='BI', fk_regione=1, inizio_validita='1992-04-16', fine_validita=NULL
WHERE id_provincia=96;
UPDATE sigas_provincia
SET cod_istat_provincia='097', denom_provincia='LECCO', sigla_provincia='LC', fk_regione=3, inizio_validita='1992-04-16', fine_validita=NULL
WHERE id_provincia=97;
UPDATE sigas_provincia
SET cod_istat_provincia='098', denom_provincia='LODI', sigla_provincia='LO', fk_regione=3, inizio_validita='1992-04-16', fine_validita=NULL
WHERE id_provincia=98;
UPDATE sigas_provincia
SET cod_istat_provincia='099', denom_provincia='RIMINI', sigla_provincia='RN', fk_regione=8, inizio_validita='1992-04-16', fine_validita=NULL
WHERE id_provincia=99;
UPDATE sigas_provincia
SET cod_istat_provincia='100', denom_provincia='PRATO', sigla_provincia='PO', fk_regione=9, inizio_validita='1992-04-16', fine_validita=NULL
WHERE id_provincia=100;
UPDATE sigas_provincia
SET cod_istat_provincia='101', denom_provincia='CROTONE', sigla_provincia='KR', fk_regione=18, inizio_validita='1992-04-16', fine_validita=NULL
WHERE id_provincia=101;
UPDATE sigas_provincia
SET cod_istat_provincia='102', denom_provincia='VIBO VALENTIA', sigla_provincia='VV', fk_regione=18, inizio_validita='1992-04-16', fine_validita=NULL
WHERE id_provincia=102;
UPDATE sigas_provincia
SET cod_istat_provincia='103', denom_provincia='VERBANO-CUSIO-OSSOLA', sigla_provincia='VB', fk_regione=1, inizio_validita='1992-05-23', fine_validita=NULL
WHERE id_provincia=103;
UPDATE sigas_provincia
SET cod_istat_provincia='108', denom_provincia='MONZA E DELLA BRIANZA', sigla_provincia='MB', fk_regione=3, inizio_validita='2009-06-30', fine_validita=NULL
WHERE id_provincia=104;
UPDATE sigas_provincia
SET cod_istat_provincia='109', denom_provincia='FERMO', sigla_provincia='FM', fk_regione=11, inizio_validita='2009-06-30', fine_validita=NULL
WHERE id_provincia=105;
UPDATE sigas_provincia
SET cod_istat_provincia='110', denom_provincia='BARLETTA-ANDRIA-TRANI', sigla_provincia='BT', fk_regione=16, inizio_validita='2009-06-30', fine_validita=NULL
WHERE id_provincia=106;
UPDATE sigas_provincia
SET cod_istat_provincia='111', denom_provincia='SUD SARDEGNA', sigla_provincia='SU', fk_regione=20, inizio_validita='2018-01-01', fine_validita=NULL
WHERE id_provincia=107;
UPDATE sigas_provincia
SET cod_istat_provincia='111', denom_provincia='SUD SARDEGNA', sigla_provincia='XX', fk_regione=20, inizio_validita='2017-01-01', fine_validita='2017-12-31'
WHERE id_provincia=108;
UPDATE sigas_provincia
SET cod_istat_provincia='104', denom_provincia='OLBIA-TEMPIO', sigla_provincia='OT', fk_regione=20, inizio_validita='1996-01-24', fine_validita='2016-12-31'
WHERE id_provincia=109;
UPDATE sigas_provincia
SET cod_istat_provincia='105', denom_provincia='OGLIASTRA', sigla_provincia='OG', fk_regione=20, inizio_validita='2006-01-01', fine_validita='2016-12-31'
WHERE id_provincia=110;
UPDATE sigas_provincia
SET cod_istat_provincia='106', denom_provincia='MEDIO CAMPIDANO', sigla_provincia='VS', fk_regione=20, inizio_validita='2006-01-01', fine_validita='2016-12-31'
WHERE id_provincia=111;
UPDATE sigas_provincia
SET cod_istat_provincia='107', denom_provincia='CARBONIA-IGLESIAS', sigla_provincia='CI', fk_regione=20, inizio_validita='2006-01-01', fine_validita='2016-12-31'
WHERE id_provincia=112;
UPDATE sigas_provincia
SET cod_istat_provincia='007', denom_provincia='VALLE D''AOSTA', sigla_provincia='AO', fk_regione=22, inizio_validita='1861-01-01', fine_validita='2007-12-31'
WHERE id_provincia=113;
UPDATE sigas_provincia
SET cod_istat_provincia='021', denom_provincia='BOLZANO - BOZEN', sigla_provincia='BZ', fk_regione=21, inizio_validita='1921-01-01', fine_validita='2007-12-31'
WHERE id_provincia=114;
UPDATE sigas_provincia
SET cod_istat_provincia='040', denom_provincia='FORLI''', sigla_provincia='FO', fk_regione=8, inizio_validita='1861-01-01', fine_validita='1992-04-03'
WHERE id_provincia=115;
UPDATE sigas_provincia
SET cod_istat_provincia='701', denom_provincia='FIUME', sigla_provincia='FM', fk_regione=23, inizio_validita='1921-01-01', fine_validita='1950-12-31'
WHERE id_provincia=116;
UPDATE sigas_provincia
SET cod_istat_provincia='702', denom_provincia='POLA', sigla_provincia='PL', fk_regione=23, inizio_validita='1921-01-01', fine_validita='1950-12-31'
WHERE id_provincia=117;
UPDATE sigas_provincia
SET cod_istat_provincia='703', denom_provincia='ZARA', sigla_provincia='ZA', fk_regione=23, inizio_validita='1921-01-01', fine_validita='1950-12-31'
WHERE id_provincia=118;
