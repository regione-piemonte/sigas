import { MeseVO } from "./mese-vo";

export class TablePagamentoVO {

    constructor(
        public idDichiarazioneImpianto: number,
        public dichiarante: string,
        public codiceDichiarazione: string,
        public codiceDitta: string,
        public titolare: string,
        public acconto: number,
        public comune: string,
        public provincia: string,
        public stato: string,
        public dataVersamento: string,
        public mese: MeseVO,
    ) { }

}