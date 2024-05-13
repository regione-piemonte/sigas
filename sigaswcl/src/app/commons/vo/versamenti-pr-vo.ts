import { TipoVersamentiVO } from "../vo/tipo-versamenti-vo";
import { ConsumiPrVO } from "../vo/consumi-pr-vo";
import { AllarmiSoggettoVO } from "../vo/allarmi-soggetto-vo";
import { Data } from "@angular/router";

export class VersamentiPrVO {
    public interessiMora: number;
    public sanzioni: number;
    public interessi: number;
    public allarme: AllarmiSoggettoVO;
    public importo_prec: number;
    public differenza: number;
    public insDate: Date;
    public codMese: number;
   

    constructor(
        public idVersamento: number,
        public idConsumi: number,
        public idAnag: number,
        public annualita: number,
        public mese: string,        
        public provincia: string,
        public tipo: TipoVersamentiVO,
        public consumo: ConsumiPrVO,
        public dataVersamento: Date,
        public importo: number,
        public note: string,
        public dataAccertamento: Date,
        public importoComplessivo: number,
        public hasPagamentiVersamenti: boolean,
        public pagamentiVersamenti: number[]
    ) { }
}