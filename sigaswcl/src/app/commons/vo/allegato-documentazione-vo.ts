import { StatoArchiviazioneVO } from './stato-archiviazione-vo';

export class AllegatoDocumentazioneVO {

    constructor(
        public idAllegato: number,
        public dataOraProtocollo: Date,
        public descrizione: string,
        public idIndex:string,
        public idStatoAllegato: number,
        public insDate: Date,
        public insUser: string,
        public nprotocollo: string,
        public nomeFile: string,
        public sigasStatoArchiviazioneVO:StatoArchiviazioneVO
        
       
        
        ) { }
}
