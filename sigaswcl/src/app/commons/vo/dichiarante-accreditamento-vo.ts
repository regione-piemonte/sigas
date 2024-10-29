import { ProvinceVO } from "./luoghi-vo/province-vo";
import { RegioniVO } from "./luoghi-vo/regioni-VO";
import { ComuniVO } from "./luoghi-vo/comuni-vo";
import { NazioniVO } from "./luoghi-vo/nazioni-VO";

export class DichiaranteVO {
//
//    constructor(
//        public denominazione?: string,
//        public codiceFiscale?: string,
//        public partitaIva?: string,
//        public codiceAzienda? : string,
//        public regione?: RegioniVO,
//        public provincia?: ProvinceVO,
//        public comune?: ComuniVO,
//        public cap?: string,
//        public indirizzo?: string,
//        public indirizzoNonTrovato?: string,
//        public civico?: string,
//        public statoEstero?: NazioniVO,
//        public flagTitolare?: string,
//        public flagCensito?: boolean
//    ) { }
    
    constructor(
            public idDichiarante?: number,
            public denominazione?: string,
            public codiceAzienda? : string,
            public regione?: RegioniVO,
            public provincia?: ProvinceVO,
            public comune?: ComuniVO,
            public indirizzo?: string,
            public emailDichiarante?: string,
            public iban?: string,
            public note?: string,
            public pecDichiarante?: string,
            public telefonoDichiarante?: number,
            public flagCensito?: boolean
        ) { }
    
}