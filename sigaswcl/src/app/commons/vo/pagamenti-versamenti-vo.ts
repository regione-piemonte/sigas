import { AnagraficaSoggettoVO } from './soggetti-vo';
import { ProvinciaVO } from './provincia-vo';
import { VersamentiPrVO } from './versamenti-pr-vo';
import { TipoVersamentiVO } from './tipo-versamenti-vo';
import { OrdinativiIncassoVO } from './ordinativi-incasso';

export class PagamentiVersamentiVO {

    constructor(
            public idPagamentoVersamento: number,
            public anno:string,
            public dataVersamento: Date,
            public fkAnag: AnagraficaSoggettoVO,
            public fkProvincia:ProvinciaVO,
            public fkVersamento:VersamentiPrVO,
            public idTipoVersamento: TipoVersamentiVO,
            public importo:number,
            public mese:string,
            public fkPagamento: number
            
            
        
    ) { }
}