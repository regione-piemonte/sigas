export class SubjectVO {

    constructor(
        public idAnag: number,
        public nomeAzienda: string,
        public codiceAzienda: string,
        public denominazione: string,
        public siglaProvincia: string,
        public siglaProvinciaAzienda: string,
        public comune: string,
        public cap: string,
        public mesi: string,
        public totale: string,
        public provincie: string,
        public codiceFiscale: string,
        public codiceFiscalePIva: string
    ) { }
}