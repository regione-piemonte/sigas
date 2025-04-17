export interface StoricoAnagraficaSoggettoVO {    
	idStoricoAnag: number;
	idAnag: number;
	codiceAzienda: string;    
    denominazione: string;
    cfPiva: string;
    indirizzo: string;
    iban: string    
    pec: string;
    email: string;
    ownerOperazione: string;
    idImport: number;
    annualita: string;
    dataRiferimento: number;
    tipoElaborazione?: string;	
}