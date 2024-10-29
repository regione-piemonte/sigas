export class PagamentoRiconciliaRequest {
    constructor(
        public codicePagamento?: string,
        public anno?: number,
        public mese?: string,
        public cfDichiarante?: string,
        public piva?: string,
    ) { }
}