import { DichiarazioneVO } from "./dichiarazione-vo";
import { ImpiantoVO } from "./impianto-vo";
import { CarrelloVO } from "./carrello-vo";

export class RiepilogoPagamentoVO {
    constructor(
        public dichiarazione: DichiarazioneVO,
        public impianti: Array<ImpiantoVO>,
        public carrello: CarrelloVO
    ) { }
}