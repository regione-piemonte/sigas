export class Routing {
    public static HOME: string = "home";
}
export class RoutingAccreditamento {
    public static RICERCA_DICHIARANTE: string = "accreditamento/inserisciAccreditamento/ricercadichiarante";
    public static INSERIMENTO_DICHIARANTE: string = "accreditamento/inserisciAccreditamento/inserisciAccreditamentoDichiarante";
    public static INSERMENTO_LEGALE_RAPPR: string = "accreditamento/inserisciAccreditamento/inserisciAccreditamentoLegaleRappresentante";
    public static INSERIMENTO_OPERATRE_SERVIZI: string = "accreditamento/inserisciAccreditamento/inserisciAccreditamentoOperatoreServizi";
    public static MODIFICA_DICHIARANTE: string = "accreditamento/modificaAccreditamento/modificaAccreditamentoDichiarante";
    public static MODIFICA_LEGALE_RAPPR: string = "accreditamento/modificaAccreditamento/modificaAccreditamentoLegaleRappresentante"
    public static MODIFICA_OPERATORE_SERVIZI: string = "accreditamento/modificaAccreditamento/modificaAccreditamentoOperatoreServizi";
    public static REVOCA_ACCREDITAMENTO: string = "accreditamento/revocaAccreditamento/revocaAccreditamento";
    public static ACCREDITAMENTO_INSERIMENTO_SUCCESS: string = "accreditamento/successInserimento";
    public static ACCREDITAMENTO_MODIFICA_SUCCESS: string = "accreditamento/successModifica";
    public static ACCREDITAMENTO_REVOCA_SUCCESS: string = "accreditamento/successRevoca";
}
export class RoutingDichiarazioneRP {
    public static INSERIMENTO_CREA_DICHIARAZIONE: string = "dichiarazioniRP/inserimentoDichiarazioneRP/creaDichiarazione";
    public static INSERIMENTO_RICERCA_DICHIARANTE: string = "dichiarazioniRP/inserimentoDichiarazioneRP/ricercaDichiarante";
    public static INSERIMENTO_INSERISCI_DICHIARAZIONE: string = "dichiarazioniRP/inserimentoDichiarazioneRP/inserisciDichiarazione";
    public static INSERIMENTO_RICERCA_IMPIANTO: string = "dichiarazioniRP/inserimentoDichiarazioneRP/ricercaImpianto/";//+idDichiarazione
    public static INSERIMENTO_DATI_IMPIANTO: string = "dichiarazioniRP/inserimentoDichiarazioneRP/datiImpianto/";//+idDichiarazione
    public static INSERIMENTO_DATI_TITOLARE: string = "dichiarazioniRP/inserimentoDichiarazioneRP/datiTitolare/";//+idDichiarazione
    public static INSERIMENTO_DATI_GESTORE: string = "dichiarazioniRP/inserimentoDichiarazioneRP/datiGestore/";//+idDichiarazione
    public static INSERIMENTO_PAGAMENTI_IMPIANTO: string = "dichiarazioniRP/inserimentoDichiarazioneRP/pagamentiImpianto/";//+idDichiarazione
    public static INSERIMENTOSUCCESS: string = "dichiarazioniRP/successInserimento/";//+idDichiarazione
    
    //INSERIMENTO CESSAZIONE RP
    public static INSERIMENTO_CREA_DICHIARAZIONE_CESSAZIONE: string = "dichiarazioniRP/inserimentoDichiarazioneCessazioneRP/creaDichiarazione";
    public static INSERIMENTO_RICERCA_DICHIARANTE_CESSAZIONE: string = "dichiarazioniRP/inserimentoDichiarazioneCessazioneRP/ricercaDichiarante";
    public static INSERIMENTO_INSERISCI_DICHIARAZIONE_CESSAZIONE: string = "dichiarazioniRP/inserimentoDichiarazioneCessazioneRP/inserisciDichiarazione";
    public static INSERIMENTO_RICERCA_IMPIANTO_CESSAZIONE: string = "dichiarazioniRP/inserimentoDichiarazioneCessazioneRP/ricercaImpianto/";//+idDichiarazione
    public static INSERIMENTO_DATI_IMPIANTO_CESSAZIONE: string = "dichiarazioniRP/inserimentoDichiarazioneCessazioneRP/datiImpianto/";//+idDichiarazione
    public static INSERIMENTO_DATI_TITOLARE_CESSAZIONE: string = "dichiarazioniRP/inserimentoDichiarazioneCessazioneRP/datiTitolare/";//+idDichiarazione
    public static INSERIMENTO_DATI_GESTORE_CESSAZIONE: string = "dichiarazioniRP/inserimentoDichiarazioneCessazioneRP/datiGestore/";//+idDichiarazione
    public static INSERIMENTO_PAGAMENTI_IMPIANTO_CESSAZIONE: string = "dichiarazioniRP/inserimentoDichiarazioneCessazioneRP/pagamentiImpianto/";//+idDichiarazione
    public static INSERIMENTOSUCCESS_CESSAZIONE: string = "dichiarazioniRP/successInserimentoCessazione/";//+idDichiarazione

    //MODIFICA
    public static MODIFICA_RICERCA_DICHIARAZIONE: string = "dichiarazioniRP/modificaDichiarazioneRP/ricercaDichiarazione";
    public static MODIFICA_ELENCO_DICHIARAZIONI = "dichiarazioniRP/modificaDichiarazioneRP/elencoDichiarazioni"
    public static MODIFICA_DICHIARAZIONE = 'dichiarazioniRP/modificaDichiarazioneRP/modificaDichiarazione/'; // +idDichiarazione 
    public static MODIFICA_ELENCO_IMPIANTI: string = "dichiarazioniRP/modificaDichiarazioneRP/elencoImpianti/"; // +idDichiarazione
    public static MODIFICA_IMPIANTO: string = "dichiarazioniRP/modificaDichiarazioneRP/impiantoDichiarazione/";//+id
    public static MODIFICA_TITOLARE: string = "dichiarazioniRP/modificaDichiarazioneRP/modificaTitolare/";//+id
    public static MODIFICA_GESTORE: string = "dichiarazioniRP/modificaDichiarazioneRP/modificaGestore/";//+id
    public static SUCCESS_MODIFICA: string = "dichiarazioniRP/successModifica/";//+id
    public static MODIFICA_PAGAMENTI_IMPIANTO: string = "dichiarazioniRP/modificaDichiarazioneRP/pagamentiImpianto/";//+idDichiarazione
    
    
    //MODIFICA DICHIARAZIONE CESSAZIONE RP
    public static MODIFICA_RICERCA_DICHIARAZIONE_CESSAZIONE: string = "dichiarazioniRP/modificaDichiarazioneCessazioneRP/ricercaDichiarazione";
    public static MODIFICA_ELENCO_DICHIARAZIONI_CESSAZIONE = "dichiarazioniRP/modificaDichiarazioneCessazioneRP/elencoDichiarazioni"
    public static MODIFICA_DICHIARAZIONE_CESSAZIONE = 'dichiarazioniRP/modificaDichiarazioneCessazioneRP/modificaDichiarazione/'; // +idDichiarazione 
    public static MODIFICA_ELENCO_IMPIANTI_CESSAZIONE: string = "dichiarazioniRP/modificaDichiarazioneCessazioneRP/elencoImpianti/"; // +idDichiarazione
    public static MODIFICA_IMPIANTO_CESSAZIONE: string = "dichiarazioniRP/modificaDichiarazioneCessazioneRP/impiantoDichiarazione/";//+id
    public static MODIFICA_TITOLARE_CESSAZIONE: string = "dichiarazioniRP/modificaDichiarazioneCessazioneRP/modificaTitolare/";//+id
    public static MODIFICA_GESTORE_CESSAZIONE: string = "dichiarazioniRP/modificaDichiarazioneCessazioneRP/modificaGestore/";//+id
    public static SUCCESS_MODIFICA_CESSAZIONE: string = "dichiarazioniRP/successModificaCessazione/";//+id
    public static MODIFICA_PAGAMENTI_IMPIANTO_CESSAZIONE: string = "dichiarazioniRP/modificaDichiarazioneCessazioneRP/pagamentiImpianto/";//+idDichiarazione

    //RETTIFICA DA CENSIRE NEL ROUTING 
    public static RETTIFICA_RICERCA_DICHIARAZIONI: string = "dichiarazioniRP/rettificaDichiarazioneRP/creaDichiarazioni"
    //public static RETTIFICA_ELENCO_IMPIANTI: string = "dichiarazioniRP/rettificaDichiarazioneRP/elencoImpianti/";//da chiedere
    public static RETTIFICA_DICHIARAZIONE: string = "dichiarazioniRP/rettificaDichiarazioneRP/rettificaDichiarazione/";// +idDichiarazione
    public static RETTIFICA_ELENCO_DICHIARAZIONI: string = "dichiarazioniRP/rettificaDichiarazioneRP/elencoDichiarazioni";
    public static RETTIFICA_SUCCESS: string = "dichiarazioniRP/successRettifica/";//+idDichiarazione

    //CONSULTA
    public static CONSULTA_RICERCA_DICHIARAZIONI: string = "dichiarazioniRP/consultaDichiarazioneRP/ricercaDichiarazione";
    public static CONSULTA_ELENCO_DICHIARAZIONI: string = "dichiarazioniRP/consultaDichiarazioneRP/elencoDichiarazioni";
    public static CONSULTA_DICHIARAZIONE: string = "dichiarazioniRP/consultaDichiarazioneRP/consultaDichiarazione/";//+idDichiarazione
    public static CONSULTA_ELENCO_IMPIANTI: string = "dichiarazioniRP/consultaDichiarazioneRP/elencoImpianti/";//+idDichiarazione
    public static CONSULTA_IMPIANTO: string = "dichiarazioniRP/consultaDichiarazioneRP/consultaImpianto/";//+idDichiarazione
    public static CONSULTA_TITOLARI: string = "dichiarazioniRP/consultaDichiarazioneRP/consultaTitolari/";//+idDichiarazione
    public static CONSULTA_GESTORE: string = "dichiarazioniRP/consultaDichiarazioneRP/consultaGestori/";//+idDichiarazione
    public static CONSULTA_ELENCO_PAGAMENTI: string = "dichiarazioniRP/consultaDichiarazioneRP/consultaElencoPagamenti/";//+idDichiarazione

    //INVIO
    public static INVIO_RICERCA_DICHIARAZIONE: string = "dichiarazioniRP/invioDichiarazioneRP/invioRicercaDichiarazioni";
    public static INVIO_ELENCO_DICHIARAZIONE: string = "dichiarazioniRP/invioDichiarazioneRP/invioElencoDichiarazioni";
    public static INVIO_INVIA_DICHIARAZIONE: string = "dichiarazioniRP/invioDichiarazioneRP/invioInviaDichiarazione/"; // +idDichiarazione
    public static INVIO_SUCCESS: string = "dichiarazioniRP/invioSuccess"
     
     

}

export class RoutingPagamentoRP {
    //CONSULTA
    public static CONSULTA_RICERCA_PAGAMENTI: string = "pagamentiRP/consultaPagamentiRP/ricercaPagamenti";
    public static CONSULTA_ELENCO_PAGAMENTI: string = "pagamentiRP/consultaPagamentiRP/elencoPagamenti";
    public static CONSULTA_PAGAMENTO: string = "pagamentiRP/consultaPagamentiRP/dettaglioPagamento";


    //RICONCIA PAGAMENTI
    public static RICONCILIA_PAGAMENTI_RICERCA: string = "pagamentiRP/riconciliaPagamenti/pagamentiRicerca";
    public static RICONCILIA_PAGAMENTI_ELENCO: string = "pagamentiRP/riconciliaPagamenti/pagamentiElenco";
    public static RICONCILIA_PAGAMENTI_CONFERMA: string = "pagamentiRP/riconciliaPagamenti/pagamentiConferma";


}

export class RoutingDichiarazioneOperatore {
    //INSERIMENTO
    public static INSERIMENTO_CREA_DICHIARAZIONE_OPERATORE: string = "dichiarazioneOperatore/inserimentoDichiarazioneOperatore/creaDichiarazione";
    public static INSERIMENTO_RICERCA_IMPIANTO: string = "dichiarazioneOperatore/inserimentoDichiarazioneOperatore/ricercaImpianto/"; //id
    public static DATI_IMPIANTO: string = "dichiarazioneOperatore/inserimentoDichiarazioneOperatore/datiImpianto/"; //+id
    public static DATI_TITOLARE: string = "dichiarazioneOperatore/inserimentoDichiarazioneOperatore/datiTitolare/"; //+id
    public static DATI_GESTORE: string = "dichiarazioneOperatore/inserimentoDichiarazioneOperatore/datiGestore/"; //+id
    public static INSERIMENTO_SUCCSS: string = "dichiarazioneOperatore/inserimentoSuccess/"; // +id
    public static INSERIMENTO_PAGAMENTI_IMPIANTI: string = "dichiarazioneOperatore/inserimentoDichiarazioneOperatore/pagamentiImpianto/"; // +id

    //MODIFICA
    public static MODIFICA_RICERCA_IMPIANTO: string = "dichiarazioneOperatore/modificaDichiarazioneOperatore/modificaRicercaImpianto/"; //+id
    public static MODIFICA_IMPIANTO: string = "dichiarazioneOperatore/modificaDichiarazioneOperatore/modificaDatiImpianto/"; //+id
    public static MODIFICA_TITOLARE: string = "dichiarazioneOperatore/modificaDichiarazioneOperatore/modificaDatiTitolare/";
    public static MODIFICA_GESTORE: string = "dichiarazioneOperatore/modificaDichiarazioneOperatore/modificaDatiGestore/";
    public static MODIFICA_SUCCSS: string = "dichiarazioneOperatore/modificaSuccess/"; // +id
    public static MODIFICA_PAGAMENTI_IMPIANTI: string = "dichiarazioneOperatore/modificaDichiarazioneOperatore/modificaPagamentiImpianto/"; //+id
    
    
    //MODIFICA CESSAZIONE
    public static MODIFICA_CESSAZIONE_RICERCA_IMPIANTO: string = "dichiarazioneOperatore/modificaDichiarazioneOperatoreCessazione/modificaRicercaImpianto/"; //+id
    public static MODIFICA_CESSAZIONE_IMPIANTO: string = "dichiarazioneOperatore/modificaDichiarazioneOperatoreCessazione/modificaDatiImpianto/"; //+id
    public static MODIFICA_CESSAZIONE_TITOLARE: string = "dichiarazioneOperatore/modificaDichiarazioneOperatoreCessazione/modificaDatiTitolare/";
    public static MODIFICA_CESSAZIONE_GESTORE: string = "dichiarazioneOperatore/modificaDichiarazioneOperatoreCessazione/modificaDatiGestore/";
    public static MODIFICA_CESSAZIONE_SUCCSS: string = "dichiarazioneOperatore/modificaSuccessCessazione/"; // +id
    public static MODIFICA_CESSAZIONE_PAGAMENTI_IMPIANTI: string = "dichiarazioneOperatore/modificaDichiarazioneOperatoreCessazione/modificaPagamentiImpianto/"; //+id
    

    //RETTIFICA
    public static RETTIFICA_ELENCO_DICHIARAZIONI: string = "dichiarazioniOperatore/rettificaDichiarazioneOperatore/rettificaElencoDichiarazioni";
    public static RETTIFICA_ELENCO_IMPIANTI: string = "dichiarazioniOperatore/rettificaDichiarazioneOperatore/rettificaElencoImpianti/";// +idDichiarazione

    //CONSULTA
    public static CONSULTA_RICERCA_DICHIARAZIONI: string = "dichiarazioniOperatore/consultaDichiarazioneOperatore/ricercaDichiarazione";
    public static CONSULTA_DICHIARAZIONE: string = "dichiarazioniOperatore/consultaDichiarazioneOperatore/consultaDichiarazione";
    public static CONSULTA_ELENCO_IMPIANTI: string = "dichiarazioniOperatore/consultaDichiarazioneOperatore/elencoImpianti/";//+idDichiarazione
    public static CONSULTA_IMPIANTO: string = "dichiarazioniOperatore/consultaDichiarazioneOperatore/consultaImpianto/";//+idDichiarazione
    public static CONSULTA_TITOLARI: string = "dichiarazioniOperatore/consultaDichiarazioneOperatore/consultaTitolari/";
    public static CONSULTA_GESTORE: string = "dichiarazioniOperatore/consultaDichiarazioneOperatore/consultaGestori/";
    public static CONSULTA_ELENCO_PAGAMENTI: string = "dichiarazioniOperatore/consultaDichiarazioneOperatore/consultaElencoPagamenti/";
    public static CONSULTA_ELENCO_DICHIARAZIONI_CESSAZIONE: string = "dichiarazioniOperatore/consultaDichiarazioneOperatore/consultaElencoDichiarazioneCessazione";

    //INVIO
    public static INVIO_INVIA_DICHIARAZIONE: string = "dichiarazioneOperatore/invioDichiarazioneOperatore/inviaDichiarazioniOperatore/";
    public static INVIO_ELENCO_DICHIARAZIONE: string = "dichiarazioneOperatore/invioDichiarazioneOperatore/elencoDichiarazioniOperatore";//+id
    public static INVIO_SUCCESS: string = "dichiarazioneOperatore/invioDichiarazioneOperatore/invioSuccess/"; //+id
    
    //CESSAZIONE
    public static CESSAZIONE_CREA_DICHIARAZIONE_OPERATORE: string = "dichiarazioneOperatore/cessazioneDichiarazioneOperatore/creaDichiarazione";
    public static INSERIMENTO_CESSAZIONE_RICERCA_IMPIANTO: string = "dichiarazioneOperatore/cessazioneDichiarazioneOperatore/ricercaImpianto/"; //id
    public static INSERIMENTO_ELENCO_DICHIARAZIONE_CESSAZIONE: string = "dichiarazioneOperatore/cessazioneDichiarazioneOperatore/elencoDichiarazione/"; //anno
    public static DATI_IMPIANTO_CESSAZIONE: string = "dichiarazioneOperatore/cessazioneDichiarazioneOperatore/datiImpianto/"; //+id
    public static DATI_TITOLARE_CESSAZIONE: string = "dichiarazioneOperatore/cessazioneDichiarazioneOperatore/datiTitolare/"; //+id
    public static DATI_GESTORE_CESSAZIONE: string = "dichiarazioneOperatore/cessazioneDichiarazioneOperatore/datiGestore/"; //+id
    public static CESSAZIONE_SUCCSS: string = "dichiarazioneOperatore/inserimentoSuccessCessazione/"; // +id
    public static CESSAZIONE_PAGAMENTI_IMPIANTI: string = "dichiarazioneOperatore/cessazioneDichiarazioneOperatore/pagamentiImpianto/"; // +id
}

export class RoutingPagamentoOperatore {
    //INSERIMENTO
    public static INSERIMENTO_PAGAMENTO_OPERATORE: string = "pagamentoEffettuareOperatore/effettuarePagamento/inserisciPagamentoOperatore"
    public static INSERIMENTO_ELENCO_IMPIANTI: string = "pagamentoEffettuareOperatore/effettuarePagamento/inserisciElencoImpianti/" //id
    public static INSERIMENTO_RICERCA_IMPIANTO: string = "pagamentoEffettuareOperatore/effettuarePagamento/ricercaImpianto/" //id
    public static INSERIMENTO_DATI_IMPIANTO: string = "pagamentoEffettuareOperatore/effettuarePagamento/datiImpianto/" //id
    public static INSERIMENTO_DATI_TITOLARE: string = "pagamentoEffettuareOperatore/effettuarePagamento/datiTitolare/" //id
    public static INSERIMENTO_DATI_GESTORE: string = "pagamentoEffettuareOperatore/effettuarePagamento/datiGestore/" //id
    public static INSERIMENTO_PAGAMENTO_IMPIANTO: string = "pagamentoEffettuareOperatore/effettuarePagamento/inserisciPagamentoImpianto/" //id
    public static INSERIMENTO_CARRELLO_PAGAMENTI: string = "pagamentoEffettuareOperatore/effettuarePagamento/inserisciCarrelloPagamenti/" //id
    public static INSERIMENTO_ELENCO_IMPIANTI_CARRELLO: string = "pagamentoEffettuareOperatore/effettuarePagamento/elencoImpiantiCarrello/"
    public static SUCCESS_TRANSAZIONE: string = "pagamentoEffettuareOperatore/effettuarePagamento/successTransazione"
    public static FAILURE_TRANSAZIONE: string = "pagamentoEffettuareOperatore/effettuarePagamento/failureTransazione"
    public static SALVATAGGIO_SUCCESS_DICHIARAZIONE: string = "pagamentoEffettuareOperatore/effettuarePagamento/salvataggioSuccess/"
    public static SUCCESS_TRANSAZIONE_STAMPA_AVVISO: string = "pagamentoEffettuareOperatore/effettuarePagamento/successTransazioneStampaAvviso/"

    //SCARICA RICEVUTA
    public static RICERCA_RICEVUTA_PAGAMENTO: string = "pagamentoScaricaOperatore/scaricaRicevuta"
    public static SCARICA_RICEVUTA_PAGAMENTO: string = "pagamentoScaricaOperatore/scaricaRicevuta/scaricaRicevutaPagamento"

}

export class RoutingDichiarazioneDoganiere {
    //CONSULTA
    public static CONSULTA_RICERCA_DICHIARAZIONI: string = "dichiarazioniDoganiere/consultaDichiarazioneDoganiere/ricercaDichiarazione";
    public static CONSULTA_ELENCO_DICHIARAZIONI: string = "dichiarazioniDoganiere/consultaDichiarazioneDoganiere/elencoDichiarazioni";
    public static CONSULTA_DICHIARAZIONE: string = "dichiarazioniDoganiere/consultaDichiarazioneDoganiere/consultaDichiarazione/";//+idDichiarazione
    public static CONSULTA_ELENCO_IMPIANTI: string = "dichiarazioniDoganiere/consultaDichiarazioneDoganiere/elencoImpianti/";//+idDichiarazione
    public static CONSULTA_IMPIANTO: string = "dichiarazioniDoganiere/consultaDichiarazioneDoganiere/consultaImpianto/";//+idDichiarazione
    public static CONSULTA_TITOLARI: string = "dichiarazioniDoganiere/consultaDichiarazioneDoganiere/consultaTitolari/";
    public static CONSULTA_GESTORE: string = "dichiarazioniDoganiere/consultaDichiarazioneDoganiere/consultaGestori/";
    public static CONSULTA_ELENCO_PAGAMENTI: string = "dichiarazioniDoganiere/consultaDichiarazioneDoganiere/consultaElencoPagamenti/";

}

export class RoutingReportRP {
    public static CONSULTARE_REPORT: string = "consultaReport/report/consultaReport";
}
