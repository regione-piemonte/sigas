import { StatoDichiarazioneVO } from "../vo/stato-dichiarazione-vo";


export class RicercaDichiarazioniConsultaRequest {
    constructor(
        public anno?: number,
        public stato?: StatoDichiarazioneVO,
        public flagRettifica?: boolean,
        public cfDichiarante?: string,
        public piva?: string,
        public flagCessata?: boolean,
        public flagDichiarazione?: boolean,
    ) { }
}