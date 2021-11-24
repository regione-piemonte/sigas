import { PagamentoImpiantoVO } from "../vo/pagamenti-impianto-vo";


export class PagamentoImpiantoResponse {
    constructor(
        public pagamentiImpianto: Array<PagamentoImpiantoVO>,
        public flagInserito: boolean,
        public denomDichiarante: string,
        public codDitta: string
         
    ) { }
}