import { ImpiantoVO } from "./impianto-vo";
import { AccontoVO } from "./acconto-vo";

export class DettaglioPagamentoVO {

    constructor(
        public impianto: ImpiantoVO,
        public acconti: Array<AccontoVO>,
    ) { }

}