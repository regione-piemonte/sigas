import { AccontoVO } from "../vo/acconto-vo";

export class SalvaAccontoRequest {

    constructor(
        public idDichiarazione: number,
        public idImpianto: number,
        public idDichiarazioneImpianto: number,
        public acconto: AccontoVO
    ) { }
}