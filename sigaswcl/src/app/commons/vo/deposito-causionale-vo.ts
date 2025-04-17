import { ProvinciaVO } from './provincia-vo';
import { AnagraficaSoggettoVO } from './soggetti-vo';
import { DocumentiVO } from './documenti-vo';

export class DepositoCausionaleVO {
    constructor(
        public idDepositoCausionale: number,        
        public importo: number,
        public anagraficaSoggettoVO?: AnagraficaSoggettoVO,
        public provinciaVO?: ProvinciaVO,
        public documentoVO?: DocumentiVO,
        public annoAcccertamento?: String,
        public numeroAccertamento?: String,
        public numeroDetermina?: String,
    ) { }
}