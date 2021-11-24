import {LegaleRappresentanteVO} from '../vo/legale-rappresentante-vo';
import {OperatoreVO} from '../vo/operatore-vo';
import {DichiaranteVO} from '../../../../commons/vo/dichiarante-accreditamento-vo';

export class ConfermaAccreditamentoRequest {

    public idOperatoreProvvisorio: number;

    constructor(public dichiarante: DichiaranteVO,
                public legaleRappresentante: LegaleRappresentanteVO,
                public operatore: OperatoreVO
    ) {
    }
}
