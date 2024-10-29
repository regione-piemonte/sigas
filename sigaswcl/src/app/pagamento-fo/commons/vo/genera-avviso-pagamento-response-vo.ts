
import { GeneraAvvisoPagamentoResultVO } from './genera-avviso-pagamento-result-vo';

export class GeneraAvvisoPagamentoResponseVO {
    constructor(
        public paymentnotice: any,
        public result: GeneraAvvisoPagamentoResultVO
    ) { }
}