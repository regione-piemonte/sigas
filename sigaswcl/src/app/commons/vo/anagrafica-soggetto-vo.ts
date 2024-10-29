import { AllarmiVO } from '../vo/allarmi-vo';

export class SoggettoVO {

    constructor(
        public id_anag: number,
        public dichiarante: string,
        public codice_azienda: string,
        public totVersato: number,
        public validazione: boolean,
        public allarmi: AllarmiVO
    ) { }
}