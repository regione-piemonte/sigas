import { RegioniVO } from "./luoghi-vo/regioni-VO";
import { ProvinceVO } from "./luoghi-vo/province-VO";
import { ComuniVO } from "./luoghi-vo/comuni-VO";


export class TitolareVO {
    constructor(
        public denominazione: string,
        public codiceFiscale: string,
        public partitaIva: string,
        public regione: RegioniVO,
        public provincia: ProvinceVO,
        public comune: ComuniVO,
        public cap: string,
        public indirizzo: string,
        public civico: string,
        public indirizzoNonTrovato: string,
        public fineValidita: string,
        public nAtto: string,
        public dataAtto: string,
    ) { }
}