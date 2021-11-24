import { DichiaranteVO } from "../vo/dichiarante-vo";


export class ModificaDichiarazioneRequest {
    constructor(
        public id: number,
        public dataCartaceo: string,
        public dichiarante: DichiaranteVO
    ) { }
}