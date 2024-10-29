import { ProvinceVO } from "./luoghi-vo/province-vo";
import { RegioniVO } from "./luoghi-vo/regioni-VO";
import { ComuniVO } from "./luoghi-vo/comuni-vo"; 
import { NazioniVO } from "./luoghi-vo/nazioni-VO";
import { DichiaranteVO } from './dichiarante-accreditamento-vo';
import { LegaleRappresentanteVO } from './legale-rappresentante-vo';
import { OperatoreVO } from './operatore-vo';

export class UtenteProvvisorioVO {

    constructor(
            
            public idUtenteProvv: number,
            public erroreMail: string,
            public idLegaleRapp: number,
            public idOperatore: number,
            public idSoggettoProvvisorio: number,
            public note: string,
            public idPratica: number,
            public stato: string,
            public insDate: Date,
            public insUser:string,
            public modDate:Date,
            public modUser:string,
            public dichiaranteVO: DichiaranteVO,
            public legaleRappresentanteVO: LegaleRappresentanteVO,
            public operatoreVO: OperatoreVO
    ) { }
}