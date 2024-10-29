import { DichiaranteVO } from './dichiarante-accreditamento-vo';
import { LegaleRappresentanteVO } from './legale-rappresentante-vo';
import { OperatoreVO } from './operatore-vo';
import { UtenteProvvisorioVO } from './utente-provvisorio-vo'


export class AccreditamentoVO {

    constructor(
        public dichiarante: DichiaranteVO,
        public legaleRappresentante: LegaleRappresentanteVO,
        public operatore: OperatoreVO,
        public utenteProvvisorio: UtenteProvvisorioVO 
        
        
    ) { }
}
