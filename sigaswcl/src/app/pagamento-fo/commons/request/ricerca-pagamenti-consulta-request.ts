import { ProvinceVO } from "../vo/luoghi-vo/province-VO";
import { MeseVO } from "../vo/mese-vo";

export class RicercaPagamentiConsultaRequest {
    constructor(
        public anno?: number,
        public meseInizio?: MeseVO,
        public meseFine?: MeseVO,
        public provincia?: ProvinceVO,
        public cfDichiarante?: string,
        public pivaDichiarante?: string,
        public cfTitolare?: string,
        public pivaTitolare?: string,
        public codiceDitta?: string
    ) { }
}