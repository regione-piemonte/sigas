export class RicercaDichiaranteRequest {
    constructor(
        public codiceFiscale: string,
        public denominazione: string,
        public partitaIva: string
    ) { }
}