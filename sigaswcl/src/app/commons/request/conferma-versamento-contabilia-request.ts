import { VersamentiPrVO } from '../vo/versamenti-pr-vo';
import { OrdinativiIncassoVO } from '../vo/ordinativi-incasso'

export class ConfermaVersamentoContabiliaRequest {

    constructor(public versamento: VersamentiPrVO,
                public pagamento: OrdinativiIncassoVO,
                public conciliato: boolean,
                public confermaInserimento:boolean)
    { }
}
