import { ProvinceVO } from "./luoghi-vo/province-VO";
import { RegioniVO } from "./luoghi-vo/regioni-VO";
import { ComuniVO } from "./luoghi-vo/comuni-VO";
import { NazioniVO } from "./luoghi-vo/nazioni-VO";

export class DichiaranteVO {

    constructor(
        public denominazione?: string,
        public codiceFiscale?: string,
        public partitaIva?: string,
        public regione?: RegioniVO,
        public provincia?: ProvinceVO,
        public comune?: ComuniVO,
        public cap?: string,
        public indirizzo?: string,
        public indirizzoNonTrovato?: string,
        public civico?: string,
        public statoEstero?: NazioniVO,
        public flagTitolare?: string,
        public flagCensito?: boolean
    ) { }
}