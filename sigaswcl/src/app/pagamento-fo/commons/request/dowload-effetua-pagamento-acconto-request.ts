export class DowloadEffetuaPagamentoAccontoRequest {
    constructor(
        public idImpianto: number,
        public totale: number,
        public optradio: number,
        public anno: number
    ) { }

}