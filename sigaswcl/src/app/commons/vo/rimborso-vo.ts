import { AnagraficaSoggettoVO } from "../vo/soggetti-vo";
import { AnaComunicazioniVO } from "../vo/ana-comunicazioni-vo";

export class RimborsoVO {

    constructor(
        public idRimborso: number,
        public anagraficaSoggettoVO: AnagraficaSoggettoVO,
        public anaComunicazioniVO: AnaComunicazioniVO, 
        public annualita: string,
        public dataIstanza: Date,
        public dataRimborso: Date,
        public importo: number,
        public importoRimborsato: number,
        public statoPratica: string,
        public version: number,
        public allarme: boolean,
        public dataVersamento: Date
    ) { }
}