import { PagamentoImpiantoVO } from "../vo/pagamenti-impianto-vo";

export class RequestCalcolaPagamenti {
    constructor(
        public idDichiarazione:number,
        public pagamentiImpianto: Array<PagamentoImpiantoVO>
    ) { }
}