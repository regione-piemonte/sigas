import { LegaleRappresentanteVO } from "../vo/legale-rappresentante-vo";
import { OperatoreVO } from "../vo/operatore-vo";
import { DichiaranteVO } from "../vo/dichiarante-accreditamento-vo";



export class ConfermaPraticaAccreditamentoRequest {
    constructor(
            public dichiarante: DichiaranteVO,
            public operatore: OperatoreVO,
            public legaleRappresentante: LegaleRappresentanteVO,
            public idUtenteProvv: number,
            public stato: string,
            public note: string
    ) {

    }
}
