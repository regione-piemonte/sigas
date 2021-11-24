import { PagamentoImpiantoVO } from "../vo/pagamenti-impianto-vo";

export class SalvaPagamentiImpiantoRequest {
    constructor (
        public idImpianto:number,
        public idDichiarazione:number,
        public pagamentiImpianto:Array<PagamentoImpiantoVO>
    ) { } 
}