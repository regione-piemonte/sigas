/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.integration.doqui;

public class DoquiConstants {

	public static final String LOGGER_PREFIX = "doqui";
	public static final String APPLICATION_CODE = "sigasbl";

	public final static String NULL_VALUE = "NULL_VALUE";

	public static final String COLLOCAZIONE_CARTACEA = "Archivio settore competente";	
	public static final String ENTE = "REGIONE PIEMONTE"; //provvisorio, recuperarlo da tabella su TAU	
	public static final String SI = "Si";
	public static final String NO = "No";
	public static final String LUOGO = "TORINO";
	public static final String FRUITORE_PEC = "PEC";
	public static final String TASSA_AUTO = "TASSA AUTO";


    public static final String MIMETYPE_DEFAULT             = "application/octet-stream";
    
    
	public static final String INDEX_ROOT = "/app:company_home";									// 20200616_LC							
	public static final String INDEX_DEFAULT_PREFIX = "cm:";
	public static final String INDEX_ENCODING = "UTF-8";
	public static final String INDEX_TYPE_TEXT = "d:text";
	
	public static final String INDEX_SUFFIX= "/cm:";
	public static final String INDEX_CONTENT_PREFIX_NAME = "cm:content";
	public static final String INDEX_CONTENT_PREFIX_CONTAINS="cm:contains";
	public static final String INDEX_CONTENT_PREFIX_MODEL = "cm:contentmodel";
	public static final String INDEX_CONTENT_PREFIX_FOLDER = "cm:folder";
	public static final String INDEX_CONTENT_PREFIX_NAME_SHORT = "cm:name";

	
	public static final long STATO_RICHIESTA_INVIATA  = 1;
	public static final long STATO_RICHIESTA_EVASA    = 2;
	public static final long STATO_RICHIESTA_ERRATA   = 3;
	public static final long STATO_RICHIESTA_IN_ELAB  = 4;
	// Servizio
	public static final long SERVIZIO_INSERIMENTO_ARCHIVIAZIONE       = 10;
	public static final long SERVIZIO_CONSULTAZIONE_ARCHIVIAZIONE     = 11;	
	public static final long SERVIZIO_INSERIMENTO_PROTOCOLLAZIONE     = 20;
	public static final long SERVIZIO_CONSULTAZIONE_PROTOCOLLAZIONE   = 21;
	public static final long SERVIZIO_ASSOCIA_DOCUMENTO_PROTOCOLLO    = 22;
	public static final long SERVIZIO_INSERIMENTO_PROTOCOLLAZIONE_FISICA = 23;	
	public static final long SERVIZIO_SPOSTAMENTO_PROTOCOLLAZIONE     = 24;			// 20200706_LC	
	public static final long SERVIZIO_COPIA_PROTOCOLLAZIONE     	  = 25;			// 20200728_LC	
	public static final long SERVIZIO_INSERIMENTO_GENERICO            = 30;
	public static final long SERVIZIO_CONSULTAZIONE_GENERICO          = 31;
	public static final long SERVIZIO_CANCELLAZIONE_GENERICO          = 32;
	public static final long SERVIZIO_MODIFICA_STATO_RICHIESTA        = 33;	
	public static final long SERVIZIO_RICERCA_ALLEGATO_INDEX     = 34;
	public static final long SERVIZIO_AGGIUNGI_ALLEGATO_ACTA    = 35;	
	// TipoFornitore
	public static final long FORNITORE_ACTA	= 1;
	public static final long FORNITORE_INDEX	= 2;
	
	// --
	

	
	// codice fruitore
	public static final String CODICE_FRUITORE = "SIGAS";
	
	// --
	
	// 20200711_LC utenete SYSTEM (scheduled tasks)
	public static final String USER_SCHEDULED_TASK  = "SYSTEM";
	
	
	
	
	

	public static final String ACTA_ID_AOO = "ACTA_ID_AOO";

	public static final String ACTA_ID_STRUTTURA = "ACTA_ID_STRUTTURA";

	public static final String ACTA_ID_NODO = "ACTA_ID_NODO";

	public static final String ACTA_CODE_FRUITORE = "ACTA_CODE_FRUITORE";

	public static final String ACTA_CF = "ACTA_CF";	
	
	public static final String REPOSITORY_ACTA = "REPOSITORY_ACTA";
	
	public static final String APPLICATION_KEY_ACTA = "APPLICATION_KEY_ACTA";	
	
	
	public static final String CODICE_STATO_PRESENTATO = "PRESEN";
	
	public static final String CODICE_STATO_IN_LAVORAZIONE = "IN_LAV";
	
	public static final String CODICE_STATO_ACCETTATA = "ACC";
	
	public static final String CODICE_STATO_RIFIUTATA = "RIF";

	public static final String CODICE_STATO_LETT_RISP = "LETT_RISP";

	public static final String CODICE_CLASSIFICAZIONE = "codiceClassificazione";
	
	public static final String COGNOME_DIRIGENTE = "cognomeDirigente";
	
	public static final String NOME_DIRIGENTE = "nomeDirigente";
	
	public static final String MAIL_SETTORE_TRIBUTI = "mailSettoreTributi";
	
	public static final String CODICE_STATO_ARCHIVIAZIONE_DA_CARICARE = "DA_CARICARE";
	
	public static final String CODICE_STATO_ARCHIVIAZIONE_CARICATO = "CARICATO";
	
//	public static final String CODICE_STATO_ARCHIVIAZIONE_IN_CARICAMENTO = "IN_CARICAMENTO";
	
	public static final String DESTINATARIO_GIURIDICO = "REGIONE PIEMONTE";
	
	public static final String ACCERTAMENTI = "ACCE";
	public static final String COMUNICAZIONI_VARIE = "COMV";
	public static final String DEPOSITI_CAUZIONALI = "DEPO";
	public static final String DICHIARAZIONI = "DICH";
	public static final String RIMBORSI = "RIMB";
	
	public static final String NOME_SOGGETTO_MITTENTE = "SIGAS";
	public static final String COGNOME_SOGGETTO_MITTENTE = "SIGAS";
	
}
