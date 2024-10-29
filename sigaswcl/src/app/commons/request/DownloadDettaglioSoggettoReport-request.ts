import { ItemSoggettoReport } from "./ItemSoggettoReport";

export class DownloadDettaglioSoggettoReport {
    constructor(
        public denominazione: string,
        public codiceAzienda: string,
        public indirizzo: string,
        public iban: string,
        public telefono: string,
        public pec: string,
        public email: string,
        public note: string,
        public fideussione: string,
        public importoFideussione: number,
        public comune: string,
        public provincia: string,

        public itemSoggettoReportList: Array<ItemSoggettoReport>
    ) { }
}