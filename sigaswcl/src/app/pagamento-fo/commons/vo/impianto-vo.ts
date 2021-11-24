import { TitolareVO } from "./titolare-vo";
import { GestoreVO } from "./gestore-vo";
import { ComuniVO } from "./luoghi-vo/comuni-VO";
import { ProvinceVO } from "./luoghi-vo/province-VO";
import { TipologiaPermessoVO } from "./tipologia-permesso-vo";


export class ImpiantoVO {
    constructor(
        public id: number,
        public codiceDitta: string,
        public provincia: ProvinceVO,
        public comune: ComuniVO,
        public cap: string,
        public indirizzo: string,
        public civico: string,
        public indirizzoNonTrovato: string,
        public tipologiaPermesso: TipologiaPermessoVO,
        public ente: string,
        public numero: string,
        public data: string,
        public titolare: Array<TitolareVO>,
        public gestore: Array<GestoreVO>
    ) { }
}