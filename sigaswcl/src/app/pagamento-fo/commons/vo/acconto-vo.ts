import { CarrelloVO } from "./carrello-vo";
import { MeseVO } from "./mese-vo";

export class AccontoVO {

    constructor(
        public id: number,

        public mese: MeseVO,
        public litri: number,
        public aliquota: number,
        public note: String,

        public imposta: number,
        public impostaDovuta: number,
        public acconto: number,
        public sanzioni: number,
        public interessi: number,

        public dataPagamento: string,
        public dataVersamento: string,
        public totale: number,

        public carrello: CarrelloVO,
    ) { }
}