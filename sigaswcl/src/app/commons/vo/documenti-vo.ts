import { AnagraficaSoggettoVO } from './soggetti-vo';
import { TipoDocumentoVO } from './tipo-documento-vo';
import { AllegatoVO } from './allegato-vo';
import { StatoDocumentoVO } from './stato-documento-vo';
import { AllegatoDocumentazioneVO } from './allegato-documentazione-vo';
import { StatoArchiviazioneVO } from './stato-archiviazione-vo';

export class DocumentiVO {

    constructor(
        public idDocumento: number,
        public anagraficaSoggettoVO: AnagraficaSoggettoVO,
        public annualita: number,
        public tipoDocumentoVO: TipoDocumentoVO,
        public tipoComunicazioneVO: TipoDocumentoVO,
        public tipoRimborsoVO: TipoDocumentoVO,
        public nomeFile: string,
        public note: string,
        public nprotocollo: string,
        public rifArchivio: string,
        public cfPiva: string,
        public statoDocumentoVO: StatoDocumentoVO,
        public noteBo: string ,
        public dataProtocollazione: Date,
        public insDate: Date,
        public insUser: string,
        public idIndex:string,
        public sigasAllegatos: Array<AllegatoDocumentazioneVO>,
        public sigasStatoArchiviazioneVO:StatoArchiviazioneVO,
        public nProtocolloAccertamento: string,
        public annoProtocolloAccertamento: string
        
        ) { }
}
