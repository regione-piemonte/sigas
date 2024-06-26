import { AnagraficaSoggettoVO } from './soggetti-vo';
import { TipoComunicazioniVO } from './tipo-comunicazioni-vo';
import { AllegatoVO } from './allegato-vo';

export class AnaComunicazioniVO {

    constructor(
        public idComunicazione: number,
        public anagraficaSoggettoVO: AnagraficaSoggettoVO,
        public annualita: number,
        public tipoComunicazioneVO: TipoComunicazioniVO,
        public dataDocumento: Date,
        public descrizione: string,
        public note: string,
        public datiRiassuntivi: string,
        public nprotocollo: string,
        public rifArchivio: string,
        public allarmeOn: boolean,
        public allegati: Array<AllegatoVO>,
        private delDate: Date,
        private delUser: string
        ) { }
}
