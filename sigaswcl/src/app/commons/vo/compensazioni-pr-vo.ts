export interface CompensazioniPrVO {
    
	id_compensazione?: number;	
	data_compensazione?: String;
	id_consumi?: number;
    conguaglio_di_riferimento?: number;
	conguaglio_di_riferimento_t0?: number;
	conguaglio_compensato?: number;
	compensazione?: number;	
}