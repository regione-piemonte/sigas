export class MessageCreaDichiarazioneError {
    // DICHIARAZIONE PIEMONTE
    public static DICHIARAZIONE_BOZZA_ESISTENTE: string = "DBE";
    public static DICHIARAZIONE_INVIATA_ESISTENTE: string = "DIE";
    public static DICHIARAZIONE_BOZZA_RETTIFICATA: string = "DBR";
    public static DICHIARAZIONE_INVIATA_RETTIFICA: string = "DIR";
    public static DICHIARAZIONE_FUORI_TEMPO: string = "DFT";
    public static DICHIARAZIONE_INVIATA_ANNO_PRECEDENTE: string = "DIAP";
}

export class MessageNuovoImpiantoCessazioneError {
    // NUOVO IMPIANTO DICHIARAZIONE CESSAZIONE PIEMONTE
    public static IMPIANTO_ESISTENTE_DICHIARAZIONE_BOZZA: string = "IEDB";

}

export class MessageDichiarazioneRPSuccess {
    public static modifica: string = "Modifica Dichiarazione avvenuta con successo";
    public static inserimento: string = "Inserimento Dichiarazione avvenuta con successo";
    public static rettifica: string = "Rettifica Dichiarazione avvenuta con successo";
}

export class MessageConfermaSalvaDichiarazioneRegionale {
    public static INSERIMENTO: string = "I";
    public static CONFERMA: string = "C";
    public static CONFERMANEW: string = "CN";
    public static CONFERMAVECCHIA: string = "CV";
}

export class TypeSuccess {
    public static INSERIMENTO_IMPIANTO: string = "INSERIMENTO_IMPIANTO";
    public static MODIFICA_IMPIANTO: string = "MODIFICA_IMPIANTO";
    public static RETTIFICA_IMPIANTO: string = "RETTIFICA_IMPIANTO";
    public static CESSAZIONE_IMPIANTO: string = "MODIFICA_IMPIANTO_CESSAZIONE";
}

export class MessageRettificaDichiarazioneError {
    public static DICHIARAZIONE_BOZZA_RETTIFICATA: string = "DBR";
    public static DICHIARAZIONE_INVIATA_RETTIFICA: string = "DIR";
}

export class MessageInviaDichiarazioneError {
    // PAGAMENTI MANCANTI
    public static NESSUN_PAGAMENTO_PRESENTE: string = "NPP";
    public static NESSUN_IMPIANTO_PRESENTE: string = "NIP";


}

export class MessageEliminaImpianto {
    public static PAGAMENTI_PRESENTI_IMPIANTO = "PPI";
}

export class EsitoSalvaDichiarazione {
    public static FIND = "F";
    public static INSERT = "I"
}