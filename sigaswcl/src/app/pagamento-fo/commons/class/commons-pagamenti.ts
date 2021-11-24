export class MessageInserisciPagamentoError {
    // DICHIARAZIONE PIEMONTE
    public static DICHIARAZIONE_BOZZA_ESISTENTE: string = "DBE";
    public static DICHIARAZIONE_INVIATA_ESISTENTE: string = "DIE";
    public static DICHIARAZIONE_BOZZA_RETTIFICATA: string = "DBR";
    public static DICHIARAZIONE_INVIATA_RETTIFICA: string = "DIR";
    public static DICHIARAZIONE_FUORI_TEMPO: string = "DFT";
    public static DICHIARAZIONE_INVIATA_ANNO_PRECEDENTE: string = "DIAP";
}

export class PagamentiCarrelloVO {
    public static STATO_CARRELLO_INIZIALIZZATO_ID: number = 1;
    public static STATO_CARRELLO_INCORSO_ID = 2;
}

export class SituazionePagamentoConstants {
    public static CORRETTO: number = 1;
    public static INSUFFICIENTE = 2;
}

