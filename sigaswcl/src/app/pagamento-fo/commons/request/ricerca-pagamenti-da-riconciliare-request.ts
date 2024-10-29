import { MeseVO } from "../vo/mese-vo";

export class RicercaPagamentiDaRiconciliareRequest {
    constructor(
        public codicePagamento?: string,
        public anno?: number,
        public mese?: MeseVO,
        public cfDichiarante?: string,
        public piva?: string,
        public flagRiconciliato?: boolean
    ) { }
}