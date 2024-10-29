export class DownloadReport {

    constructor(
        private codiceReport: String,
        private anno: number,
        private codiceFiscalePIva: String,
        private codiceDitta: String,
        private meseDa: number,
        private meseA: number
    ) { }
}