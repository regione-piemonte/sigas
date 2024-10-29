
export class RicercaOrdinativiRequest {
    constructor(
        public anno: string,
        public dataIncassoDa: Date,
        public dataIncassoA: Date,
        public conciliato: Boolean,
        public azienda : string,
        public codiceAzienda : string,
        public conciliatoParziale: Boolean
    ) {

    }
}