import { DichiaranteVO } from "../vo/dichiarante-vo";
import { TransazioneVO } from "../vo/transazione-vo";
import { RicevutaTelematicaVO } from "../vo/ricevuta-telematica-vo";

export class ScaricaRicevutaResponse {
    constructor(
        public dichiarante: DichiaranteVO,
        public transazione: TransazioneVO,
        public rt: RicevutaTelematicaVO
    ) { }
}