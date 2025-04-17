export class RicercaStoricoSoggettiRequest {
    constructor(
        public denominazioneSoggetto?: string,
        public annualita?: string,
        public indirizzo?: string,
        public codiceAzienda?: string,
        public cfPiva?: string
    ) {

    }
}