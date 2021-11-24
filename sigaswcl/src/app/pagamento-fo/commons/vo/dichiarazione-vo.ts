
import { StatoDichiarazioneVO } from "./stato-dichiarazione-vo";
import { DichiaranteVO } from "./dichiarante-vo";


export class DichiarazioneVO {

    constructor(
        public id: number,
        public anno: number,
        public stato: StatoDichiarazioneVO,
        public dataCartaceo: string,
        public codiceDichiarazione: string,
        public numeroProtocollo: string,
        public dichiarante: DichiaranteVO,
        //RETTIFICA
        public flagValidata: boolean,
        public totaleVersato: number,
        public utenteInserimento: string,
        public dichiarazionePadre: DichiarazioneVO,
        //INVIO
        public flagRettificato: boolean,
        public flagCessata: boolean
    ) { }
}