import { ProvinceVO } from "./luoghi-vo/province-VO";
import { ComuniVO } from "./luoghi-vo/comuni-VO";


export class GestoreVO {
    constructor(public denominazione: string,
        public codiceFiscale: string,
        public partitaIva: string,
        public provincia: ProvinceVO,
        public comune: ComuniVO,
        public indirizzo: string,
        public civico: string,
        public indirizzoNonTrovato: string,
        public fineValidita: string) { }
}