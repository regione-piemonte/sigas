export class Routing {
    public static HOME: string = "home";
    public static ELENCO_CONSUMI: string = "home/soggetti";
    public static DETTAGLIO_SOGGETTO: string = "home/soggetti/dettaglioSoggetto";
    public static NUOVO_SOGGETTO: string = "home/soggetti/inserimentoSoggetto";
    public static ASSOCIA_SOGGETTO: string = "home/ordinativiIncasso/associaSoggetto";
    public static ORDINATIVI_INCASSO: string = "home/ordinativiIncasso";
    public static ASSOCIA_VERSAMENTO: string = "home/ordinativiIncasso/associaVersamento";
}
export class RoutingImpostazione {
    public static IMPORTAZIONE_UTF: string = "UTF/importazione";
    public static ALIQUOTE: string = "impostazioni/aliquote";
    public static TASSI_INTERESSE: string = "impostazioni/tassi";
}
export class RoutingAccreditamento {
    public static ACCREDITAMENTO_ELENCO: string = "accreditamento/elencoRichieste";
    public static ACCREDITAMENTO_GESTIONE: string = "accreditamento/gestioneAccreditamento";
    public static ACCREDITAMENTO_INSERIMENTO_SUCCESS: string = "accreditamento/successInserimento";
}


export class RoutingAccreditamentoFO {
    public static RICERCA_PRATICHE: string = "accreditamento/ricerca/ricercaPratiche";
    public static INSERIMENTO_DICHIARANTE: string = "accreditamento/inserisciAccreditamento/inserisciAccreditamentoDichiarante";
    public static ELENCO_PRATICHE: string = "accreditamento/ricerca/elencoPratiche";
    public static INSERMENTO_LEGALE_RAPPR: string = "accreditamento/inserisciAccreditamento/inserisciAccreditamentoLegaleRappresentante";
    public static INSERIMENTO_OPERATRE_SERVIZI: string = "accreditamento/inserisciAccreditamento/inserisciAccreditamentoOperatoreServizi";
    public static MODIFICA_ACCREDITAMENTO_ELENCO: string = "accreditamento/modificaAccreditamento/elencoPratiche";
    public static MODIFICA_ACCREDITAMENTO: string = "accreditamento/modificaAccreditamento/modificaPraticaAccreditamento";
    public static VISUALIZZA_ACCREDITAMENTO: string = "accreditamento/visualizzaAccreditamento/visualizzaPraticaAccreditamento";
//    public static MODIFICA_DICHIARANTE: string = "accreditamento/modificaAccreditamento/modificaAccreditamentoDichiarante";
//    public static MODIFICA_LEGALE_RAPPR: string = "accreditamento/modificaAccreditamento/modificaAccreditamentoLegaleRappresentante"
//    public static MODIFICA_OPERATORE_SERVIZI: string = "accreditamento/modificaAccreditamento/modificaAccreditamentoOperatoreServizi";
    public static REVOCA_ACCREDITAMENTO: string = "accreditamento/revocaAccreditamento/revocaAccreditamento";
    public static ACCREDITAMENTO_INSERIMENTO_SUCCESS: string = "accreditamento/successInserimento";
    public static ACCREDITAMENTO_MODIFICA_SUCCESS: string = "accreditamento/successModifica";
    public static ACCREDITAMENTO_REVOCA_SUCCESS: string = "accreditamento/successRevoca";
}

export class RoutingDocumentazioneFO {
    public static INOLTRA_DOCUMENTAZIONE: string = "documentazione/inoltraDocumentazione";
    public static CONSULTA_DOCUMENTAZIONE: string = "documentazione/consultaDocumentazione";
}

export class RoutingDocumentazioneBO {
    public static DOCUMENTI_IN_ARRIVO: string = "documentazione/documentazioneInArrivo";
    public static DETTAGLIO_DOCUMENTAZIONE: string = "documentazione/documentazioneInArrivo/dettaglioDocumentazione";
    public static GESTIONE_RISPOSTA_TEMPLATE_LETTERA: string = "documentazione/documentazioneInArrivo/template-lettera-risposta/"; //+id
}