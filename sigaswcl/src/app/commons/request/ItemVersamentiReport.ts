export class ItemVersamentiReport {

    constructor(        
        public anno: string,
        public mese: string,        
	    public provincia: string,
	    public tipo: string,
        //Importo Versato
        public importo: number,
        public importo_calcolato: number,
        public differenza:number,
        public note:string
    ) { }
}